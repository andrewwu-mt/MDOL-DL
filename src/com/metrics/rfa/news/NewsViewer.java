package com.metrics.rfa.news;


import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.OptionDao;
import com.metrics.MDOL.dbo.Option;
import com.metrics.rfa.framework.sub.SubAppContext;
import com.metrics.rfa.framework.sub.SubAppContextClient;
import com.metrics.rfa.utility.CommandLine;
import com.reuters.rfa.common.Handle;



/**
 * <p>
 * This is a start point to run the NewsViewer application.
 * </p>
 * 
 * The class is responsible for the following actions:
 * <ul>
 * <li>Set up and obtain command line options
 * <li>Initialize the GUI
 * <li>Create the {@link SubAppContext}
 * <li>Release the Session's resources when closing the GUI
 * </ul>
 * 
 **/
public class NewsViewer implements SubAppContextClient {
    // Configuration
    protected static String _serviceName;
    protected String _itemName;

    // RFA objects
    protected Handle _itemHandle;
    
    protected SubAppContext _appContext;
    private ApplicationContext context;
    
    public NewsViewer(ApplicationContext context) {
        // Read options from the command line
        //_serviceName = CommandLine.variable("serviceName");
    	this.context = context;
    	loadDatabaseOption();
    	CommandLine.setArguments(new String[] {});
        _itemName = CommandLine.variable("itemName");
        _appContext = SubAppContext.createOMM(System.out);
        _appContext.setAutoDictionaryDownload();
        _appContext.setCompletionClient(this);
    }

    /**
     * Initializes GUI components
     * 
     */
    public void init() {}

    public void processComplete() {
        // MR rfaj0838: Make GUI visible at start up.
        // _frame.setVisible(true);
        System.out.println("Subscribing to " + _itemName);
        NewsHeadlineClient myClient = new NewsHeadlineClient(_appContext, context);
 
        _appContext.register(myClient, _serviceName, _itemName, true);
    }

    public void cleanup() {
    	System.out.println(new Date() + "stop download...");
        _appContext.cleanup();
    }

    public void run() {
    	System.out.println(new Date() + "start download...");
        _appContext.runAwt();
    }

    public static void addCommandLineOptions(String serviceName, String sessionName, String enumType, String userName) {
        SubAppContext.addCommandLineOptions();
        
        if(serviceName != null && !"".equals(serviceName)) {
        	_serviceName = serviceName;
        	CommandLine.changeDefault("serviceName", _serviceName);
        }
        
        if(sessionName != null && !"".equals(sessionName)) {
        	CommandLine.changeDefault("session", sessionName);
        }
        
        if(enumType != null && !"".equals(enumType)) {
        	CommandLine.changeDefault("enumType", enumType);
        }
        
        if(userName != null && !"".equals(userName)) {
        	_serviceName = serviceName;
        	CommandLine.changeDefault("user", userName);
        }
        
        CommandLine.addOption("itemName", "N2_UBMS", "news item name to request");
        
    }
    
    private void loadDatabaseOption() {
    	OptionDao optionDao = (OptionDao) context.getBean("optionDaoProxy");
    	Option option = optionDao.getOptionBy(1);
    	addCommandLineOptions(option.getServiceName(), option.getSessionName(), option.getEnumType(), option.getUserName());
    }
}
