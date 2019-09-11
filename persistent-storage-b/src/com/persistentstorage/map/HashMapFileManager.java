package com.persistentstorage.map;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class is used to save and load the HashMap entries to and from file on disk
 * by means of serialization and deserialization
 */
public class HashMapFileManager<K,V> implements Serializable {
	
	 /**
     * Seriliazes HashMap entries and saves in disk.
     * @param hashMap The HashMap object with the entries to be saved
     * @return Returns true if save was successful and false otherwise.
     */
	protected static boolean save(HashMap hashMap, String fileName) {
		
		if (hashMap != null) {
			
			FileOutputStream fileOutputStream = null;
			ObjectOutputStream objectOutputStream = null;
			
			try {
				
				//create a file output stream to write the objects to
				fileOutputStream = new FileOutputStream(fileName);
				
				//create an object output stream to write out the objects to the file
				objectOutputStream = new ObjectOutputStream(fileOutputStream);

				//write the serializable object to the object output stream
				objectOutputStream.writeObject(hashMap);
				
				//flush the object output stream
				objectOutputStream.flush();
				
				//SUCCESS!
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				//before proceeding, close output streams if they were opened
				closeCloseable(fileOutputStream);				
				closeCloseable(objectOutputStream);
			}
		}
		
		return false;
	}
	
	/**
     * Checks if the file exists.
     * If it does not, creates a new file with the given fileName
     * in the current workspace directory and returns a reference to HashMap
     * If it exists, deserializes it and returns HashMap object containing file contents
     * @param fileName The name of the file from which to load the storage data
     * @return Returns a new HashMap object if loading succeeds or a new HashMap reference
     * if there no file to load
     */
	protected static HashMap load(String fileName) {
		//get the current directory of the workspace to see if the storage file exists
		String cwd = System.getProperty("user.dir");
		
		//construct the complete file path
		File file = new File(cwd + "/" + fileName);
		
		//check if file exists
		if(file.exists() == false) {
			//if not, just return a HashMap reference
			return new HashMap();
		}
		else {
			
			FileInputStream fileInputStream = null;
			ObjectInputStream objectInputStream = null;
			
			try {
				
				//create the file input stream with the fileNameWithExt to read the objects from
				fileInputStream = new FileInputStream(fileName);
				
				 //create an object input stream on the file input stream to read in the objects from the file
				objectInputStream = new ObjectInputStream(fileInputStream);
				
				//create a new HashMap object to load the deserialized object into
				HashMap hMap = new HashMap();
				
				//read the deserialized object from the object input stream and cast it to the HashMap object
				hMap = (HashMap) objectInputStream.readObject();
				
				//Success! Return the loaded HashMap object
				return hMap;
				
			}catch(IOException e) {
				e.printStackTrace();
			}catch(ClassNotFoundException c) {
				c.printStackTrace();
			}finally {
				//before proceeding, close output streams if they were opened
				closeCloseable(fileInputStream);
				closeCloseable(objectInputStream);
			}
		}
		
		return null;
	}
	
	/**
     * Closes a valid Closeable object.
     * @param closeable The Closable to be closed.
     */
	private static void closeCloseable(Closeable closeable) {
        if(closeable != null) {
            //try to close it
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

}
