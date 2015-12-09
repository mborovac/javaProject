/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class used to create and maintain a mask of boolean values. Class provides the user with three static methods
 * used to create a new mask out of a valid string (method parse()), a valid index (method fromIndex()) or as a
 * combination of 2 masks (method combine()).
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Mask {
	
	private MaskValue[] mask;
	
	/**
	 * Class constructor. Constructor takes an array of valid mask values(zero, one or don't care) and creates
	 * a mask based on it.
	 * 
	 * @param mask array of mask values, can not be null and can not contain null elements
	 */
	public Mask(MaskValue[] mask) {
		if(mask == null) {
			throw new IllegalArgumentException("Mask's array can not be null");
		}
		MaskValue[] newMaskArray = new MaskValue[mask.length];
		for(int i = 0; i < mask.length; i++) {
			if(mask[i] == null) {
				throw new IllegalArgumentException("Mask can not contain null elements!");
			}
			newMaskArray[i] = mask[i];
		}
		this.mask = newMaskArray;
	}
	
	/**
	 * Static method used to create a new mask out of a string or zeros, ones and Xs. X represents don't care.
	 * 
	 * @param inputString string based on which a new mask is to be created, can not be null or an empty
	 * string, can only contain 0, 1 or X characters. Case doesn't matter.
	 * @return
	 */
	public static Mask parse(String inputString) {
		if(inputString == null || inputString.isEmpty()) {
			throw new IllegalArgumentException("Can not create a Mask from given string: " + inputString);
		}
		MaskValue[] maskArray = new MaskValue[inputString.length()];
		String trimmedInputString = inputString.trim();
		for(int i = 0; i < trimmedInputString.length(); i++) {
			char currentCharacter = trimmedInputString.toLowerCase().charAt(i);
			switch(currentCharacter) {
			case '0':
				maskArray[i] = MaskValue.ZERO;
				break;
			case '1':
				maskArray[i] = MaskValue.ONE;
				break;
			case 'x':
				maskArray[i] = MaskValue.DONT_CARE;
				break;
			default :
				throw new IllegalArgumentException("Legal mask values are 1, 0 and X");
			}
		}
		return new Mask(maskArray);
	}
	
	/**
	 * Static method used to create a new mask out of an index's binary representation. It can not
	 * create masks with don't cares as Xs can not be represented binary.
	 * 
	 * @param maskSize size of the new mask, must be an integer greater than zero
	 * @param index index based on which the new mask is to be created, can not be lower than 0 or so
	 * great that its binary representation has more digits that the mask can hold.
	 * @return returns a new mask created out of the given index
	 */
	public static Mask fromIndex(int maskSize, int index) {
		if(maskSize < 1) {
			throw new IllegalArgumentException("Mask size can not be lower than 1");
		}
		if(index < 0) {
			throw new IllegalArgumentException("Index can not be lower than 0");
		}
		MaskValue[] maskArray = new MaskValue[maskSize];
		String binaryIndex = Integer.toBinaryString(index).trim();
		if(binaryIndex.length() > maskSize) {
			throw new IllegalArgumentException("Mask size is too small for the given number");
		}
		int indexStartingPoint = maskSize - binaryIndex.length();
		if(binaryIndex.length() < maskSize) {
			for(int i = 0; i < indexStartingPoint; i++) {
				maskArray[i] = MaskValue.ZERO;
			}
		}
		for(int i = 0; i < binaryIndex.length(); i++) {
			char currentCharacter = binaryIndex.toLowerCase().charAt(i);
			switch(currentCharacter) {
			case '0':
				maskArray[i + indexStartingPoint] = MaskValue.ZERO;
				break;
			case '1':
				maskArray[i + indexStartingPoint] = MaskValue.ONE;
				break;
			default :
				throw new IllegalArgumentException("Legal mask values are 1 and 0");
			}
		}
		return new Mask(maskArray);
	}
	
	/**
	 * Method for acquiring the value of the mask at the given position.
	 * 
	 * @param index position of the value of the mask that is sought
	 * @return returns a mask value at the given position in the mask
	 */
	public MaskValue getValue(int index) {
		if(index < 0 || index > this.getSize() - 1) {
			throw new IllegalArgumentException("Invalid index!");
		}
		return mask[index];
	}
	
	/**
	 * Method checks whether a given mask is more general that the current one. Mask is more general than 
	 * another if it has at least one don't care at the position where the other one has one or zero and the rest
	 * of the position values are the same in both masks.
	 * 
	 * @param givenMask mask that is to be compared to the current mask, can not be null or of a different
	 * size
	 * @return
	 */
	public boolean isMoreGeneralThan(Mask givenMask) {
		if(givenMask == null) {
			throw new IllegalArgumentException("Given mask can not be null");
		}
		if(this.getSize() != givenMask.getSize()) {
			throw new IllegalArgumentException("Given mask is longer/shorter than this mask");
		}
		boolean moreGeneral = false;
		for(int i = 0; i < this.getSize(); i++) {
			if(this.mask[i] != givenMask.mask[i]) {
				if(this.mask[i] != MaskValue.DONT_CARE) {
					return false;
				} else {
					moreGeneral = true;
				}
			}
		}
		return moreGeneral;
	}
	
	/**
	 * Static method used to create a new mask by combining two masks. Masks can be combined only if they differ 
	 * at only one position (values of both masks have to be the same at every position except one) and the value
	 * that is different must not be don't care in any of the two masks. Masks have to be of the same size.
	 * 
	 * @param mask1 1st mask to be combined, can not be null
	 * @param mask2 2nd mask to be combined, can not be null
	 * @return returns a new mask if the masks can be combined, null otherwise
	 */
	public static Mask combine(Mask mask1, Mask mask2) {
		if(mask1 == null || mask2 == null || mask1.getSize() != mask2.getSize()) {
			throw new IllegalArgumentException("Masks can not be combined!");
		}
		int differences = 0;
		int indexOfDifference = 0;
		for(int i = 0; i < mask1.getSize(); i++) {
			if(mask1.mask[i] != mask2.mask[i]) {
				differences++;
				indexOfDifference = i;
			}
		}
		MaskValue[] maskArray = new MaskValue[mask1.getSize()];
		if(differences == 0) {
			return null;
		} else if(differences == 1) {
			for(int i = 0; i < maskArray.length; i++) {
				if(i == indexOfDifference) {
					maskArray[i] = MaskValue.DONT_CARE;
				} else {
					maskArray[i] = mask1.mask[i];
				}
			}
			Mask combinedMask = new Mask(maskArray);
			if(combinedMask.isMoreGeneralThan(mask1)) {
				return combinedMask;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Method used to acquire the number of zero values of the mask.
	 * 
	 * @return returns the number of zero values
	 */
	public int getNumberOfZeros() {
		int zeroCounter = 0;
		for(MaskValue value: this.mask) {
			if(value.equals(MaskValue.ZERO)) {
				zeroCounter++;
			}
		}
		return zeroCounter;
	}
	
	/**
	 * Method used to acquire the number of one values of the mask.
	 * 
	 * @return returns the number of one values
	 */
	public int getNumberOfOnes() {
		int oneCounter = 0;
		for(MaskValue value: this.mask) {
			if(value.equals(MaskValue.ONE)) {
				oneCounter++;
			}
		}
		return oneCounter;
	}
	
	/**
	 * Method used to acquire the size of the mask.
	 * 
	 * @return returns the size of the mask
	 */
	public int getSize() {
		return this.mask.length;
	}
	
	/**
	 * Method used to acquire decimal representation of the mask. If mask has don't cares it is broken in to 2 masks
	 * per don't care differing at the spot where don't care was, one mask getting value one, the other getting value
	 * zero.
	 * 
	 * @return returns the decimal representation of the mask
	 */
	public List<Integer> getMaskIndexes() {
		String maskString = "";
		for(int i = 0; i < this.mask.length; i++) {
			if(this.mask[i].equals(MaskValue.ONE)) {
				maskString = maskString.concat("1");
			} else if(this.mask[i].equals(MaskValue.ZERO)) {
				maskString = maskString.concat("0");
			} else {
				maskString = maskString.concat("x");
			}
		}
		List<Integer> maskIndexes = new ArrayList<Integer>();	
		if(maskString.indexOf('x') == -1) {				// mask doesn't contain don't care
			maskIndexes.add(Integer.parseInt(maskString, 2));
		} else {						// mask contains don't care, it needs to be broken down
			boolean hasDontCare = true;
			List<String> maskStringArray = new ArrayList<String>();
			maskStringArray.addAll(replaceFirstDontCare(maskString));
			while(hasDontCare) {
				hasDontCare = false;
				List<String> listOfModifiedElements = new ArrayList<String>();
				List<String> elementsForDeletion = new ArrayList<String>();
				for(String oneMaskString: maskStringArray) {
					if(oneMaskString.indexOf('x') != -1) {
						hasDontCare = true;
						listOfModifiedElements.addAll(replaceFirstDontCare(oneMaskString));
						elementsForDeletion.add(oneMaskString);
					}
				}
				maskStringArray.removeAll(elementsForDeletion);
				maskStringArray.addAll(listOfModifiedElements);
			}
			for(String oneMaskString: maskStringArray) {
				maskIndexes.add(Integer.parseInt(oneMaskString, 2));
			}
		}
		return maskIndexes;
	}
	
	/**
	 * Private method used to create 2 masks out of one containing don't care by giving one mask value one at 
	 * the don't care spot and giving the other mask value zero.
	 * 
	 * @param string string representing the original mask containing don't care
	 * @return returns a list of strings representing 2 new masks
	 */
	private List<String> replaceFirstDontCare(String string) {
		int indexOfFirstDontCare = string.indexOf('x');
		List<String> modifiedStrings = new ArrayList<String>();
		modifiedStrings.add(string.substring(0, indexOfFirstDontCare) + "1" +
				string.substring(indexOfFirstDontCare + 1, string.length()));
		modifiedStrings.add(string.substring(0, indexOfFirstDontCare) + "0" +
				string.substring(indexOfFirstDontCare + 1, string.length()));
		return modifiedStrings;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * 
	 * Hash value of the instance is the hash value of its mask value array.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(mask);
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Masks are equal if their arrays of mask values are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Mask))
			return false;
		Mask other = (Mask) obj;
		if (!Arrays.equals(mask, other.mask))
			return false;
		return true;
	}
	
	
}
