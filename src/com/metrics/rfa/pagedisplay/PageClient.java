package com.metrics.rfa.pagedisplay;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrics.MDOL.dao.PageUpdateDao;
import com.metrics.MDOL.dbo.PageUpdate;
import com.metrics.MDOL.util.TimeUtil;
import com.metrics.rfa.framework.sub.NormalizedEvent;
import com.metrics.rfa.framework.sub.SubAppContext;
import com.metrics.rfa.utility.gui.Status;
import com.reuters.rfa.ansipage.Page;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;

/**
 * This is a client class that register page(input from user) into RFA using
 * {@link SubAppContext}. This class implements {@link Client} to process event
 * from the back-end server infrastructure. It uses
 * {@link com.reuters.rfa.example.ansipage ANSI Page package} to parse data and
 * use {@link PagePanel} to display data.
 * 
 */
public class PageClient implements Client
{
    boolean _active;
    boolean _hasData;
    boolean _updater = false;
    Page _page;
    PagePanel _panel;
    PrintStream _printStream;
    Status _status;
    Handle _handle;
    SubAppContext _appContext;
    String _itemName;
    String _serviceName;
    
	String[] patterns = {"?=P" , "?>P" , "??P" , "?@P" , "?AP" ,  "?BP" , "?CP" , "?DP" , "?EP" , "?FP" , "?GP" , "?HP" , "?IP" , "?JP" , "?KP" , "?LP" , "?MP" , "?NP" , "?OP" , "?PP" , "?QP" , "?RP" , "?SP"};
	String[] rows = {"=" , ">" , "?" , "@" , "A" ,  "B" , "C" , "D" , "E" , "F" , "G" , "H" , "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S"};

    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");	
    PageUpdateDao pageUpdateDao = (PageUpdateDao) context.getBean("pageUpdateDaoProxy");

    List<String> toBeUpdatedList = new ArrayList<String>();

    public PageClient(PagePanel panel, String serviceName, short rows, short cols)
    {
        _active = false;
        _hasData = false;
        _panel = panel;
        _printStream = _panel._statusBar.printStream();
        _status = _panel._statusBar;
        _itemName = "";
        _serviceName = serviceName;
        _page = new Page(rows, cols);
    }

    public void processEvent(Event event)
    {
        _printStream.println(event);
        
        NormalizedEvent nevent = _appContext.getNormalizedEvent(event);
        int msgType = nevent.getMsgType();

        switch (event.getType())
        {
            case Event.OMM_ITEM_EVENT:
            {
                if (msgType == NormalizedEvent.REFRESH)
                {
                    _status.setStatus(_itemName + ": " + nevent.getStatusText());
                    byte[] data = nevent.getPayloadBytes();
                    parseData(data, false);
                    _panel.repaintCanvas();
                    _hasData = true;
                }
                else if (msgType == NormalizedEvent.UPDATE)
                {
                    byte[] data = nevent.getPayloadBytes();
                    parseData(data, true);
                }
                else if (msgType == NormalizedEvent.STATUS)
                {
                    _status.setStatus(_itemName + ": " + nevent.getStatusText());
                    _panel.repaintCanvas();
                    if (nevent.isClosed())
                    {
                        if (_page != null)
                        {
                            _panel.repaintCanvas();
                            _active = false;
                            _handle = null;
                            _hasData = false;
                        }
                    }
                    else if (nevent.isRename())
                    {
                        // msg.getState().getStreamState() ==
                        // OMMState.Stream.REDIRECT)
                        String attribInfoName = nevent.getNewItemName();
                        _status.setStatus(_itemName + ": renamed to: " + attribInfoName);
                        _itemName = attribInfoName;
                    }
                }
            }
                break;
            default:
                // ignore other events
        }
    }

    /**
     * Indicates whether the page is fully populated or not
     */
    public boolean hasData()
    {
        return _hasData;
    }

    public boolean active()
    {
        return _active;
    }

    /**
     * Unregister the current page
     */
    public void unsubscribe()
    {
        if (_handle != null)
        {
            _appContext.unregister(_handle);
            _handle = null;
            _active = false;
        }
    }

    /**
     * Register page to RFA by using {@link SubAppContext}
     * 
     * @param name an instrument (Reuter Instrument Code or RIC)
     */
    public void subscribe(String name)
    {
        _itemName = name;
        _handle = _appContext.register(this, _serviceName, _itemName, true);
        _active = true;
    }

