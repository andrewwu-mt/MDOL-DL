package com.metrics.rfa.quick;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.FieldDao;
import com.metrics.MDOL.dao.FieldListDao;
import com.metrics.MDOL.dao.ItemDao;
import com.metrics.MDOL.dao.OptionDao;
import com.metrics.MDOL.dao.SymbolDao;
import com.metrics.MDOL.dbo.Item;
import com.metrics.MDOL.dbo.Option;
import com.metrics.MDOL.util.Streamer;
import com.metrics.rfa.utility.GenericOMMParser;
import com.reuters.rfa.common.Context;
import com.reuters.rfa.common.DispatchException;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.common.EventSource;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.dictionary.DictionaryException;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.session.Session;
import com.reuters.rfa.session.omm.OMMConsumer;

public class QSConsumer implements Runnable
{
    //RFA objects
    protected Session              _session;
    protected EventQueue           _eventQueue;
    protected OMMConsumer          _consumer;
    protected LoginClient          _loginClient;
    protected ItemManager 		   _itemManager;
    protected OMMEncoder           _encoder;
    protected OMMPool              _pool;
    protected String			   _serviceName;
    
    protected static String[] itemNames1;
    protected ApplicationContext _context;	
    protected SymbolDao symbolDao;
    protected ItemDao itemDao;
    protected OptionDao optionDao;
    protected Option opt;
    protected FieldDao fieldDao;
    protected FieldListDao fieldListDao;

	// class constructor
    public QSConsumer(ApplicationContext context){
    	_context = context;
    	_itemManager = new ItemManager(this, _context);
        symbolDao = (SymbolDao) _context.getBean("symbolDaoProxy");
        itemDao = (ItemDao) _context.getBean("itemDaoProxy");
        optionDao = (OptionDao) _context.getBean("optionDaoProxy");
        fieldDao = (FieldDao) _context.getBean("fieldDaoProxy");
        fieldListDao = (FieldListDao) _context.getBean("fieldListDaoProxy");
        opt = optionDao.getOptionBy(1);
        
        _serviceName = opt.getServiceName();
        
        System.out.println("*****************************************************************************");
        System.out.println("*                             Begin Program                                 *");
        System.out.println("*****************************************************************************");
        Thread qs = new Thread(this);
        qs.start();
    }
    
