package com.metrics.rfa.utility;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.Quote;
import com.metrics.MDOL.dbo.QuoteDay;
import com.metrics.MDOL.dbo.Symbol;
import com.metrics.MDOL.util.Streamer;
import com.metrics.MDOL.util.TimeUtil;
import com.metrics.rfa.quick.ItemManager;
import com.reuters.rfa.ansipage.Page;
import com.reuters.rfa.ansipage.PageUpdate;
import com.reuters.rfa.common.PublisherPrincipalIdentity;
import com.reuters.rfa.dictionary.DataDef;
import com.reuters.rfa.dictionary.DataDefDictionary;
import com.reuters.rfa.dictionary.DictionaryException;
import com.reuters.rfa.dictionary.ElementEntryDef;
import com.reuters.rfa.dictionary.FidDef;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.dictionary.FieldEntryDef;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMDataBuffer;
import com.reuters.rfa.omm.OMMDataDefs;
import com.reuters.rfa.omm.OMMElementEntry;
import com.reuters.rfa.omm.OMMEntry;
import com.reuters.rfa.omm.OMMException;
import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMFieldList;
import com.reuters.rfa.omm.OMMFilterEntry;
import com.reuters.rfa.omm.OMMFilterList;
import com.reuters.rfa.omm.OMMIterable;
import com.reuters.rfa.omm.OMMMap;
import com.reuters.rfa.omm.OMMMapEntry;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPriority;
import com.reuters.rfa.omm.OMMSeries;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.omm.OMMVector;
import com.reuters.rfa.omm.OMMVectorEntry;
import com.reuters.rfa.rdm.RDMInstrument;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.utility.HexDump;

/**
 * The GenericOMMParser is used to read and initialize dictionaries and parse
 * any OMM message that is passed to it using the parse() method.
 * 
 * This class is not thread safe due to the static variables. The "CURRENT"
 * variables save state between methods, so another thread cannot change the
 * values. CURRENT_DICTIONARY requires only one FieldDictionary to be used at a
 * time. CURRENT_PAGE requires only one page to be parsed at a time.
 */

@SuppressWarnings("unused")
public final class GenericOMMParser
{
    private static HashMap<Integer, FieldDictionary> DICTIONARIES = new HashMap<Integer, FieldDictionary>();
    private static FieldDictionary CURRENT_DICTIONARY;
    private static Page CURRENT_PAGE;
    public static String itemName;

    private static boolean INTERNAL_DEBUG = false;

    /**
     * This method should be called one before parsing and data.
     * 
     * @param fieldDictionaryFilename
     * @param enumDictionaryFilename
     * @throws DictionaryException if an error has occurred
     */
    public static void initializeDictionary(String fieldDictionaryFilename,
            String enumDictionaryFilename) throws DictionaryException
    {
        FieldDictionary dictionary = FieldDictionary.create();
        try
        {
            FieldDictionary.readRDMFieldDictionary(dictionary, fieldDictionaryFilename);
            //System.out.println("field dictionary read from RDMFieldDictionary file");

            FieldDictionary.readEnumTypeDef(dictionary, enumDictionaryFilename);
            //System.out.println("enum dictionary read from enumtype.def file");

            initializeDictionary(dictionary);
        }
        catch (DictionaryException e)
        {
            throw new DictionaryException("ERROR: Check if files " + fieldDictionaryFilename
                    + " and " + enumDictionaryFilename + " exist and are readable.", e);
        }
    }

    // This method can be used to initialize a downloaded dictionary
    public synchronized static void initializeDictionary(FieldDictionary dict)
    {
        int dictId = dict.getDictId();
        if (dictId == 0)
            dictId = 1; // dictId == 0 is the same as dictId 1
        DICTIONARIES.put(new Integer(dictId), dict);
    }

    public static FieldDictionary getDictionary(int dictId)
    {
        if (dictId == 0)
            dictId = 1;
        return (FieldDictionary)DICTIONARIES.get(new Integer(dictId));
    }

    /**
     * parse msg and print it in a table-nested format to System.out
     */
    public static final void parse(OMMMsg msg)
    {
        parseMsg(msg, System.out);
    }


