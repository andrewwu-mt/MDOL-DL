package com.metrics.rfa.news;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.OptionDao;
import com.metrics.MDOL.dao.StoryDao;
import com.metrics.MDOL.dbo.Option;
import com.metrics.MDOL.dbo.Story;
import com.metrics.MDOL.util.TimeUtil;
import com.metrics.rfa.framework.chain.SegmentChain;
import com.metrics.rfa.framework.chain.SegmentChainClient;
import com.metrics.rfa.framework.sub.SubAppContext;

/**
 * This is a SegmentChainClient class that handle callback for story data.
 * 
 * @see SegmentChainClient
 */
@SuppressWarnings("unused")
public class NewsStoryClient implements SegmentChainClient {
	String _serviceName;
	SubAppContext _appContext;
	SegmentChain _segchain;
	boolean _neverTabular;
	String _storyContent = "";
	Headline _headline;
	ApplicationContext _context;
	static protected SimpleDateFormat _IDNdateTimeFormat = new SimpleDateFormat("dd MMM yyyyHH:mm:ss", Locale.ENGLISH);
	static protected SimpleDateFormat _IDNdateTimeFormat2 = new SimpleDateFormat("dd MMM yyyyHH:mm", Locale.ENGLISH);
	static protected TimeZone _gmt = TimeZone.getTimeZone("GMT");
	static protected SimpleDateFormat _timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

	static {
		_IDNdateTimeFormat.setTimeZone(_gmt);
		_IDNdateTimeFormat2.setTimeZone(_gmt);
	}

	public NewsStoryClient(SubAppContext appContext, String serviceName, Headline headline, ApplicationContext context) {
		_serviceName = serviceName;
		_appContext = appContext;
		_headline = headline;
		_context = context;
		_segchain = new SegmentChain(appContext, this, headline.getPnac());
	}

	public void cancel() {
		_segchain.cleanup();
	}


	public void processUpdate(SegmentChain chain) {
		String type = chain.tabText();
		String segText = chain.currentSegText();
		_storyContent += segText;
	}

