/**
 * Package holding all the classes used in 2nd assignment of 6th Java homework.
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class is used to allow the user to store multiple values in a map for the same key. The values
 * of one key are held on a stack and can be accessed/stored via usual stack methods.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ObjectMultistack {
	
	private Map<String, MultistackEntry> multistack = new HashMap<>();
	
	/*
	 * No constructor is given because the default one is enough.
	 */
	
	/**
	 * Method used to put another value in the map for a specific key.
	 * 
	 * @param name the key in the map, can not be null or empty
	 * @param valueWrapper the element that is to be added, can not be null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Given name is empty");
		}
		if(valueWrapper == null) {
			throw new IllegalArgumentException("Given value wrapper can not be null");
		}
		MultistackEntry entry;
		if(multistack.containsKey(name)) {
			entry = multistack.get(name);
		} else {
			entry = new MultistackEntry();
		}
		entry.add(valueWrapper);
		multistack.put(name, entry);
	}
	
	/**
	 * Method used to access the top element of the value stack for the given key.
	 * Method removes the top element.
	 * 
	 * @param name the key in the map, can not be null or empty
	 * @return returns the top element or null if map doesn't contain a key with that name
	 */
	public ValueWrapper pop(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Given name is empty");
		}
		MultistackEntry entry;
		if(multistack.containsKey(name)) {
			entry = multistack.get(name);
			if(entry.isEmpty()) {
				throw new EmptyStackException();
			}
			return entry.removeFirst();
		}
		return null;
	}
	
	/**
	 * Method used to access the top element of the value stack for the given key.
	 * Method does not remove the top element.
	 * 
	 * @param name the key in the map, can not be null or empty
	 * @return returns the top element or null if map doesn't contain a key with that name
	 */
	public ValueWrapper peek(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Given name is empty");
		}
		MultistackEntry entry;
		if(multistack.containsKey(name)) {
			entry = multistack.get(name);
			if(entry.isEmpty()) {
				throw new EmptyStackException();
			}
			return entry.getFirst();
		}
		return null;
	}
	
	/**
	 * Method used to check whether there are any values stored for the specified key.
	 * 
	 * @param name the key in the map
	 * @return returns true if the given key is not stored in the map or there are no values in the map
	 *  for the given key, false if there are some values connected to the given key in the map
	 */
	public boolean isEmpty(String name) {
		if(!multistack.containsKey(name)) {
			return true;
		} else {
			if(multistack.get(name).isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Private class used to implement a stack-like behavior of the values in the map.
	 * 
	 * @author MarkoB
	 * @version 1.0
	 */
	private static class MultistackEntry {
		
		private List<ValueWrapper> stack = new LinkedList<>();
		
		/**
		 * Method used to add an element to the stack-like abstraction.
		 * 
		 * @param valueWrapper the value that is to be added
		 */
		protected void add(ValueWrapper valueWrapper) {
			stack.add(0, valueWrapper);
		}
		
		/**
		 * Method used to get the 1st element from the stack-like abstraction.
		 * Method does not remove the element.
		 * 
		 * @return returns the 1st element
		 */
		protected ValueWrapper getFirst() {
			return stack.get(0);
		}
		
		/**
		 * Method used to get the 1st element from the stack-like abstraction.
		 * Method removes the element.
		 * 
		 * @return returns the 1st element
		 */
		protected ValueWrapper removeFirst() {
			return stack.remove(0);
		}
		
		/**
		 * Method checks whether the stack-like abstraction is empty.
		 * 
		 * @return returns true if it's empty, false otherwise
		 */
		protected boolean isEmpty() {
			return stack.isEmpty();
		}
	}
}
