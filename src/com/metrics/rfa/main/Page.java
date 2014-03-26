package com.metrics.rfa.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrics.rfa.pagedisplay.PageDisplay;

public class Page {

	/**
	 * @param args
	 */
    public static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new PageDisplay(context);
	}

}