	private static final String hintString(OMMMsg msg)
    {
        StringBuilder buf = new StringBuilder(60);

        boolean bAppend = true;

        if (msg.has(OMMMsg.HAS_ATTRIB_INFO))
        {
            bAppend = append(buf, "HAS_ATTRIB_INFO", bAppend);
        }
        if (msg.has(OMMMsg.HAS_CONFLATION_INFO))
        {
            bAppend = append(buf, "HAS_CONFLATION_INFO", bAppend);
        }
        if (msg.has(OMMMsg.HAS_HEADER))
        {
            bAppend = append(buf, "HAS_HEADER", bAppend);
        }
        if (msg.has(OMMMsg.HAS_ITEM_GROUP))
        {
            bAppend = append(buf, "HAS_ITEM_GROUP", bAppend);
        }
        if (msg.has(OMMMsg.HAS_PERMISSION_DATA))
        {
            bAppend = append(buf, "HAS_PERMISSION_DATA", bAppend);
        }
        if (msg.has(OMMMsg.HAS_PRIORITY))
        {
            bAppend = append(buf, "HAS_PRIORITY", bAppend);
        }
        if (msg.has(OMMMsg.HAS_QOS))
        {
            bAppend = append(buf, "HAS_QOS", bAppend);
        }
        if (msg.has(OMMMsg.HAS_QOS_REQ))
        {
            bAppend = append(buf, "HAS_QOS_REQ", bAppend);
        }
        if (msg.has(OMMMsg.HAS_RESP_TYPE_NUM))
        {
            bAppend = append(buf, "HAS_RESP_TYPE_NUM", bAppend);
        }
        if (msg.has(OMMMsg.HAS_SEQ_NUM))
        {
            bAppend = append(buf, "HAS_SEQ_NUM", bAppend);
        }
        if (msg.has(OMMMsg.HAS_ID))
        {
            bAppend = append(buf, "HAS_ID", bAppend);
        }
        if (msg.has(OMMMsg.HAS_PUBLISHER_INFO))
        {
            bAppend = append(buf, "HAS_PUBLISHER_INFO", bAppend);
        }
        if (msg.has(OMMMsg.HAS_STATE))
        {
            bAppend = append(buf, "HAS_STATE", bAppend);
        }
        if (msg.has(OMMMsg.HAS_USER_RIGHTS))
        {
            bAppend = append(buf, "HAS_USER_RIGHTS", bAppend);
        }

        return buf.toString();
    }

    private static boolean append(StringBuilder buf, String str, boolean first)
    {
        if (!first)
        {
            buf.append(" | ");
            first = false;
        }
        else
            first = false;

        buf.append(str);
        return first;
    }

    /**
     * parse msg and print it in a table-nested format to the provided
     * PrintStream
     */
    public static final void parseMsg(OMMMsg msg, PrintStream ps)
    {
        parseMsg(msg, ps, 0);
    }

