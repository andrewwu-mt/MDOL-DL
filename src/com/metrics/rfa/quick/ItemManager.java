package com.metrics.rfa.quick;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.ItemDao;
import com.metrics.MDOL.dao.SymbolDao;
import com.metrics.MDOL.dbo.Item;
import com.metrics.MDOL.util.Streamer;
import com.metrics.rfa.utility.GenericOMMParser;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.rdm.RDMInstrument;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.session.omm.OMMItemEvent;
import com.reuters.rfa.session.omm.OMMItemIntSpec;

// This class is a Client implementation that is utilized to handle item requests
// and responses between application and RFA.
// An instance of this class is created by QSConsumerDemo.
// This class performs the following functions:
// - Creates and encodes item request messages and registers the client (itself) 
// - with RFA (method sendRequest()). The registration will cause RFA
//  to send item open request. RFA will return back a handle instance.
// This application will request two items - TRI.N and MSFT.O.
// - Unregisters the client in RFA (method closeRequest()).
// - Processes events for this client (method processEvent()). processEvent() method
// must be implemented by a class that implements Client interface.
//
// The class keeps the following members:
// ArrayList<Handle> _itemHandles - handles returned by RFA on registering the items
//							application uses this handles to identify the items
// QSConsumerDemo _mainApp - main application class

public class ItemManager implements Client
{
    ArrayList<Handle> _itemHandles;
    QSConsumer _mainApp;
    public static String[] itemNames;
    public static String[] itemNames1;

    ApplicationContext _context;
    SymbolDao symbolDao;
    ItemDao itemDao;

    private	String	_className = "ItemManager";

    // constructor
    public ItemManager(QSConsumer mainApp , ApplicationContext context)
    {
    	_context = context;
        _mainApp = mainApp;
        symbolDao = (SymbolDao) _context.getBean("symbolDaoProxy");
        itemDao = (ItemDao) _context.getBean("itemDaoProxy");
        _itemHandles = new ArrayList<Handle>();
    }

    // creates streaming request messages for items and register them to RFA
    public void sendRequest()
    {
        System.out.println(_className+".sendRequest: Sending item requests");
        String serviceName = _mainApp._serviceName;
        
        short msgModelType = RDMMsgTypes.MARKET_PRICE;

        OMMItemIntSpec ommItemIntSpec = new OMMItemIntSpec();

        //Preparing item request message
        OMMPool pool = _mainApp.getPool();
        OMMMsg ommmsg = pool.acquireMsg();

        ommmsg.setMsgType(OMMMsg.MsgType.REQUEST);
        ommmsg.setMsgModelType(msgModelType);
        ommmsg.setIndicationFlags(OMMMsg.Indication.REFRESH);
        ommmsg.setPriority((byte) 1, 1);
        
        // Setting OMMMsg with negotiated version info from login handle        
        if( _mainApp.getLoginHandle() != null )
        {
        	ommmsg.setAssociatedMetaInfo(_mainApp.getLoginHandle());
        }

        //SQL
		List<Item> symbols = itemDao.getAllActiveItem();
		
		StringBuilder itemStr1 = new StringBuilder();
		
		for(Item items: symbols){
            if(itemStr1.length() > 0){
            	itemStr1.append(", ");
            }
            itemStr1.append(items.getName());
        }
		
		String items1 = itemStr1.toString();
	   	
		items1 = items1.replace(" ", "");
    	
    	itemNames1 = items1.split(",");
    	
    	Streamer.setItems(itemNames1);
    	
		List<String> itemList = new ArrayList<String>();
		
		for(int i=0 ; i<symbols.size() ; i++){
			String s = symbols.get(i).getName();
			itemList.add(s);
		}
		
        StringBuilder itemStr = new StringBuilder();
        
        for(String item: itemList){
            if(itemStr.length() > 0){
            	itemStr.append(", ");
            }
            itemStr.append(item);
        }
        
        
        String items = itemStr.toString();
   	
    	items = items.replace(" ", "");
    	
    	itemNames = items.split(",");
    	
    	
        // register for each item
        for (int i = 0; i < itemNames.length; i++)
        {
        	String itemName = itemNames[i];
            System.out.println(_className+": Subscribing to " + itemName);

        	ommmsg.setAttribInfo(serviceName, itemName, RDMInstrument.NameType.RIC);

        	//Set the message into interest spec
            ommItemIntSpec.setMsg(ommmsg);
            Handle  itemHandle = _mainApp.getOMMConsumer().registerClient(
            		_mainApp.getEventQueue(), ommItemIntSpec, this, null);
            _itemHandles.add(itemHandle);
        }
        pool.releaseMsg(ommmsg);
    }