	public void processComplete(SegmentChain chain) {
		// All segments of chain was completed.

		try {

			String headlinetext = _headline.getText();

			_storyContent = _storyContent;

			String toppiccode = _headline.getTopicCodes();

			String storydateime = _headline.getStoryDate() + _headline.getStoryTime();

			Date _storyDateTime = null;

			try {
				_storyDateTime = _IDNdateTimeFormat.parse(storydateime);
			} catch (Exception e) {
				try {
					_storyDateTime = _IDNdateTimeFormat2.parse(storydateime);
				} catch (Exception e2) {
					System.err.println("err: " + e.getMessage());
				}
			}

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				storydateime = sdf.format(_storyDateTime);
			} catch (Exception e) {
				System.err.println("err: " + e.getMessage());
			}
			
			Story story = new Story();
			story.setCode(_headline.getTopicCodes());
			story.setCompanyCode(_headline.getCompanyCodes());
			story.setHeadline(headlinetext);
			story.setLang(_headline.getLang());
			story.setTime(Timestamp.valueOf(storydateime));
			story.setContent(_storyContent);
			story.setInsertDate(TimeUtil.getTimeStamp(new Date()));
			
			StoryDao storyDao = (StoryDao) _context.getBean("storyDaoProxy");
			OptionDao optionDao = (OptionDao) _context.getBean("optionDaoProxy");
			
			Option option = optionDao.getOptionBy(1);
			
			List<Story> list = storyDao.getBy(headlinetext, story.getTime());
			
			if(list.size() > 0) {
				System.out.println("Double data: " + headlinetext);
				
			} else {
				if (option.getNewsLang() == 1) {
					ArrayList<String> _language = new ArrayList<String>();
					_language.add("ZH");
					ArrayList<String> _acctopic = new ArrayList<String>();
					_acctopic.add("LZH");
					ArrayList<String> _excltopic = new ArrayList<String>();
					_excltopic.add("LZT");
					ArrayList<String> _incltopic = new ArrayList<String>();
					_incltopic.add("LZS");
					if (checkLanguage(_headline, _language) && checkTopic(_headline, _acctopic, _excltopic)) {
						storyDao.save(story);
					}
				} else {
					storyDao.save(story);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.cancel();
	}

	public void processError(SegmentChain chain) {
		System.err.println(_headline.option() + " - " + _headline.getText()	+ " - " + _headline.getPnac());
		System.err.println(chain.errorText());

		// try {
		//
		// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// Connection conn = null;
		// String dbcolpre = "";
		//
		// if(_dbconfig.get_db_type().equalsIgnoreCase("mssql")) {
		// dbcolpre = "N";
		// conn =
		// DriverManager.getConnection("jdbc:sqlserver://"+_dbconfig.get_db_server()+";databaseName="+_dbconfig.get_db_schema()+";SendStringParametersAsUnicode=true;",_dbconfig.get_db_user(),
		// _dbconfig.get_db_pass());
		// }
		// else if(_dbconfig.get_db_type().equalsIgnoreCase("mysql")) {
		// conn =
		// DriverManager.getConnection("jdbc:mysql://"+_dbconfig.get_db_server()+":3306/"+_dbconfig.get_db_schema()+"?useUnicode=true&characterEncoding=utf-8",_dbconfig.get_db_user(),
		// _dbconfig.get_db_pass());
		// }
		// else {
		// System.err.println("database type "+
		// _dbconfig.get_db_type()+" is not supported");
		// System.exit(1);
		// }
		// Statement statement = conn.createStatement();
		//
		// String headlinetext = _headline.getText().replaceAll("'", "��");
		// _storyContent = _storyContent.replaceAll("'", "��");
		// String toppiccode = _headline.getTopicCodes().replaceAll(" ", "�A");
		// String storydateime =
		// _headline.getStoryDate()+_headline.getStoryTime();
		// Date _storyDateTime=null;
		// try {
		// _storyDateTime = _IDNdateTimeFormat.parse(storydateime);
		// }
		// catch (Exception e) {
		// try {
		// _storyDateTime = _IDNdateTimeFormat2.parse(storydateime);
		// }
		// catch (Exception e2) {
		// System.err.println("err: "+e.getMessage());
		// }
		// }
		//
		// try {
		//
		// SimpleDateFormat sdf = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		// storydateime = sdf.format(_storyDateTime);
		// }
		// catch(Exception e) {
		// System.err.println("err: "+e.getMessage());
		// }
		//
		//
		// ResultSet rs =
		// statement.executeQuery("select * from zt_article_alert where ARTICLETITLE = "+dbcolpre+"'"+headlinetext+"' and PUBLISTTIME = '"+
		// storydateime+"'");
		// if (!rs.next()) {
		// statement.executeUpdate("INSERT INTO zt_article_alert (ARTICLETITLE, PUBLISTTIME, TYPECODE, LANCODE) "
		// +
		// "VALUES ("+dbcolpre+"'"+headlinetext+"', '"+storydateime+"', '"+toppiccode+"','"+_headline.getLang()+"')");
		//
		// }
		// rs.close();
		//
		// ArrayList<String> _language = new ArrayList();
		// _language.add("ZH");
		//
		// ArrayList _acctopic = new ArrayList();
		// _acctopic.add("LZH");
		// ArrayList _excltopic = new ArrayList();
		// _excltopic.add("LZT");
		// ArrayList _incltopic = new ArrayList();
		// _incltopic.add("LZS");
		//
		// if(checkLanguage(_headline, _language)&&checkTopic(_headline,
		// _acctopic, _excltopic)) {
		//
		// JChineseConvertor jcc = JChineseConvertor.getInstance();
		// headlinetext = jcc.t2s(headlinetext);
		// String storycontent = jcc.t2s(_storyContent);
		// ResultSet rscn =
		// statement.executeQuery("select * from zt_article_alert_cn where ARTICLETITLE = "+dbcolpre+"'"+headlinetext+"' and PUBLISTTIME = '"+storydateime+"'");
		// if (!rscn.next()) {
		// statement.executeUpdate("INSERT INTO zt_article_alert_cn (ARTICLETITLE, PUBLISTTIME, TYPECODE, LANCODE) "
		// +
		// "VALUES ("+dbcolpre+"'"+headlinetext+"', '"+storydateime+"', '"+toppiccode+"','"+_headline.getLang()+"')");
		// }
		// rscn.close();
		//
		// }
		//
		//
		//
		// statement.close();
		// conn.close();
		//
		//
		// }
		// catch (Exception e) {
		// try {
		// FileWriter fstream = new FileWriter("insertdb_error.txt", true);
		// BufferedWriter out = new BufferedWriter(fstream);
		// out.write("***  "+System.currentTimeMillis()+" "
		// +_headline.getText()+ " ***\r\n");
		// out.write(e.getMessage()+"\r\n");
		// out.close();
		//
		// }
		// catch (Exception ioe) {}
		// //e.printStackTrace();
		// }

		this.cancel();

	}

	boolean checkLanguage(Headline hl, ArrayList<String> lang) {
		boolean flag = false;
		for (int i = 0; i < lang.size(); i++) {
			if (hl.getLang().equalsIgnoreCase(lang.get(i).toString())) {
				flag = true;
			}
		}

		if (lang.size() == 0) {
			flag = true;
		}

		return flag;

	}

	boolean checkTopic(Headline hl, ArrayList<String> topics,
			ArrayList<String> excltopics) {
		boolean flag = false;
		String[] headlinetopics = hl.getTopicCodes().split(" ");
		for (int i = 0; i < topics.size(); i++) {
			for (int j = 0; j < headlinetopics.length; j++) {
				if (headlinetopics[j]
						.equalsIgnoreCase(topics.get(i).toString())) {
					flag = true;
				}
			}
		}

		for (int i = 0; i < excltopics.size(); i++) {
			for (int j = 0; j < headlinetopics.length; j++) {
				if (headlinetopics[j].equalsIgnoreCase(excltopics.get(i)
						.toString())) {
					flag = false;
				}
			}
		}
		return flag;
	}
	
	
}