    static final void parseMsg(OMMMsg msg, PrintStream ps, int tabLevel)
    {
        msg.getMsgType();
        dumpIndent(ps, tabLevel);
        //ps.println("MESSAGE");
        dumpIndent(ps, tabLevel + 1);
       // ps.println("Msg Type: " + OMMMsg.MsgType.toString(msg.getMsgType()));
        dumpIndent(ps, tabLevel + 1);
        //ps.println("Msg Model Type: " + RDMMsgTypes.toString(msg.getMsgModelType()));
        dumpIndent(ps, tabLevel + 1);
        //ps.println("Indication Flags: " + OMMMsg.Indication.indicationString(msg));

        dumpIndent(ps, tabLevel + 1); 
        //ps.println("Hint Flags: " + hintString(msg));

        if ((msg.getDataType() == OMMTypes.ANSI_PAGE) && msg.isSet(OMMMsg.Indication.CLEAR_CACHE))
        {
            CURRENT_PAGE = null;
        }

        if (msg.has(OMMMsg.HAS_STATE))
        {
            dumpIndent(ps, tabLevel + 1);
            //ps.println("State: " + msg.getState());
        }
        if (msg.has(OMMMsg.HAS_PRIORITY))
        {
            dumpIndent(ps, tabLevel + 1);
            OMMPriority p = msg.getPriority();
            if (p != null){
                //ps.println("Priority: " + p.getPriorityClass() + "," + p.getCount());
            } else{
            //ps.println("Priority: Error flag recieved but there is not priority present");
            }
        }
        if (msg.has(OMMMsg.HAS_QOS))
        {
            dumpIndent(ps, tabLevel + 1);
            //ps.println("Qos: " + msg.getQos());
        }
        if (msg.has(OMMMsg.HAS_QOS_REQ))
        {
            dumpIndent(ps, tabLevel + 1);
            //ps.println("QosReq: " + msg.getQosReq());
        }
        if (msg.has(OMMMsg.HAS_ITEM_GROUP))
        {
            dumpIndent(ps, tabLevel + 1);
            //ps.println("Group: " + msg.getItemGroup());
        }
        if (msg.has(OMMMsg.HAS_PERMISSION_DATA))
        {
        	byte[] permdata = msg.getPermissionData();
        	
            dumpIndent(ps, tabLevel + 1);
            //ps.print("PermissionData: " + HexDump.toHexString(permdata, false));
            //ps.println(" ( " + HexDump.formatHexString(permdata) + " ) ");
        }
        if (msg.has(OMMMsg.HAS_SEQ_NUM))
        {
            dumpIndent(ps, tabLevel + 1);
            //ps.println("SeqNum: " + msg.getSeqNum());
        }

        if (msg.has(OMMMsg.HAS_CONFLATION_INFO))
        {
            dumpIndent(ps, tabLevel + 1);
           // ps.println("Conflation Count: " + msg.getConflationCount());
            dumpIndent(ps, tabLevel + 1);
            //ps.println("Conflation Time: " + msg.getConflationTime());
        }

        if (msg.has(OMMMsg.HAS_RESP_TYPE_NUM))
        {
            dumpIndent(ps, tabLevel + 1);
            //ps.print("RespTypeNum: " + msg.getRespTypeNum());
            dumpRespTypeNum(msg, ps);
        }

        if (msg.has(OMMMsg.HAS_ID))
        {
            dumpIndent(ps, tabLevel + 1);
            //ps.println("Id: " + msg.getId());
        }

        if ((msg.has(OMMMsg.HAS_PUBLISHER_INFO)) || (msg.getMsgType() == OMMMsg.MsgType.POST))
        {
            PublisherPrincipalIdentity pi = (PublisherPrincipalIdentity)msg.getPrincipalIdentity();
            if (pi != null)
            {
                dumpIndent(ps, tabLevel + 1);
                //ps.println("Publisher Address: 0x" + Long.toHexString(pi.getPublisherAddress()));
                dumpIndent(ps, tabLevel + 1);
                //ps.println("Publisher Id: " + pi.getPublisherId());
            }
        }

        if (msg.has(OMMMsg.HAS_USER_RIGHTS) )
        {
        	dumpIndent(ps, tabLevel + 1);
        	//ps.println("User Rights Mask: " + OMMMsg.UserRights.userRightsString(msg.getUserRightsMask()));
        }

        if (msg.has(OMMMsg.HAS_ATTRIB_INFO))
        {
            dumpIndent(ps, tabLevel + 1);
            //ps.println("AttribInfo");
            OMMAttribInfo ai = msg.getAttribInfo();
            if (ai.has(OMMAttribInfo.HAS_SERVICE_NAME))
            {
                dumpIndent(ps, tabLevel + 2);
                //ps.println("ServiceName: " + ai.getServiceName());
            }
            if (ai.has(OMMAttribInfo.HAS_SERVICE_ID))
            {
                dumpIndent(ps, tabLevel + 2);
                //ps.println("ServiceId: " + ai.getServiceID());
            }
            if (ai.has(OMMAttribInfo.HAS_NAME))
            {
                dumpIndent(ps, tabLevel + 2);
               // ps.println("Name: " + ai.getName());
            }
            if (ai.has(OMMAttribInfo.HAS_NAME_TYPE))
            {
                dumpIndent(ps, tabLevel + 2);
                //ps.print("NameType: " + ai.getNameType());
                if (msg.getMsgModelType() == RDMMsgTypes.LOGIN)
                {
                    //ps.println(" (" + RDMUser.NameType.toString(ai.getNameType()) + ")");
                }
                else if (RDMInstrument.isInstrumentMsgModelType(msg.getMsgModelType()))
                {

                    //ps.println(" (" + RDMInstrument.NameType.toString(ai.getNameType()) + ")");
                }
                else
                {
                    //ps.println();
                }
            }
            if (ai.has(OMMAttribInfo.HAS_FILTER))
            {
                dumpIndent(ps, tabLevel + 2);
                //ps.print("Filter: " + ai.getFilter());
                if (msg.getMsgModelType() == RDMMsgTypes.DIRECTORY)
                {
                    //ps.println(" (" + RDMService.Filter.toString(ai.getFilter()) + ")");
                }
                else if (msg.getMsgModelType() == RDMMsgTypes.DICTIONARY)
                {
                    //ps.println(" (" + RDMDictionary.Filter.toString(ai.getFilter()) + ")");
                }
                else
                {
                    //ps.println();
                }
            }
            if (ai.has(OMMAttribInfo.HAS_ID))
            {
                dumpIndent(ps, tabLevel + 2);
                //ps.println("ID: " + ai.getId());
            }
            if (ai.has(OMMAttribInfo.HAS_ATTRIB))
            {
                dumpIndent(ps, tabLevel + 2);
                //ps.println("Attrib");
                parseData(ai.getAttrib(), ps, tabLevel + 2);
            }
        }
        
        dumpIndent(ps, tabLevel + 1);
        //ps.print("Payload: ");
        if (msg.getDataType() != OMMTypes.NO_DATA)
        {
            //ps.println(msg.getPayload().getEncodedLength() + " bytes");
            parseData(msg.getPayload(), ps, tabLevel + 1);
        }
        else
        {
            //ps.println("None");
        }
    }

