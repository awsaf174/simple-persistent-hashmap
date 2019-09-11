package com.persistentstorage.map;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class to model HashMap from.
 */
public class HashMap<K,V> implements Map<K,V>, Serializable {
	
	private static final int DEFAULT_CAPACITY = 16;
	
	//Stores entries for this HashMap in an array of LinkedList
	private LinkedList<HashMapEntry>[] hashTable;
	
	//name of file where the HashMap entries will be saved
	private static String fileName = "HashMapDB.txt";
	
	public HashMap() {
		this(DEFAULT_CAPACITY);
	}
	
	 /**
     * Constructs a new HashMap object.
     * Initializes the array of LinkedList used to store the entries 
     * @param capacity The size of the array
     */
	public HashMap(int capacity) {
		hashTable = new LinkedList[capacity];
	}
	
	/**
     * Attempts to add a new entry using key-value pair to the HashMap storage.
     * @param key The key of the new entry
     * @param value The value associated with the provided key
     */
	@Override
	public void put(K key, V value) {
		
		//create a temporary linked list to add the entry to 
		LinkedList<HashMapEntry> tempList = new LinkedList<>();
		
		//check if the key is equal to null
		if (key == null) {
			//if so check if there already exists an entry with a null key
			if(hashTable[0] == null) {
				//if it does not, initialize the linkedlist at the 0th index
				hashTable[0] = new LinkedList<>();
			}
			//if there is a value with a null key replace it with the new value
			
			//add the entry to the temporary linked list
			tempList.add(new HashMapEntry(key,value));
			
			//add the entry to the HashMap at the 0th index
			hashTable[0] = tempList;
		}
		else {
			//calculate the index to store the entry in
			int index = getHash(key);
			
			//check if the LinkedList in that position is null or not
			if(hashTable[index] == null) {
				//if so, initialize the LinkedList
				hashTable[index] = new LinkedList<>();
			}
			
			//Assign the LinkedList at that index to the temporary LinkedList
			tempList = hashTable[index];
			
			//check if the list is empty
			if(tempList != null) {
				//if not, iterate through the list to check if the key already exists
				for(HashMapEntry e : tempList) {
					//if it exists, remove the entry from the list
					if(e.getKey().equals(key) ) {
						tempList.remove(e);
					}
				}
				
				//add the new entry to the temporary list
				tempList.add(new HashMapEntry<>(key, value));
			}
			else {
				//since the list is empty, add the new entry
				tempList.add(new HashMapEntry<>(key, value));
			}
			//Add the temporary list containing the new entry to the HashMap
			hashTable[index] = tempList;
			
			System.out.println("Entry successful");
		}
		
	}
	
	
	/**
	 * Fetches the a value from the HashMap using the corresponding key
	 * @param key The key needed to get a value from the HashMap
	 * @return the value of the entry if the entry exists or a string denoting 
	 * the entry does not exist.
	 */
	@Override
	public V get(K key) {
		
		//create a temporary linked list to add the entry to
		LinkedList<HashMapEntry> tempList = new LinkedList<>();
		
		//check if the key is equal to null
		if(key == null) {
			
			//if so, assign the linkedlist at the 0th index to the temporary list as null keys are always saved at the 0th index
			tempList = hashTable[0];
			
			//create an iterator to iterate through the temporary LinkedList
			Iterator<HashMapEntry> i = tempList.iterator();
			
			while(i.hasNext()) {
				
				//save the entry in a HashMapEntry object
				HashMapEntry e = i.next();
				
				//return the value of that entry
				return (V) e.getValue();
			}
		}
		else {
			//calculate the index the entry should be stored in
			int index = getHash(key);
			
			//Assign the LinkedList at that index position to the temporary LinkedList
			tempList = hashTable[index];
			
			//check if the list is empty
			if(tempList != null) {
				
				//if not, create an iterator to iterate through the list
				Iterator<HashMapEntry> i = tempList.iterator();
				
				while(i.hasNext()) {
					//save an entry in a HashMapEntry object
					HashMapEntry e = i.next();
					
					//check if the current entry key is equal to the key of the entry we are looking for
					if(e.getKey().equals(key)) {
						
						//if so, return its value
						return (V) e.getValue();
					}
				}
			}
			
		}
		
		//if the entry with the provided key does not exist return a string denoting entry not found
		return (V) "Entry not found";
		
	}
	
