package com.metrics.rfa.pagedisplay;

import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.OptionDao;
import com.metrics.MDOL.dbo.Option;
import com.metrics.rfa.framework.sub.SubAppContext;
import com.metrics.rfa.utility.CommandLine;

/**
 * This is a main class to start GUI application. It uses {@link PagePanel} to
 * build GUI display. It also creates
 * {@link com.reuters.rfa.example.framework.sub.SubAppContext SubAppContext} to
 * initialize RFA.
 */
public class PageDisplay implements Runnable
{
    protected PagePanel _panel;
    protected String _serviceName;
    protected SubAppContext _subAppContext;
    protected JTextField _symbolField;
    
    protected ApplicationContext _context;	
    protected OptionDao optionDao;
    protected Option opt;
    static String enumType;
    static String sessionName;

    /**
     * Context and GUI initialization
     */
    
    public PageDisplay(ApplicationContext context){
    	_context = context;
    	optionDao = (OptionDao) _context.getBean("optionDaoProxy");
    	opt = optionDao.getOptionBy(1);
    	enumType = opt.getEnumType();
    	sessionName = opt.getSessionName();
    	
    	Thread t = new Thread(this);
    	t.start();
    }
    
    @Override
	public void run() {
		// TODO Auto-generated method stub
		addCommandLineOptions();
		String[] argv = {};
        CommandLine.setArguments(argv);
        init();
	}
    
    public void init()
    {
        short rows = (short)CommandLine.intVariable("rows");
        short cols = (short)CommandLine.intVariable("cols");
        int fontSize = CommandLine.intVariable("fontSize");
        _serviceName = opt.getServiceName();
        _panel = new PagePanel(_serviceName, rows, cols, fontSize);

        final JFrame appFrame = new JFrame("PageDisplay");
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel toolbar = new Panel();
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

        _symbolField = new JTextField(10);
        toolbar.add(new JLabel("Enter Symbol:"));
        toolbar.add(_symbolField);
        _symbolField.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if (_symbolField.getText().equals(""))
                    return;
                _panel.newSymbolEntered(_symbolField.getText());
            }
        });

        appFrame.getContentPane().add("North", toolbar);
        appFrame.getContentPane().add("Center", _panel._panel);
        appFrame.setVisible(false);
        appFrame.pack();

        try
        {
            _subAppContext = SubAppContext.createOMM(_panel._statusBar.printStream());
        }
        catch (RuntimeException rex)
        {
            System.out.println("ERROR: Could not create context. Connection type: "
                    + CommandLine.variable("type"));
            System.out.println("type must be OMM. Program exiting...");
            System.exit(1);
        }
        _panel._page._appContext = _subAppContext;
        _subAppContext.runAwt();
        _panel.newSymbolEntered("FOREX");
    }

    /**
     * Initialize and set the default for the command line options.
     * 
     * @see SubAppContext#addCommandLineOptions()
     * 
     */
    public static void addCommandLineOptions()
    {
        SubAppContext.addCommandLineOptions();
        CommandLine.addOption("enumType", enumType, "enumtype.def filename");
        CommandLine.addOption("session", sessionName, "Session name to use");
        CommandLine.addOption("fontSize", 12, "font size");
        CommandLine.addOption("rows", 25, "rows in page");
        CommandLine.addOption("cols", 80, "columns in page");
    }


}