    /**
     * parse msg and print it in a table-nested format to the provided
     * PrintStream
     */
    @SuppressWarnings("rawtypes")
	public static final void parseDataDefinition(OMMDataDefs datadefs, short dbtype,
            PrintStream ps, int tabLevel)
    {
        DataDefDictionary listDefDb = DataDefDictionary.create(dbtype);
        DataDefDictionary.decodeOMMDataDefs(listDefDb, datadefs);

        //ps.print("DATA_DEFINITIONS ");
        for (Iterator listDefDbIter = listDefDb.iterator(); listDefDbIter.hasNext();)
        {
            DataDef listdef = (DataDef)listDefDbIter.next();

            //ps.print("Count: ");
            //ps.print(listdef.getCount());
           // ps.print(" DefId: ");
           //ps.println(listdef.getDataDefId());

            if (dbtype == OMMTypes.ELEMENT_LIST_DEF_DB)
            {
                for (Iterator listdefIter = listdef.iterator(); listdefIter.hasNext();)
                {
                    ElementEntryDef ommEntry = (ElementEntryDef)listdefIter.next();
                    dumpIndent(ps, tabLevel + 1);
                    //ps.print("ELEMENT_ENTRY_DEF ");
                   // ps.print("Name: ");
                    //ps.print(ommEntry.getName());
                   // ps.print(" Type: ");
                   // ps.println(OMMTypes.toString(ommEntry.getDataType()));
                }
            }
            else
            {
                for (Iterator listdefIter = listdef.iterator(); listdefIter.hasNext();)
                {
                    FieldEntryDef ommEntry = (FieldEntryDef)listdefIter.next();
                    dumpIndent(ps, tabLevel + 1);
                   // ps.print("FIELD_ENTRY_DEF ");
                    //ps.print("FID: ");
                   // ps.print(ommEntry.getFieldId());
                   // ps.print(" Type: ");
                   // ps.println(OMMTypes.toString(ommEntry.getDataType()));
                }
            }
        }
    }

    private static void dumpRespTypeNum(OMMMsg msg, PrintStream ps)
    {
        if (msg.getMsgType() == OMMMsg.MsgType.REFRESH_RESP)
        {
            //ps.println(" (" + OMMMsg.RespType.toString(msg.getRespTypeNum()) + ")");
        }
        else
        // msg.getMsgType() == OMMMsg.OMMMsg.MsgType.UPDATE_RESP
        {
            if ((msg.getMsgModelType() >= RDMMsgTypes.MARKET_PRICE)
                    && (msg.getMsgModelType() <= RDMMsgTypes.HISTORY))
            {
                //ps.println(" (" + RDMInstrument.Update.toString(msg.getRespTypeNum()) + ")");
            }
        }
    }

    /**
     * parse data and print it in a table-nested format to the System.out
     */

    public static final void parse(OMMData data)
    {
        parseData(data, System.out, 0);
    }

    @SuppressWarnings("rawtypes")
	private static final void parseAggregate(OMMData data, PrintStream ps, int tabLevel)
    {
        parseAggregateHeader(data, ps, tabLevel);
        for (Iterator iter = ((OMMIterable)data).iterator(); iter.hasNext();)
        {
            OMMEntry entry = (OMMEntry)iter.next();
            parseEntry(entry, ps, tabLevel + 1);
        }
    }