    public void request()
    {
        System.out.println(_className+".sendRequest: Sending item requests");
        String serviceName = _mainApp._serviceName;
        
        short msgModelType = RDMMsgTypes.MARKET_PRICE;

        OMMItemIntSpec ommItemIntSpec = new OMMItemIntSpec();

        //Preparing item request message
        OMMPool pool = _mainApp.getPool();
        OMMMsg ommmsg = pool.acquireMsg();

        ommmsg.setMsgType(OMMMsg.MsgType.REQUEST);
        ommmsg.setMsgModelType(msgModelType);
        ommmsg.setIndicationFlags(OMMMsg.Indication.REFRESH);
        ommmsg.setPriority((byte) 1, 1);
        
        // Setting OMMMsg with negotiated version info from login handle        
        if( _mainApp.getLoginHandle() != null )
        {
        	ommmsg.setAssociatedMetaInfo(_mainApp.getLoginHandle());
        }
    	
        
    	Streamer.setItems(itemNames1);
    	
    	List<String> itemList = new ArrayList<String>();
		
		for(int i=0 ; i<Streamer.items.length ; i++){
			String s = Streamer.items[i];
			itemList.add(s);
			
		}
		
    	
        
        StringBuilder itemStr = new StringBuilder();
        
        for(String item: itemList){
            if(itemStr.length() > 0){
            	itemStr.append(", ");
            }
            itemStr.append(item);
        }
        
        
        String items = itemStr.toString();
   	
    	items = items.replace(" ", "");
    	
    	itemNames = items.split(",");
    	
        // register for each item
        for (int i = 0; i < itemNames.length; i++)
        {
        	String itemName = itemNames[i];
            System.out.println(_className+": Subscribing to " + itemName);

        	ommmsg.setAttribInfo(serviceName, itemName, RDMInstrument.NameType.RIC);

        	//Set the message into interest spec
            ommItemIntSpec.setMsg(ommmsg);
            Handle  itemHandle = _mainApp.getOMMConsumer().registerClient(
            		_mainApp.getEventQueue(), ommItemIntSpec, this, null);
            _itemHandles.add(itemHandle);
        }
        pool.releaseMsg(ommmsg);
    }
    
    // Unregisters/unsubscribes login handle
    public void closeRequest()
    {
    	while (!_itemHandles.isEmpty())
    	{
    		Handle itemHandle = _itemHandles.remove(0);
            _mainApp.getOMMConsumer().unregisterClient(itemHandle);
        }
        _itemHandles.clear();
    }

    // This is a Client method. When an event for this client is dispatched,
    // this method gets called.
    public void processEvent(Event event)
    {
    	// Completion event indicates that the stream was closed by RFA    	
    	if (event.getType() == Event.COMPLETION_EVENT) 
    	{
    		//System.out.println(_className+": Receive a COMPLETION_EVENT, "+ event.getHandle());
    		return;
    	}

    	// check for an event type; it should be item event.
        //System.out.println(_className+".processEvent: Received Item Event");
        if (event.getType() != Event.OMM_ITEM_EVENT) 
        {
            //System.out.println("ERROR: "+_className+" Received an unsupported Event type.");
            _mainApp.cleanup();
            return;
        }

        OMMItemEvent ie = (OMMItemEvent) event;
        OMMMsg respMsg = ie.getMsg();
        
        for(int i=0 ; i<_itemHandles.size() ; i++){
        	if(event.getHandle().equals(_itemHandles.get(i))){
        		GenericOMMParser.setItemName(itemNames[i]);
        		GenericOMMParser.parse(respMsg);
        		break;
        	}
        }
      
        //GenericOMMParser.parse(respMsg);
        
        
    }

	public static String[] getItemNames() {
		return itemNames;
	}

	public static void setItemNames(String[] itemNames) {
		ItemManager.itemNames = itemNames;
	}

	public static String[] getItemNames1() {
		return itemNames1;
	}

	public static void setItemNames1(String[] itemNames1) {
		ItemManager.itemNames1 = itemNames1;
	}
	
	
	
}
