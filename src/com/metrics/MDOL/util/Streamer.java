package com.metrics.MDOL.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.metrics.MDOL.bean.NowLast;


public class Streamer {
	
	public static List<Object> last = new ArrayList<Object>();
	
	public static List<NowLast> nowLast = new ArrayList<NowLast>();
	
	public static int Length;
	public static String[] items;
	public static String[] fields;
	

	public static Map<String , List<NowLast>> map = new HashMap<String , List<NowLast>>();
	public static List<String> listMap = new ArrayList<String>();
	
	//getter and setter

	public static int getLength() {
		return Length;
	}

	public static void setLength(int length) {
		Length = length;
	}

	public static String[] getItems() {
		return items;
	}

	public static void setItems(String[] items) {
		Streamer.items = items;
	}

	public static String[] getFields() {
		return fields;
	}

	public static void setFields(String[] fields) {
		Streamer.fields = fields;
	}
	
	
}
