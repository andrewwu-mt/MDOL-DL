package com.metrics.MDOL.snapshot;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.OptionDao;
import com.metrics.MDOL.dbo.Option;
import com.metrics.MDOL.util.TimeUtil;


public class Scheduler implements Runnable{
	
	protected ApplicationContext _context;
	protected OptionDao optionDao;
	protected Runner runner;

	public Scheduler(ApplicationContext context){
		_context = context;
		optionDao = (OptionDao) _context.getBean("optionDaoProxy");
		runner = new Runner(_context);
		Thread scheduler = new Thread(this);
		scheduler.start();
		
	}
	
	public void run(){
		
		while(true){
			System.out.println(TimeUtil.getTimeStamp(new Date()));
			Option opt = optionDao.getOptionBy(1);
			int freq = opt.getFreq() * 1000;
			
			runner.runner();

			
			try {
				Thread.sleep(freq);
			} catch (InterruptedException e) {
				new Scheduler(_context);
			}
			
			
		}
		
		
	}
	
}