    /**
     * parse data and print it in a table-nested format to the provided
     * PrintStream
     */
    @SuppressWarnings("rawtypes")
	public static final void parseData(OMMData data, PrintStream ps, int tabLevel)
    {
        if (data.isBlank())
            dumpBlank(ps);
        else if (OMMTypes.isAggregate(data.getType()))
            parseAggregate(data, ps, tabLevel + 1);
        else if ((data.getType() == OMMTypes.RMTES_STRING)
                && ((OMMDataBuffer)data).hasPartialUpdates())
        {
            Iterator iter = ((OMMDataBuffer)data).partialUpdateIterator();
            while (true)
            {
                OMMDataBuffer partial = (OMMDataBuffer)iter.next();
                //ps.print("hpos: ");
                //ps.print(partial.horizontalPosition());
                //ps.print(", ");
                //ps.print(partial.toString());
                if (iter.hasNext()){
                    //ps.print("  |  ");
                }
                else
                    break;
            }
            //ps.println();
        }
        else if (data.getType() == OMMTypes.ANSI_PAGE)
        {
            // process ANSI with com.reuters.rfa.ansipage
            parseAnsiPageData(data, ps, tabLevel);
        }
        else if (data.getType() == OMMTypes.BUFFER || data.getType() == OMMTypes.OPAQUE_BUFFER)
        {
            if (data.getEncodedLength() <= 20)
            {
                dumpIndent(ps, tabLevel + 1);
                // for small strings, print hex and try to print ASCII
                //ps.print(HexDump.toHexString(((OMMDataBuffer)data).getBytes(), false));
               //ps.print(" | ");
                //ps.println(data);
            }
            else
            {
                if (INTERNAL_DEBUG)
                {
                   // ps.println("Hex Format and Data Bytes: ");
                   // ps.println(HexDump.hexDump(((OMMDataBuffer)data).getBytes(), 50));

                   // ps.println("Hex Format: ");
                }

                int lineSize = 32;
                String s = HexDump.toHexString(((OMMDataBuffer)data).getBytes(), false);

                int j = 0;
                while (j < s.length())
                {
                    if (j != 0){
                       // ps.println();
                    }
                    dumpIndent(ps, 1);

                    int end = j + lineSize;
                    if (end >= s.length())
                        end = s.length();

                    for (int i = j; i < end; i++)
                    {
                       // ps.print(s.charAt(i));
                    }
                    j = j + lineSize;
                }

               // ps.println("\nData Bytes: ");
                dumpIndent(ps, 1);
               // ps.println(data);
            }
        }
        else if (data.getType() == OMMTypes.MSG)
        {
            parseMsg((OMMMsg)data, ps, tabLevel + 1);
        }
        else
        {
            try
            {
               // ps.println(data);
            }
            catch (Exception e)
            {
                byte[] rawdata = data.getBytes();
                //ps.println(HexDump.hexDump(rawdata));
            }
        }
    }

    private static final void parseAggregateHeader(OMMData data, PrintStream ps, int tabLevel)
    {
        dumpIndent(ps, tabLevel);
        short dataType = data.getType();
        //ps.println(OMMTypes.toString(dataType));
        switch (dataType)
        {
            case OMMTypes.FIELD_LIST:
            {
                // set DICTIONARY to the dictId for this field list
                OMMFieldList fieldList = (OMMFieldList)data;
                int dictId = fieldList.getDictId();
                CURRENT_DICTIONARY = getDictionary(dictId);
            }
                break;
            case OMMTypes.SERIES:
            {
                OMMSeries s = (OMMSeries)data;
                if (s.has(OMMSeries.HAS_SUMMARY_DATA))
                {
                    dumpIndent(ps, tabLevel + 1);
                    //ps.println("SUMMARY");
                    parseData(s.getSummaryData(), ps, tabLevel + 1);
                }
                if (s.has(OMMSeries.HAS_DATA_DEFINITIONS))
                {
                    dumpIndent(ps, tabLevel + 1);
                    short dbtype = s.getDataType() == OMMTypes.FIELD_LIST ? OMMTypes.FIELD_LIST_DEF_DB
                            : OMMTypes.ELEMENT_LIST_DEF_DB;
                    parseDataDefinition(s.getDataDefs(), dbtype, ps, tabLevel + 1);
                }
            }
                break;
            case OMMTypes.MAP:
            {
                OMMMap s = (OMMMap)data;

                String flagsString = ExampleUtil.mapFlagsString(s);
                dumpIndent(ps, tabLevel);
              //  ps.print("flags: ");
               // ps.println(flagsString);

                if (s.has(OMMMap.HAS_SUMMARY_DATA))
                {
                    dumpIndent(ps, tabLevel + 1);
                    //ps.println("SUMMARY");
                    parseData(s.getSummaryData(), ps, tabLevel + 1);
                }
            }
                break;
            case OMMTypes.VECTOR:
            {
                OMMVector s = (OMMVector)data;

                String flagsString = ExampleUtil.vectorFlagsString(s);
                dumpIndent(ps, tabLevel);
               // ps.print("flags: ");
                //ps.println(flagsString);

                if (s.has(OMMVector.HAS_SUMMARY_DATA))
                {
                    dumpIndent(ps, tabLevel + 1);
                   // ps.println("SUMMARY");
                    parseData(s.getSummaryData(), ps, tabLevel + 1);
                }
            }
                break;
            case OMMTypes.FILTER_LIST:
            {
                OMMFilterList s = (OMMFilterList)data;

                String flagsString = ExampleUtil.filterListFlagsString(s);
                dumpIndent(ps, tabLevel);
                //ps.print("flags: ");
               // ps.println(flagsString);
            }
                break;
        }
    }