    // Decode the page data received and get the list of PageUpdates.
    // Apply each PageUpdate to the display panel.
    protected void parseData(byte[] data, boolean update)
    {
        Timestamp time = TimeUtil.getTimeStamp(new Date());
        
        if(!_updater){
    		List<Byte> byteList = new ArrayList<Byte>();
    		int trig = 0;
    		for(int i=0 ; i<data.length ; i++){
    			
    			if(data[i] == 1){
    				if(i < data.length){
    					if(data[i+1] == 61){
    						trig = 1;
    					}
    				}
    				
    			}
    			if(trig == 1){
    				byteList.add(data[i]);
    			}
    		}
    		
    		for(int i=0 ; i<byteList.size() ; i++){
    			if(byteList.get(i) == 1){
    				byteList.set(i, (byte) 63);
    			}
    		}
    		
    		byte[] byteData = new byte[byteList.size()];
    		for(int i=0 ; i<byteData.length ; i++){
    			byteData[i] = byteList.get(i);
    		}
    		
    		String dataString = new String(byteData);
    		
    		for(int i=0 ; i<patterns.length ; i++){
    			if(i != patterns.length-1){
    				int rowNum = i+1;
    				String newData = dataString.substring(dataString.indexOf(patterns[i])+3, dataString.indexOf(patterns[i+1]));
    				PageUpdate  pageUpdate= new PageUpdate();
    				pageUpdate.setRow(rowNum);
    				pageUpdate.setValue(newData);
    				pageUpdate.setInsertDate(time);
    				pageUpdateDao.save(pageUpdate);
    				toBeUpdatedList.add(newData);
    				System.out.println(newData);
    			}
    			
    		}
    		
        	_updater = true;
        } else {
        	List<Byte> updateList = new ArrayList<Byte>();

    		for(int i=0 ; i<data.length ; i++){
    			if(data[i] > 31){
    				updateList.add(data[i]);
    			}
    		}
    		
    		byte[] byteUpdate = new byte[updateList.size()];
    		for(int i=0 ; i<byteUpdate.length ; i++){
    			byteUpdate[i] = updateList.get(i);
    		}
    		
    		String updateString = new String(byteUpdate);
    		updateString = updateString + "S";
    		int index = updateString.indexOf("=");
    		updateString = updateString.replace(updateString.substring(0 , index), "");
    		
    		
    		
    		for(int i=0 ; i<rows.length ; i++){
    			if(i != rows.length-1){
    				if(updateString.contains(rows[i]) && updateString.contains(rows[i+1])){
    					String newRow = updateString.substring(updateString.indexOf(rows[i])+1, updateString.indexOf(rows[i+1]));
    					int rowNum = i+1;
    					String column = "";
    					String value = "";
    					newRow = newRow.replace("[", ",");
    					String toBeUpdated = toBeUpdatedList.get(i);
    					
    					String[] updates = newRow.split(",");
    					for(int j=1 ; j<updates.length ; j++){
    						column = updates[j].split("`")[0];
    						value = updates[j].split("`")[1];
    						char[] valueChar = value.toCharArray();
    						System.out.println("row : "+rowNum+" column : "+column+" value : "+value);
    						if(valueChar.length == 1){
    							StringBuilder myString = new StringBuilder(toBeUpdated);
    							myString.setCharAt(Integer.valueOf(column), valueChar[0]);
    							toBeUpdated = myString.toString();
        						//System.out.println(toBeUpdated);
    						} else {
    							StringBuilder myString = new StringBuilder(toBeUpdated);
    							for(int k=0 ; k<valueChar.length ; k++){
    								myString.setCharAt(Integer.valueOf(column)+k, valueChar[k]);
    							}
    							toBeUpdated = myString.toString();
        						//System.out.println(toBeUpdated);
    						}
    					}
    					toBeUpdatedList.set(i, toBeUpdated);
    				}
    			}
    			
    		}
    		
    		for(int i=0 ; i<toBeUpdatedList.size() ; i++){
    			PageUpdate pageUpd = new PageUpdate();
    			int rowNum = i+1;
    			String value = toBeUpdatedList.get(i);
    			pageUpd.setRow(rowNum);
    			pageUpd.setValue(value);
    			pageUpd.setInsertDate(time);
    			pageUpdateDao.save(pageUpd);
    		}

        }
        
		
		
    }

    
    public short getCols()
    {
        return _page.getNumberOfColumns();
    }

    public short getRows()
    {
        return _page.getNumberOfRows();
    }

    public Page getPage()
    {
        return _page;
    }
}
