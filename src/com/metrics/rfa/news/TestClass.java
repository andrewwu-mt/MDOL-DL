package com.metrics.rfa.news;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestClass {
	private static NewsViewer demo;
	
	public static void startDownload() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");	
		demo = new NewsViewer(context);
		demo.run();
	}
	
	public static void stopDownload() {
		demo.cleanup();
	}
	
	public static void main(String[] args) {
		startDownload();
	}
	

}
