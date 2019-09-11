package com.persistentstorage.map.main;

import com.persistentstorage.map.HashMap;
import com.persistentstorage.map.Map;

/**
 * A program that emulates a persistent HashMap storage
 */
public class PersistentStorageMain {
	
	//the HashMap object
	private static HashMap<String, Integer> hMap;
	
	public static void main(String[] args) {
		
		//load the HashMap storage
		hMap = HashMap.load();
		
		//input a key-value pair
		hMap.put("Awsaf", 10);
		
		//save the entry
		hMap.save();
	}

}