	/**
     * Returns whether or not an entry of the provided key exists.
     * @param key The key of the entry to check
     * @return true if the key exists and false otherwise.
     */
	@Override
	public boolean contains(K key) {
		//check if the key is equal to null
		if(key == null) {
			//if so, check if the 0th index is null
			if(hashTable[0] == null) {
				//if it is, entry with the provided key does not exist
				return false;
			}
			else{
				return true;
			}
		}
		
		else {
			
			//create a temporary linked list
			LinkedList<HashMapEntry> tempList = new LinkedList();
			
			//calculate the index the entry should be stored in
			int index = getHash(key);
			
			//Fetch the values at the target index and store it in the temporary list
			tempList = hashTable[index];
			
			//check if the list is empty
			if(tempList == null) {
				//if so, the HashMap does not contain entry for the provided key
				return false;
			}
			
			else {
				//if not, create an iterator to iterate through the list
				Iterator<HashMapEntry> i = tempList.iterator();
				
				while(i.hasNext()) {
					//save an entry in a HashMapEntry object
					HashMapEntry e = i.next();
					
					//check if the key of the current list entry is equal to the provided key
					if(e.getKey().equals(key)) {
						//if so, the HashMap does contain an entry for the provided key
						return true;
					}
				}
				
				return false;
			}
			
			
		}
	}
	
	 /**
     * Attempts to remove an entry from the HashMap using the key
     * @param name The key of the entry
     * @return True if the key was found and entry removed and false otherwise.
     */
	@Override
	public boolean remove(K key) {
		//check if the key is equal to null
		if(key == null) {
			//if so, check if the 0th index is null
			if(hashTable[0] == null) {
				//if it is, the entry with the provided key does not exist and cannot be removed
				return false;
			}
			else{
				//if the entry exists, remove it by assigning null to the linkedlist at the 0th index and return true
				hashTable[0] = null;
				return true;
			}
		}
		else {
			//create a temporary LinkedList to hold HashMapEntry objects
			LinkedList<HashMapEntry> tempList = new LinkedList<>();
			
			//calculate the index of the entry to be removed
			int index = getHash(key);
			
			//Fetch the values at the target index and store it in the temporary list
			tempList = hashTable[index];
			
			//check if the list is empty
			if(tempList == null) {
				//if so, the entry to be removed does not exist. Return false
				System.out.println("Key to remove not found");
				return false;
			}
			
			else {
				
				//if not, create an iterator to iterate through the list
				Iterator<HashMapEntry> i = tempList.iterator();
				
				while(i.hasNext()) {
					
					//save an entry in a HashMapEntry object
					HashMapEntry e = i.next();
					
					//check if the key of the current list entry is equal to the provided key
					if(e.getKey().equals(key)) {
						
						//if so, remove it from the list
						tempList.remove(e);
						
						//add the updated list to the HashMap and return true
						hashTable[index] = tempList;
						return true;
					}
				}
				
				return false;
			}
		}
	}
	
	 /**
     * Tries to save this HashMap object to disk
     * @return Returns true if the save was successful and false otherwise.
     */
	public boolean save() {
		boolean success = HashMapFileManager.save(this, fileName);
		return success;
	}
	
	 /**
     * Tries to load a HashMap object from disk
     * @param fileName the name of the file to load HashMap entries from
     * @return A loaded HashMap object or a HashMap reference if the file does not exist.
     */
	public static HashMap load() {
		return HashMapFileManager.load(fileName);
	}
	
	 /**
	  *	@param key The key of the entry to be added the HashMap 
	  * @return The index the entry with this key will be stored in.
	  */
	public int getHash(K key) {
		return (key.hashCode() & (hashTable.length - 1));
	}
	

}
