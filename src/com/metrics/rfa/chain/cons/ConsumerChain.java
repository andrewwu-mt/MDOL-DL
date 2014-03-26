package com.metrics.rfa.chain.cons;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.ChainDao;
import com.metrics.MDOL.dao.LinkDao;
import com.metrics.MDOL.dao.OptionDao;
import com.metrics.MDOL.dbo.Chain;
import com.metrics.MDOL.dbo.Option;
import com.metrics.rfa.utility.CommandLine;
import com.reuters.rfa.common.Context;

public class ConsumerChain implements Runnable
{
	ApplicationContext _context;
    LinkDao linkDao;
    ChainDao chainDao;
    OptionDao optionDao;
    Option opt;
    static String serviceName;
    static String enumType;
    static String sessionName;
    
    boolean init = true;
    List<String> chainOri = new ArrayList<String>();
    
    public ConsumerChain(ApplicationContext context)
    {
    	_context = context;
        linkDao = (LinkDao) _context.getBean("linkDaoProxy");
        chainDao = (ChainDao) _context.getBean("chainDaoProxy");
        optionDao = (OptionDao) _context.getBean("optionDaoProxy");
        opt = optionDao.getOptionBy(1);
        serviceName = opt.getServiceName();
        enumType = opt.getEnumType();
        sessionName = opt.getSessionName();

    	System.out.println("*****************************************************************************");
        System.out.println("*                             Begin Chain                                 *");
        System.out.println("*****************************************************************************");
        Thread ch = new Thread(this);
        ch.start();
    }

    public void init(boolean isChain)
    {
    	boolean _isChain = isChain;
    	
        Context.initialize();

        final ChainConsFrame chain = new ChainConsFrame(_isChain, this, _context);
        chain.init();
        
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					int freq = opt.getFreq() * 1000;

					List<Chain> chainList = chainDao.getAllChain();
					List<String> chainListName = new ArrayList<String>();
					
					if(init){
						if(chainList != null){
							for(int i=0 ; i<chainList.size() ; i++){
								Chain chain = chainList.get(i);
								String chainName = chain.getName();
								chainOri.add(chainName);
							}
						}
						init = false;
					}
					
					if(chainList != null){
						for(int i=0 ; i<chainList.size() ; i++){
							Chain chain = chainList.get(i);
							String chainName = chain.getName();
							chainListName.add(chainName);
						}
						
						if(!chainOri.containsAll(chainListName)){
							List<String> remChain = new ArrayList<String>();
							for(int i=0 ; i<chainListName.size() ; i++){
								String chName = chainListName.get(i);
								if(!chainOri.contains(chName)){
									remChain.add(chName);
								}
							}
							
		                    for(int i=0 ; i<remChain.size() ; i++){
		                    	String item = remChain.get(i);
		                    	 ItemManager _itemManager = new ItemManager(chain, _context);
		                         _itemManager.sendRequest(item, serviceName);
		                    }
		                    chainOri = chainListName;
						} else {
							System.out.println("No new chain");
						}
					} else {
						System.out.println("chainList is null");
					}		

					try {
						Thread.sleep(freq);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
        });
        t.start();
        
    }

    protected void cleanUp()
    {
        Context.uninitialize();
    }

    static void addCommandLineOptions()
    {
        CommandLine.addOption("debug", false, "enable debug tracing");
        CommandLine.addOption("session", sessionName, "Session name to use");
        CommandLine.addOption("serviceName", serviceName, "service to request");
        CommandLine.addOption("itemName", "", "List of items to open separated by ','.");
        CommandLine.addOption("mmt", "MARKET_PRICE", "Message Model Type");
        CommandLine.addOption("attribInfoInUpdates", false,
                              "Ask provider to send OMMAttribInfo with update and status messages");
        String username = "rfa";
        try
        {
            //username = System.getProperty("user.name");
        }
        catch (Exception e)
        {
        }
        CommandLine.addOption("user", username, "DACS username for login");
        String defaultPosition = "1.1.1.1/net";
        try
        {
            defaultPosition = InetAddress.getLocalHost().getHostAddress() + "/"
                    + InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e)
        {
        }
        CommandLine.addOption("position", defaultPosition, "DACS position for login");
        CommandLine.addOption("application", "256", "DACS application ID for login");
        CommandLine.addOption("rdmFieldDictionary", "RDMFieldDictionary",
                              "RDMFieldDictionary filename");
        CommandLine.addOption("enumType", enumType, "enumtype.def filename");
        CommandLine.addOption("runTime", -1,
                              "How long application should run before exiting (in seconds)");
    }

	@Override
	public void run() {
		addCommandLineOptions();
		String[] argv = {};
        CommandLine.setArguments(argv);

        init(true);
	}
}
