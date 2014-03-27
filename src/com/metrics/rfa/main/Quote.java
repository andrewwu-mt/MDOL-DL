package com.metrics.rfa.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrics.rfa.quick.QSConsumer;

public class Quote {
	
    public static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) throws InterruptedException{
		new QSConsumer(context);
		/*Thread.sleep(5000);
		new Scheduler(context);*/
    }
    
    
    
}