    private static final void dumpBlank(PrintStream ps)
    {
        //ps.println();
    }

    private static final void dumpIndent(PrintStream ps, int tabLevel)
    {
        for (int i = 0; i < tabLevel; i++){
            //ps.print('\t');
        }
    }

    private static final void parseEntry(OMMEntry entry, PrintStream ps, int tabLevel)
    {
        try
        {
            switch (entry.getType())
            

            {
                case OMMTypes.FIELD_ENTRY:
                {
                    OMMFieldEntry fe = (OMMFieldEntry)entry;
                    if (CURRENT_DICTIONARY != null)
                    {
                        FidDef fiddef = CURRENT_DICTIONARY.getFidDef(fe.getFieldId());
                        if (fiddef != null)
                        {
                            dumpFieldEntryHeader(fe, fiddef, ps, tabLevel);
                            OMMData data = null;
                            if (fe.getDataType() == OMMTypes.UNKNOWN)
                                data = fe.getData(fiddef.getOMMType());
                            	
                            else
                                // defined data already has type
                                data = fe.getData();
                            if (data.getType() == OMMTypes.ENUM)
                            {
                                //ps.print(CURRENT_DICTIONARY.expandedValueFor(fiddef.getFieldId(),    ((OMMEnum)data).getValue()));
                                //ps.print(" (");
                                //ps.print(data);
                               // ps.println(")");
                            }
                            else
                            	parseData(data, ps, tabLevel);
                            
                            
	                            if(fiddef.getName().equals("VALUE_DT1")){
	                        		Streamer.date.put("["+itemName+"]VALUE_DT1", data.toString());
	                        	}
	                        	if(fiddef.getName().equals("VALUE_TS1")){
	                        		Streamer.time.put("["+itemName+"]VALUE_TS1", data.toString());
	                        	}
	                        	if(fiddef.getName().equals("DSPLY_NAME")){
	                        		Streamer.dispName.put("["+itemName+"]DSPLY_NAME", data.toString());
	                        	}
	                        	
	                        	
                            	Symbol symbol = null;
                            	Field field = null;
                            	
                            	for(Symbol s : ItemManager.symbols){
                            		if(itemName.equals(s.getName())){
                            			symbol = s;
                            			break;
                            		}
                            	}
                        		/*if(fiddef.getName().equals("BID")){
                        			System.out.println("TEST");
                        		}*/
                            	
                            	for(Field f : ItemManager.fields){
                            		if(itemName.equals(f.getSymbol().getName()) && fiddef.getName().equals(f.getName())){
                            			field = f;
                            			break;
                            		}
                            	}
                            
                            	if(symbol != null && field != null && data != null && !"".equals(data.toString())){
                            		Quote quote = new Quote();
                                	quote.setSymbol(symbol);
                                	quote.setField(field);
                                	quote.setValNow(Double.valueOf(data.toString().replace(",", "")));
                                	quote.setValLast(Double.valueOf(data.toString().replace(",", "")));
                                	quote.setInsertDate(TimeUtil.getTimeStamp(new Date()));
                                	
                                	String date = Streamer.date.get("["+itemName+"]VALUE_DT1");
                                	String time = Streamer.time.get("["+itemName+"]VALUE_TS1");
                                	String dispName = Streamer.dispName.get("["+itemName+"]DSPLY_NAME");
                                	
                                	Timestamp serverTime = TimeUtil.getTimeStamp(new Date());
                                	
                                	if(date != null && !"".equals(date) && time != null && !"".equals(time)){
                                		serverTime = TimeUtil.getTimeOMM(date+time);
                                		quote.setServerTime(serverTime);
                                	} else {
                                		quote.setServerTime(serverTime);
                                	}
                                	
                                	if(dispName != null && !"".equals(dispName)){
                                		quote.setDisplayName(dispName);
                                	} else {
                                		quote.setDisplayName("-");
                                	}
                                	
                                	QuoteDay quoteDay = ItemManager.quoteDayDao.getBySymbolAndField(itemName, fiddef.getName());
                                	if(quoteDay != null){
                                		quoteDay.setValNow(Double.valueOf(data.toString().replace(",", "")));
                                		quoteDay.setValLast(Double.valueOf(data.toString().replace(",", "")));
                                		quoteDay.setInsertDate(TimeUtil.getTimeStamp(new Date()));
                                		quoteDay.setServerTime(serverTime);
                                		quoteDay.setDisplayName(dispName);
                                		ItemManager.quoteDayDao.update(quoteDay);
                                	} else {
                                		quoteDay = new QuoteDay();
                                		quoteDay.setSymbol(symbol);
                                		quoteDay.setField(field);
                                		quoteDay.setValNow(Double.valueOf(data.toString().replace(",", "")));
                                		quoteDay.setValLast(Double.valueOf(data.toString().replace(",", "")));
                                		quoteDay.setInsertDate(TimeUtil.getTimeStamp(new Date()));
                                		quoteDay.setServerTime(serverTime);
                                		quoteDay.setDisplayName(dispName);
                                		ItemManager.quoteDayDao.save(quoteDay);
                                	}
                                	
                                	
                                	ItemManager.quoteDao.save(quote);
                                	//System.out.println(itemName + " : " + fiddef.getName() + " : " + data + " : " + date + " : "  + time + " : "  + dispName);
                                	
                            	}
                            	
	                            /*List<NowLast> list = new ArrayList<NowLast>();
	                			list.add(new NowLast(data.toString().replace(",", "")));
	                			Streamer.map.put("["+itemName+"]"+fiddef.getName(), list);*/
                        }
                        else
                        {
                            //ps.println("Received field id: " + fe.getFieldId() + " - Not defined in dictionary");
                        }
                    }
                    else
                    {
                        dumpFieldEntryHeader(fe, null, ps, tabLevel);
                        if (fe.getDataType() == OMMTypes.UNKNOWN)
                        {
                            OMMDataBuffer data = (OMMDataBuffer)fe.getData();
                            //ps.println(HexDump.toHexString(data.getBytes(), false));
                        }
                        else
                        // defined data already has type
                        {
                            OMMData data = fe.getData();
                            parseData(data, ps, tabLevel);
                        }
                    }
                    ps.flush();
                }
                    break;
                case OMMTypes.ELEMENT_ENTRY:
                    dumpElementEntryHeader((OMMElementEntry)entry, ps, tabLevel);
                    parseData(entry.getData(), ps, tabLevel);
                    break;
                case OMMTypes.MAP_ENTRY:
                    dumpMapEntryHeader((OMMMapEntry)entry, ps, tabLevel);
                    if ((((OMMMapEntry)entry).getAction() != OMMMapEntry.Action.DELETE)
                            && entry.getDataType() != OMMTypes.NO_DATA)
                        parseData(entry.getData(), ps, tabLevel);
                    break;
                case OMMTypes.VECTOR_ENTRY:
                    dumpVectorEntryHeader((OMMVectorEntry)entry, ps, tabLevel);
                    if ((((OMMVectorEntry)entry).getAction() != OMMVectorEntry.Action.DELETE)
                            && (((OMMVectorEntry)entry).getAction() != OMMVectorEntry.Action.CLEAR))
                        parseData(entry.getData(), ps, tabLevel);
                    break;
                case OMMTypes.FILTER_ENTRY:
                    dumpFilterEntryHeader((OMMFilterEntry)entry, ps, tabLevel);
                    if (((OMMFilterEntry)entry).getAction() != OMMFilterEntry.Action.CLEAR)
                        parseData(entry.getData(), ps, tabLevel);
                    break;
                default:
                    dumpEntryHeader(entry, ps, tabLevel);
                    parseData(entry.getData(), ps, tabLevel);
                    break;
            }
        }
        catch (OMMException e)
        {
            //ps.println("ERROR Invalid data: " + e.getMessage());
        }
    }

