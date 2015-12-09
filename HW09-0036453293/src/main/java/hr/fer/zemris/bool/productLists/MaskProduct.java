/**
 * Package holding the MaskProduct class used in QMC minimizer.
 */
package hr.fer.zemris.bool.productLists;

import hr.fer.zemris.bool.Mask;

import java.util.HashSet;
import java.util.Set;

/**
 * Class used to represent a product of multiple masks. Class can also create a product of only one
 * mask, the result is the given mask.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class MaskProduct {
	
	private Set<Mask> masks;
	private Set<Integer> coveredIndexes;
	
	/**
	 * Class constructor. Constructor takes one argument, a set of masks the product 
	 * is based on.
	 * 
	 * @param masks set of masks the product is based on, can not be null or empty
	 */
	public MaskProduct(Set<Mask> masks) {
		if(masks == null || masks.isEmpty()) {
			throw new IllegalArgumentException("Can not create a product of masks with no masks.");
		}
		this.masks = new HashSet<>(masks);
		coveredIndexes = new HashSet<>();
		for(Mask mask: masks) {
			coveredIndexes.addAll(mask.getCoveredIndexes());
		}
	}
	
	/**
	 * Method used to acquire the set of masks the product is based on.
	 * 
	 * @return returns the set of masks the product is based on
	 */
	public Set<Mask> getMasks() {
		return new HashSet<>(this.masks);
	}
	
	/**
	 * Method used to acquire a set of indexes the product covers.
	 * 
	 * @return returns a set of indexes the product covers
	 */
	public Set<Integer> getCoveredIndexes() {
		return new HashSet<>(this.coveredIndexes);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((masks == null) ? 0 : masks.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}	
		if(obj == null) {
			return false;
		}	
		if(!(obj instanceof MaskProduct)) {
			return false;
		}
		MaskProduct other = (MaskProduct) obj;
		if(masks == null) {
			if(other.masks != null)
			{
				return false;
			}		
		} else if (!masks.equals(other.masks)) {
			return false;
		}
		return true;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i = 1;
		for(Mask mask: masks) {
			sb.append(mask.toString());
			if(i != masks.size()) {
				sb.append(", ");
				i++;
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
