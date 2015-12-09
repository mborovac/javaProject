/**
 * Package holding all the collection classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Class used to create and maintain a dynamic array of elements.
 * 
 * @author MarkoB
 * @version 1.0
 *
 */
public class ArrayBackedIndexedCollection {

	private int size = 0;
	private int capacity;
	private Object[] elements;
	
	/**
	 * Class constructor. Creates a new object of the class ArrayBackedIndexedCollection with the initial capacity
	 * set to 16.
	 * 
	 */
	public ArrayBackedIndexedCollection() {
		capacity = 16;
		elements = new Object[16];
		size = 0;
	}
	
	/**
	 * Class constructor. Creates a new object of the class ArrayBackedIndexedCollection with the initial capacity set to
	 * the given value.
	 * 
	 * @param initialCapacity initial capacity of the newly created collection, integer value greater than 1.
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity must be greater than zero");
		}
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * Method used to determine whether the collection is empty.
	 * 
	 * @return returns true if the collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method used to determine the size of the collection.
	 * 
	 * @return returns number of the elements in the collection.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Method for adding a new element to the collection. Doubles the capacity of the collection if the collection is full
	 * at the time the new element should be added.
	 * 
	 * @param value an object that is to be added to the collection, can not be null
	 */
	public void add(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Value of the new element can not be null");
		} else {
			if(size == capacity) {
				Object[] newElementArray = new Object[2*capacity];
				capacity = 2*capacity;
				for(int i = 0; i < size; i++) {
					newElementArray[i] = elements[i];
				}
				elements = newElementArray;
			}
			elements[size] = value;
			size++;
		}
	}
	
	/**
	 * Method for retrieving the element at the given position in the collection.
	 * 
	 * @param index position of the element that is to be retrieved, integer greater than 0 and not greater than the
	 * number of elements in the list
	 * @return returns the element at the given location
	 */
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		} else {
			return elements[index];
		}
	}
	
	/**
	 * Method for removing the element at the given position in the collection.
	 * 
	 * @param index position of the element that is to be removed, integer greater than 0 and not greater than the
	 * number of elements in the list
	 */
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		} else {
			elements[index] = null;
			for(int i = index + 1; i < size; i++) {
				elements[i-1] = elements[i];
			}
			elements[size-1] = null;
			size--;
		}
	}
	
	/**
	 * Method for inserting a new element at the given position in the collection. The element at the given position before
	 * the insertion and all the elements after it are shifted. Doubles the capacity of the collection if the collection is 
	 * full at the time the new element should be added.
	 * 
	 * @param value element that is to be added to the collection, can not be null
	 * @param position index at which the element should be added, integer greater than 0 and not greater than the
	 * number of elements in the list
	 */
	public void insert(Object value, int position) {
		if(value == null) {
			throw new IllegalArgumentException("Value of the new element can not be null");
		} else if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		} else {
			if(size == capacity) {
				Object[] newElementArray = new Object[2*capacity];
				capacity = 2*capacity;
				for(int i = 0; i < size; i++) {
					newElementArray[i] = elements[i];
				}
				elements = newElementArray;
			}
			for(int i = size-1; i > position - 1; i--) {
				elements[i+1] = elements[i];
			}
			elements[position] = value;
			size++;
		}
	}
	
	/**
	 * Method for retrieving the position of the given element in the collection.
	 * 
	 * @param value the element whose index is sought, can not be null
	 * @return returns the index of the given element if the element is in the collection, -1 otherwise
	 */
	public int indexOf(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Value of the new element can not be null");
		} else {
			for(int i = 0; i < size; i++)  {
				if(elements[i].equals(value)) {
					return i;
				}
			}
			return -1;
		}
	}
	
	/**
	 * Method for checking whether the given element is in a collection.
	 * 
	 * @param value the sought element, can not be null
	 * @return returns true if the element is in the collection, false otherwise
	 */
	public boolean contains(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Value of the new element can not be null");
		} else {
			for(int i = 0; i < size; i++) {
				if(elements[i].equals(value)) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Method for removing all the elements from the collection.
	 * 
	 */
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
}