    public void init(){
    	// 1. Initialize context
        Context.initialize();
        
        try {
            Preferences.importPreferences(new DataInputStream(new FileInputStream("FeedConfig.xml")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (InvalidPreferencesFormatException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        
        // 2. Create an Event Queue
        _eventQueue = EventQueue.create( "myEventQueue" );
        
        // 3. Initialize logger
        // The application utilizes logger from RFA. User sets the level 
        // to the desired value. The default is "Info".
        Logger logger = Logger.getLogger("com.reuters.rfa");
        Level level = Level.INFO;
        logger.setLevel(level);
        Handler[] handlers = logger.getHandlers();

        if(handlers.length == 0) 
        {
        	Handler handler = new ConsoleHandler();
        	handler.setLevel(level); 
        	logger.addHandler(handler);            	
        }

        for( int index = 0; index < handlers.length; index++ )
        {
        	handlers[index].setLevel(level);
        }

        // 4. Acquire a Session
        _session = Session.acquire( opt.getSessionName());
        if ( _session == null )
        {
            System.out.println( "Could not acquire session." );
            Context.uninitialize();
            System.exit(1);
        }

        // 5. Create an OMMConsumer event source
        _consumer = (OMMConsumer) _session.createEventSource(EventSource.OMM_CONSUMER, "myOMMConsumer", true);

        // 6. Load dictionaries
    	// Application may choose to down-load the enumtype.def and RWFFldDictionary
    	// This example program loads the dictionaries from files.
    	String fieldDictionaryFilename = "RDMFieldDictionary";
    	String enumDictionaryFilename = opt.getEnumType();
    	try 
    	{
    		GenericOMMParser.initializeDictionary(fieldDictionaryFilename, enumDictionaryFilename);
    	}
    	catch (DictionaryException ex) 
    	{
    		System.out.println("ERROR: Unable to initialize dictionaries");
    		System.out.println(ex.getMessage());
    		if(ex.getCause() != null)
    			System.err.println(": " + ex.getCause().getMessage());
    		cleanup();
    		return;
    	}

        //Create a OMMPool.
        _pool = OMMPool.create();

        //Create an OMMEncoder
        _encoder = _pool.acquireEncoder();
    }
    
    // This method utilizes the LoginClient class to send login request 
    public void login()
    {
        //Initialize client for login domain.
        _loginClient = new LoginClient(this);

    	//Send login request
    	_loginClient.sendRequest();
    }

    // This method is called by _loginClient upon receiving successful login response.
    public void processLogin()
    {
            System.out.println("QSConsumerDemo"+" Login successful");
            // The application successfully logged in 
            // Now we can send the item(s) request
            itemRequests();
    }

    // This method is called when the login was not successful
    // The application exits
    public void loginFailure()
    {
    	System.out.println("OMMConsumerDemo"+": Login has been denied / rejected / closed");
    	System.out.println("OMMConsumerDemo"+": Preparing to clean up and exiting");
    	_loginClient = null;
    	cleanup();
    }

    // This method utilizes ItemManager class to request items
	public void itemRequests()
	{
        _itemManager.sendRequest();
	}
	
	public void addItem()
	{
	   
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
    	
/*		StringBuilder itemStr = new StringBuilder();
		
		for(String item: itemList){
		    if(itemStr.length() > 0){
		    	itemStr.append(", ");
		    }
		    itemStr.append(item);
		}
		
		String items = itemStr.toString();
		
		items = items.replace(" ", "");
*/		  
		//Compare with Streamer.items
		
		StringBuilder itemOri = new StringBuilder();
		
		for(String itemori : Streamer.items){
		    if(itemOri.length() > 0){
		    	itemOri.append(", ");
		    }
		    itemOri.append(itemori);
		}
		
		String itemsOri = itemOri.toString();
		
		itemsOri = itemsOri.replace(" ", "");
		
		if(!(items1).equals(itemsOri)){
			_itemManager.closeRequest();
			
			ItemManager.symbols = symbolDao.getAllActiveSymbol();
			ItemManager.fields = fieldDao.getAll();
			ItemManager.items = itemDao.getAllActiveItem();
			ItemManager.fieldLists = fieldListDao.getAllList();
			ItemManager.itemNames1 = items1.split(",");
			//ItemManager.setItemNames1(items1.split(","));

	        _itemManager.request();	
	        //Streamer.map.clear();
	        System.out.println("QSConsumer : Item Updated");
		} else {
			//System.out.println("QSConsumer : Nothing changed");
		}
	}
	
	// This method dispatches events
    public void run()
    {
        init();
        login();
        
	    Thread add = new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					Option option = optionDao.getOptionBy(1);
					int freq = option.getFreq();
					int interval = freq * 1000;
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					addItem();
				}
			};
        });
	    add.start();
	    
        while(true)
        try
        {
            _eventQueue.dispatch( 1000 );
        }
        catch (DispatchException de )
        {
            System.out.println("EventQueue has been deactivated");
            return;
        }
    }

	public void cleanup()
    {
        System.out.println(Context.string());

        _eventQueue.deactivate();

        if (_itemManager != null)
        	_itemManager.closeRequest();

    	if (_loginClient != null)
    		_loginClient.closeRequest();
    	
    	_eventQueue.destroy();
    	
    	if (_consumer != null)
			_consumer.destroy();

        _session.release();

        Context.uninitialize();

        System.out.println(getClass().toString() + " exiting");
		System.exit(0);
    }

    public EventQueue getEventQueue()
    {
        return _eventQueue;
    }

    public OMMConsumer getOMMConsumer()
    {
        return _consumer;
    }

    public OMMEncoder getEncoder()
    {
        return _encoder;
    }

    public OMMPool getPool()
    {
        return _pool;
    }

    public Handle getLoginHandle()
    {
    	if ( _loginClient != null )
    	{
    		return _loginClient.getHandle();
    	}
    	
    	return null;
    }
    
    protected String getServiceName()
    {
    	return _serviceName;
    }

}