    private static final void dumpEntryHeader(OMMEntry entry, PrintStream ps, int tabLevel)
    {
        dumpIndent(ps, tabLevel);
        //ps.print(OMMTypes.toString(entry.getType()));
       // ps.print(": ");
        if (entry.getType() == OMMTypes.SERIES_ENTRY){
            //ps.println();
        }
        // else array entry value is on same line
    }

    private static final void dumpFieldEntryHeader(OMMFieldEntry entry, FidDef def, PrintStream ps,
            int tabLevel)
    {
        dumpIndent(ps, tabLevel);
        //ps.print(OMMTypes.toString(entry.getType()));
       // ps.print(" ");
        //ps.print(entry.getFieldId());
        if (def == null)
        {
            //ps.print(": ");
        }
        else
        {
            //ps.print("/");
            //ps.print(def.getName());
            //ps.print(": ");
            if ((def.getOMMType() >= OMMTypes.BASE_FORMAT) || (def.getOMMType() == OMMTypes.ARRAY)){
                //ps.println();
            }
        }
    }

    private static final void dumpElementEntryHeader(OMMElementEntry entry, PrintStream ps,
            int tabLevel)
    {
        dumpIndent(ps, tabLevel);
        //ps.print(OMMTypes.toString(entry.getType()));
       // ps.print(" ");
       // ps.print(entry.getName());
       // ps.print(": ");
        if ((entry.getDataType() >= OMMTypes.BASE_FORMAT)
                || (entry.getDataType() == OMMTypes.ARRAY)){
           // ps.println();
        }

    }

