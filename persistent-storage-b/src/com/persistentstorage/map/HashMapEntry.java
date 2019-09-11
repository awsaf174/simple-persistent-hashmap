package com.persistentstorage.map;

import java.io.Serializable;


/**
 * A class to model the entries stored in a HashMap
 */
public class HashMapEntry<K, V> implements Serializable {
	
	//key for the entry
	private K key;
	
	//value for the corresponding key
	private V value;
	 
	/**
     * Constructs a new entry with the provided key and value.
     * @param key The key of this entry.
     * @param value The value of for this key.
     */
	public HashMapEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
    *
    * @return Returns the key of this entry
    */
	public K getKey() {
		return key;
	}
	
	 /**
     * Sets the key of this entry
     * @param key The key for this entry.
     */
	public void setKey(K key) {
		this.key = key;
	}
	
	/**
    *
    * @return Returns the value of this entry
    */
	public V getValue() {
		return value;
	}
	
	 /**
     * Sets the value of this entry
     * @param value The value for this entry.
     */
	public void setValue(V value) {
		this.value = value;
	}
	
}
