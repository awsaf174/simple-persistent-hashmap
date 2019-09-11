package com.persistentstorage.map;

import java.io.Serializable;

/**
 * An interface to model HashMap class.
 */
public interface Map<K,V> extends Serializable {
	void put(K key, V value);
	V get(K key);
	boolean contains(K key);
	boolean remove(K key);
}