    private static final void dumpFilterEntryHeader(OMMFilterEntry entry, PrintStream ps,
            int tabLevel)
    {
        dumpIndent(ps, tabLevel);
        //ps.print(OMMTypes.toString(entry.getType()));
        //ps.print(" ");
        //ps.print(entry.getFilterId());
       // ps.print(" (");
       // ps.print(OMMFilterEntry.Action.toString(entry.getAction()));
        if (entry.has(OMMFilterEntry.HAS_PERMISSION_DATA)){
            //ps.print(", HasPermissionData");
        }
        if (entry.has(OMMFilterEntry.HAS_DATA_FORMAT)){
            //ps.print(", HasDataFormat");
        }
       // ps.println(") : ");

        String flagsString = ExampleUtil.filterEntryFlagsString(entry);
        dumpIndent(ps, tabLevel);
        //ps.print("flags: ");
        //ps.println(flagsString);

    }

    private static final void dumpMapEntryHeader(OMMMapEntry entry, PrintStream ps, int tabLevel)
    {
        dumpIndent(ps, tabLevel);
       // ps.print(OMMTypes.toString(entry.getType()));
       // ps.print(" (");
       // ps.print(OMMMapEntry.Action.toString(entry.getAction()));
        if (entry.has(OMMMapEntry.HAS_PERMISSION_DATA)){
            //ps.print(", HasPermissionData");
        }
        //ps.println(") : ");

        String flagsString = ExampleUtil.mapEntryFlagsString(entry);
        dumpIndent(ps, tabLevel);
        //ps.print("flags: ");
        //ps.println(flagsString);

        dumpIndent(ps, tabLevel);
        //ps.print("Key: ");
        parseData(entry.getKey(), ps, 0);
        dumpIndent(ps, tabLevel);
       // ps.println("Value: ");
    }

    private static final void dumpVectorEntryHeader(OMMVectorEntry entry, PrintStream ps,
            int tabLevel)
    {
        dumpIndent(ps, tabLevel);
        //ps.print(OMMTypes.toString(entry.getType()));
        //ps.print(" ");
       // ps.print(entry.getPosition());
       // ps.print(" (");
       // ps.print(OMMVectorEntry.Action.vectorActionString(entry.getAction()));
        if (entry.has(OMMVectorEntry.HAS_PERMISSION_DATA)){
            //ps.print(", HasPermissionData");
        }
        //ps.println(") : ");

        String flagsString = ExampleUtil.vectorEntryFlagsString(entry);
        dumpIndent(ps, tabLevel);
        //ps.print("flags: ");
       //ps.println(flagsString);

    }

    public static final void parseAnsiPageData(OMMData data, PrintStream ps, int tabLevel)
    {
        boolean newPage = false;
        if (CURRENT_PAGE == null)
        {
            CURRENT_PAGE = new Page();
            newPage = true;
        }

        Vector<PageUpdate> pageUpdates = new Vector<PageUpdate>();
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        CURRENT_PAGE.decode(bais, pageUpdates);
        if (newPage){
            //ps.println(CURRENT_PAGE.toString()); // print the page if it is a refresh message
        }
        else
        {
            // print the update string
            Iterator<PageUpdate> iter = pageUpdates.iterator();
            while (iter.hasNext())
            {
                PageUpdate u = (PageUpdate)iter.next();
                StringBuilder buf = new StringBuilder(80);
                for (short k = u.getBeginningColumn(); k < u.getEndingColumn(); k++)
                {
                    buf.append(CURRENT_PAGE.getChar(u.getRow(), k));
                }
                if (!(buf.toString()).equalsIgnoreCase(""))
                {
                    dumpIndent(ps, tabLevel);
                    //ps.println("Update String: " + buf.toString() + " (Row: " + u.getRow()  + ", Begin Col: " + u.getBeginningColumn() + ", End Col: "     + u.getEndingColumn() + ")");
                }
            }
        }
    }

	public static String getItemName() {
		return itemName;
	}

	public static void setItemName(String itemName) {
		GenericOMMParser.itemName = itemName;
	}

}
