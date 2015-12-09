/**
 * Package holding all the classes and methods for 3rd Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw3;

import java.util.ArrayList;

/**
 * Method used to create and maintain an immutable string. Class provides the user with most of the methods he could
 * need, ranging from acquiring the length of the string to obtaining the index of a certain character in the string 
 * to creating a new string that is a substring of a given string.
 * 
 * @author MarkoB
 * @version 1.0
 * 
 */
public class CString {
	
	private char[] data;
	private int offset;
	private int length;
	
	/**
	 * Private class constructor. Creates a new string that uses a reference to existing character array as its 
	 * data field. The offset is the index of the data field at which this string starts and length is the length
	 *  of the string. Offset + length can not be greater than the size of the data field.
	 * 
	 * @param offset index of the data field at which the string starts, can not be lower than 0
	 * @param length length of the new string
	 * @param data character array with the data info
	 * @throws IndexOutOfBoundsException
	 */
	private CString(int offset, int length, char[] data) {
		/*
		 * Whether offset is negative doesn't have to be checked because it is done in the only method calling this
		 * constructor, method substring()
		 */
//		if(offset < 0) {
//			throw new IndexOutOfBoundsException("Offset must be an integer greater than 0");
//		}
		this.offset = offset;
		this.length = length;
		this.data = data;
	}
	
	/**
	 * Class constructor. Creates a new string out of an old character array. Method creates its own character array 
	 * to use as the data field. Offset + length can not be greater than the size of the data field.
	 * 
	 * @param data character array used to create the new string's data field
	 * @param offset index of the old data field at which the string starts, can not be lower than 0
	 * @param length length of the new string
	 * @throws IndexOutOfBoundsException
	 */
	public CString(char[] data, int offset, int length) {
		if(offset < 0) {
			throw new IndexOutOfBoundsException("Offset must be an integer greater than 0");
		}
		if(offset + length > data.length) {
			throw new IndexOutOfBoundsException("Offset + length must not be greater than the size of the data array");
		}
		this.offset = 0;
		this.length = length;
		char[] newArrayOfChars = new char[length];
		for(int i = 0; i < length; i++) {
			newArrayOfChars[i] = data[i + offset];
		}
		this.data = newArrayOfChars;
	}
	
	/**
	 * Class constructor. Creates a new string out of an old character array. Method creates its own character array 
	 * to use as the data field. Method creates a new string containing all of the characters in the given data 
	 * character array.
	 * 
	 * @param data character array used to create the new string's data field
	 */
	public CString(char[] data) {
		this.offset = 0;
		this.length = data.length;
		this.data = new char[data.length];
		for(int i = this.offset; i < this.offset + this.length; i++) {
			this.data[i - this.offset] = data[i];
		}
	}
	
	/**
	 * Class constructor. Creates a new string out of an old one.
	 * 
	 * @param original old string used to create a new one, can not be null
	 * @throws IllegalArgumentException
	 */
	public CString(CString original) {
		if(original == null) {
			throw new IllegalArgumentException("Original string can not be null");
		}
		this.offset = 0;
		this.length = original.length;
		if(original.offset != 0) {
			this.data = new char[original.length];
			for(int i = original.offset; i < original.offset + original.length; i++) {
				this.data[i - original.offset] = original.data[i];
			}
		} else {
			this.data = original.data;
		}
	}
	
	/**
	 * Class constructor. Creates a new string out of an old one.
	 * 
	 * @param s old string used to create a new one, can not be empty
	 * @throws IllegalArgumentException
	 */
	public CString(String s) {
		if(s.isEmpty()) {
			throw new IllegalArgumentException("Original string can not be empty");
		}
		this.length = s.length();
		this.offset = 0;
		this.data = new char[s.length()];
		for(int i = 0; i < s.length(); i++) {
			this.data[i] = s.charAt(i);
		}
	}
	
	/**
	 * Method for acquiring the length of the string.
	 * 
	 * @return returns the length of the string
	 */
	public int length() {
		return this.length;
	}
	
	/**
	 * Method for acquiring the character at the given index of the string.
	 * 
	 * @param index index of the character that is to be returned, can not be lower than 0 or greater than the 
	 * length of the string - 1
	 * @return returns the character at the given index in the string
	 * @throws IndexOutOfBoundsException
	 */
	public char charAt(int index) {
		if(index < 0 || index > this.length - 1) {
			throw new IndexOutOfBoundsException("Index " + index + " is invalid! ");
		}
		return this.data[index + this.offset];
	}
	
