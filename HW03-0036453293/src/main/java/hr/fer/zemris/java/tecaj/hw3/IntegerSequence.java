/**
 * Package holding all the classes and methods for 3rd Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;

/**
 * Class which allows user to loop from specified integer to specified integer with a given step.
 * Implements interface Iterable so it can be iterated over.
 * 
 * @author MarkoB
 * @version 1.0
 *
 */
public class IntegerSequence implements Iterable<Integer> {
	
	private int start;
	private int end;
	private int step;
	private boolean downwards;
	
	/**
	 * Class constructor. Constructor checks whether the given arguments are valid and, if everything 
	 * is valid, creates a new object.
	 * 
	 * @param start starting expression of the loop
	 * @param end ending expression of the given loop
	 * @param step step expression at which the loop will increment
	 */
	public IntegerSequence(int start, int end, int step) {
		
		if(end < start) {
			downwards = true;
		}
		if(step <= 0) {
			throw new IllegalArgumentException("Iterating requires a positive step expression greater than 0!");
		}
		this.start = start;
		this.end = end;
		this.step = step;
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 * Method creates a new object type IntegerSequenceIterator as the class iterator.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new IntegerSequenceIterator();
	}
	
	/**
	 * Class used to create an iterator for the class IntegerSequence. Class enables the downward or 
	 * upward iteration over the elements of the class IntegerSequence. Class implements interface Iterator.
	 */
	private class IntegerSequenceIterator implements Iterator<Integer> {
		
		private int currentInteger;
		private int numberOfIntegersLeft;
		
		/**
		 * Class constructor.
		 */
		public IntegerSequenceIterator() {
			this.currentInteger = start;
			if(downwards) {
				this.numberOfIntegersLeft = ((start - end) / step) + 1;
			} else {
				this.numberOfIntegersLeft = ((end - start) / step) + 1;
			}
			
		}

		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return (numberOfIntegersLeft > 0);
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Integer next() {
			if(numberOfIntegersLeft < 1) {
				throw new OutOfElementsException("Nema više elemenata!");
			}
			int value = currentInteger;
			if(downwards) {
				currentInteger -= step;
			} else {
				currentInteger += step;
			}
			numberOfIntegersLeft--;
			return value;
		}

		/**
		 * @see java.util.Iterator#remove()
		 * Method is unsupported by this iterator.
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Can not remove elements of this sequence");
		}
	}
}
