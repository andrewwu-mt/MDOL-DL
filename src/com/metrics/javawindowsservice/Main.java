package com.metrics.javawindowsservice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

import com.metrics.MDOL.snapshot.Scheduler;
import com.metrics.rfa.news.NewsViewer;
import com.metrics.rfa.news.TestClass;
import com.metrics.rfa.quick.QSConsumer;


public class Main implements WrapperListener{
	//Quote quote;
	NewsViewer news;
	 
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public static void main(String[] args){
		WrapperManager.start(new Main(), args);
	}

	@Override
	public void controlEvent(int event) {
		// TODO Auto-generated method stub
		if ((event == WrapperManager.WRAPPER_CTRL_LOGOFF_EVENT )
				&& ( WrapperManager.isLaunchedAsService() || WrapperManager.isIgnoreUserLogoffs())) {
			//Ignore
		}
		else {
			WrapperManager.stop( 0 );
		}
	}

	@Override
	public Integer start(String[] arg0) {
		
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				new QSConsumer(context);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Scheduler(context);
				
				news = new NewsViewer(context);
				news.run();
			}
		});
		t.start();
		
		return null;
	}

	@Override
	public int stop(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