	/**
	 * Method for acquiring a character array made from the string.
	 * 
	 * @return returns a character array containing all of the character the string contains
	 */
	public char[] toCharArray() {
		char[] newArray = new char[this.length];
		for(int i = this.offset; i < this.offset + this.length; i++) {
			newArray[i - this.offset] = this.data[i];
		}
		return newArray;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * Method returns a string made of all of the characters in the string's data field in the exact order
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder(this.offset + this.length);
		for(int i = this.offset; i < this.offset + this.length; i++) {
			sb.append(this.data[i]);
		}
		String returnString = sb.toString();
		return returnString;
	}
	
	/**
	 * Method for acquiring the index of the first occurrence of the given character in the string.
	 * 
	 * @param c character whose index is to be returned
	 * @return return the index of the 1st occurrence of the sought character
	 */
	public int indexOf(char c) {
		for(int i = this.offset; i < this.offset + this.length; i++) {
			if(this.data[i] == c) {
				return i - this.offset;
			}
		}
		return -1;
	}

	/**
	 * Method for checking whether the string starts with a certain substring.
	 * 
	 * @param s the substring with which the string should begin for the method to return true
	 * @return returns true if the string really begins with the given substring, false otherwise
	 * @throws IllegalArgumentException
	 */
	public boolean startsWith(CString s) {
		if(s.length > this.length) {
			throw new IllegalArgumentException("String given as the argument is too big");
		}
		for(int i = 0; i < s.length; i++) {
			if(this.data[i + this.offset] != s.data[i + s.offset]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method for checking whether the string ends with a certain substring.
	 * 
	 * @param s the substring with which the string should end for the method to return true
	 * @return returns true if the string really ends with the given substring, false otherwise
	 * @throws IllegalArgumentException
	 */
	public boolean endsWith(CString s) {
		if(s.length > this.length) {
			throw new IllegalArgumentException("String given as the argument is too big");
		}
		for(int i = s.length - 1; i >= 0; i--) {
			if(this.data[i + this.offset + (this.length - s.length)] != s.data[i + s.offset]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method for checking whether the string contains a certain substring. Only the exact match of the given 
	 * substring will return true
	 * 
	 * @param s the substring which the string should contain for the method to return true, its length can not 
	 * be greater than the length of the main string
	 * @return returns true if the string really contains the given substring, false otherwise
	 * @throws IllegalArgumentException
	 */
	public boolean contains(CString s) {
		if(s.length > this.length) {
			throw new IllegalArgumentException("String given as the argument is too big");
		}
		for(int i = 0; i < this.length; i++) {
			boolean found = true;
			if(this.data[i + this.offset] == s.data[0 + s.offset]) {
				for(int j = 1; j < s.length; j++) {
					if(i + j + this.offset >= this.length) {
						found = false;
					} else {
						if(this.data[i + j + this.offset] != s.data[j + s.offset]) {
							found = false;
						}
					}
				}
				if(found) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method for creating a new string that is a substring of the current string. The substring uses the data field of 
	 * the original string modifying its offset
	 * 
	 * @param startIndex index of the original data field at which the substring begins, can not be lower than 0
	 * @param endIndex index of the original data field at which the substring ends, it is not part of the substring, 
	 * can not be lower than the starting index and can not be greater than the length of the original string
	 * @return returns a new string that is the substring of the original string
	 * @throws IllegalArgumentException
	 */
	public CString substring(int startIndex, int endIndex) {
		if(startIndex < 0) {
			throw new IllegalArgumentException("Starting index must be greater than 0!");
		}
		if(endIndex < startIndex) {
			throw new IllegalArgumentException("Ending index must be greater than or equal to the starting index");
		}
		if(endIndex > this.length) {
			throw new IllegalArgumentException("Ending index must not be greater " +
					"than the length of the original string");
		}
		return new CString(startIndex, endIndex - startIndex, this.data);
	}
	
	/**
	 * Method for creating a new string that is the starting part of original string and is of length n.
	 * The substring starts at the start of the original string and has a length of n.
	 * 
	 * @param n the length of the substring, can not be lower than 0 or greater than the length of the current string
	 * @return returns a new string that is the substring of the original string
	 * @throws IllegalArgumentException
	 */
	public CString left(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("N must be an integer greater than or equal to 0!");
		}
		if(n > this.length) {
			throw new IllegalArgumentException("N must not be greater than the size of the original string!");
		}
		return this.substring(0, n);
	}
	
	/**
	 * Method for creating a new string that is the ending part of original string and is of length n. 
	 * The substring ends at the end of the original string and has a length of n.
	 * 
	 * @param n the length of the substring, can not be lower than 0 or greater than the length of the current string
	 * @return returns a new string that is the substring of the original string
	 * @throws IllegalArgumentException
	 */
	public CString right(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("N must be an integer greater than or equal to 0!");
		}
		if(n > this.length) {
			throw new IllegalArgumentException("N must not be greater than the size of the original string!");
		}
		return this.substring(this.length - n, this.length);
	}
	
	/**
	 * Method for concatenating the current string with another one. Method creates a new string that contains 1st the
	 * current string, then the given one.
	 * 
	 * @param s the string that is to be concatenated with the current string, can not be null
	 * @return returns a new string that is the concatenation of the current string and the given string
	 * @throws IllegalArgumentException
	 */
	public CString add(CString s) {
		if(s == null) {
			throw new IllegalArgumentException("Given string can not be null");
		}
		char[] concatenatedCharArray = new char[this.length + s.length];
		for(int i = 0; i < this.length; i++) {
			concatenatedCharArray[i] = this.data[i + this.offset];
		}
		for(int i = 0; i < s.length; i++) {
			concatenatedCharArray[i + this.length] = s.data[i + s.offset];
		}
		return new CString(concatenatedCharArray);
	}
	
	/**
	 * Method for replacing every occurrence of one character in the current string with another one.
	 * 
	 * @param oldChar character that is to be replaced
	 * @param newChar character that the old character is to be replaced with
	 * @return returns a new string with every occurrence of the "oldChar" replaced by the "newChar"
	 */
	public CString replaceAll(char oldChar, char newChar) {
		ArrayList<Object> newDynamicArray = new ArrayList<Object>();
		/*A dynamic array is needed here and including/re-implementing ArrayBackedIndexedCollection from HW02 just 
		 * messes up the code, hence ArrayList. It is basically the same, just implemented in java.util class*/
		
		for(int i = 0; i < this.length; i++) {
			if(this.data[i + this.offset] == oldChar) {
				newDynamicArray.add(newChar);
			} else {
				newDynamicArray.add(this.data[i + this.offset]);
			}
		}
		char[] arrayWithReplacedChars = new char[newDynamicArray.size()];
		for(int i = 0; i < newDynamicArray.size(); i++) {
			arrayWithReplacedChars[i] = (char) newDynamicArray.get(i);
		}
		return new CString(arrayWithReplacedChars);
	}
	
	/**
	 * Method for replacing every occurrence of one substring in the current string with another substring.
	 * 
	 * @param oldStr substring that is to be replaced, can not be null
	 * @param newStr substring that the old substring is to be replaced with, can not be null
	 * @return returns a new string with every occurrence of the "oldStr" replaced by the "newStr"
	 * @throws IllegalArgumentException
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if(oldStr == null) {
			throw new IllegalArgumentException("Old string can not be null");
		}
		if(newStr == null) {
			throw new IllegalArgumentException("New string can not be null");
		}
		CString replacedString = this;
		boolean done = false;
		while(!done) {
			int indexOfOldString = replacedString.indexOfString(oldStr);
			if(indexOfOldString == -1) {
				done = true;
			} else {
				replacedString = replacedString.substring(0, indexOfOldString).add(newStr)
						.add(substring(indexOfOldString + oldStr.length, replacedString.length));
			}
		}
		return replacedString;
	}
	
	/**
	 * Private method for acquiring the index of the 1st occurrence of the given substring in the current string.
	 * 
	 * @param s the string whose index is sought, can not be longer than the current string
	 * @return returns the index of the 1st occurrence of the given substring
	 * @throws IllegalArgumentException
	 */
	private int indexOfString(CString s) {
		if(s.length > this.length) {
			throw new IllegalArgumentException("String given as the argument is too big");
		}
		for(int i = 0; i < this.length; i++) {
			boolean found = true;
			if(this.data[i + this.offset] == s.data[0 + s.offset]) {
				for(int j = 1; j < s.length; j++) {
					if(i + j + this.offset >= this.length) {
						found = false;
					} else {
						if(this.data[i + j + this.offset] != s.data[j + s.offset]) {
							found = false;
						}
					}
				}
				if(found) {
					return i;
				}
			}
		}
		return -1;
	}
}
