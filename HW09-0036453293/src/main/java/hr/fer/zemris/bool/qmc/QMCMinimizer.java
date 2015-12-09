/**
 * Package holding all the classes needed to implement QMC minimizer.
 */
package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;
import hr.fer.zemris.bool.productLists.MaskProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class used to implement the full Quine-McCluskey minimization. It 1st minimizes the 
 * function with Quine-McCluskey method, then finds the minimal expressions using the 
 * Pyne-McCluskey method.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class QMCMinimizer {
	
	private static final int BIG_NUMBER = 9999;
	
	/**
	 * Method used to calculate possible function minimizations achieved using minimal number of masks.
	 * 
	 * @param bf boolean function to be minimized
	 * @param productsWanted flag indicating whether the products are calculated of sums
	 * @return returns the list of possible minimizations achieved using minimal number of masks
	 */
	public static List<MaskBasedBF> minimize(BooleanFunction bf, boolean productsWanted) {
		if(bf == null) {
			throw new IllegalArgumentException("Boolean function can not be null.");
		}
		List<Integer> listOfIndexes = new ArrayList<>();
		if(productsWanted) {
			for(Integer maxterm: bf.maxtermIterable()) {
				listOfIndexes.add(maxterm);
			}
		} else {
			for(Integer minterm: bf.mintermIterable()) {
				listOfIndexes.add(minterm);
			}
		}
		List<Integer> listOfDontCares = new ArrayList<>();
		for(Integer dontCare: bf.dontcareIterable()) {
			listOfDontCares.add(dontCare);
		}
		int domainSize = bf.getDomain().size();
		Set<Mask> unusedMasks = QMCMinimization(domainSize, listOfIndexes, listOfDontCares);
		Set<MaskProduct> solution = PMCMinimization(unusedMasks, new HashSet<>(listOfIndexes));
		List<MaskBasedBF> returnList = new ArrayList<>();
		for(MaskProduct product: solution) {
			MaskBasedBF newMaskBasedBF = new MaskBasedBF("randomName", bf.getDomain(), !productsWanted,
					new ArrayList<Mask>(product.getMasks()), new ArrayList<Mask>());
			returnList.add(newMaskBasedBF);
		}
		return returnList;
	}
	
	/**
	 * Private method used to calculate the minimized function using the Quine-McCluskey method.
	 * 
	 * @param domainSize number of variables the function being minimized is based on
	 * @param listOfIndexes normal indexes that must be covered 
	 * @param listOfDontCares don't care indexes
	 * @return returns the set of masks that must be minimized by Pyne-McCluskey method
	 */
	private static Set<Mask> QMCMinimization(int domainSize, List<Integer> listOfIndexes, List<Integer> listOfDontCares) {
		List<Mask> listOfMasks = new ArrayList<>();
		for(Integer index: listOfIndexes) {
			listOfMasks.add(Mask.fromIndex(domainSize, index, false));
		}
		for(Integer index: listOfDontCares) {
			listOfMasks.add(Mask.fromIndex(domainSize, index, true));
		}
		List<List<Mask>> listOfAllCreatedMasks = new ArrayList<>();
		listOfAllCreatedMasks.add(listOfMasks);
		boolean newMasksCreated = true;
		// combining masks until no new masks ca be created
		while(newMasksCreated) {
			newMasksCreated = false;
			List<Mask> masksOfThisIteration = new ArrayList<>();
			for(Mask mask1: listOfAllCreatedMasks.get(listOfAllCreatedMasks.size() - 1)) {
				for(Mask mask2: listOfAllCreatedMasks.get(listOfAllCreatedMasks.size() - 1)) {
					Mask combinedMask = Mask.combine(mask1, mask2);
					if(combinedMask != null) {
						if(!masksOfThisIteration.contains(combinedMask)) {
							masksOfThisIteration.add(combinedMask);
							newMasksCreated = true;
						}
						mask1.setUsed();
						mask2.setUsed();
					}
				}
			}
			listOfAllCreatedMasks.add(masksOfThisIteration);
		}
		Set<Mask> unusedMasks = new HashSet<>();
		for(List<Mask> maskList: listOfAllCreatedMasks) {
			for(Mask mask: maskList) {
				if(!mask.wasUsed() && !mask.isDontCareMask()) {
					unusedMasks.add(mask);
				}
			}
		}
		return unusedMasks;
	}
	
	/**
	 * Method used to determine the minimal expressions from the masks returned by the Quine-McCluskey method
	 * using the Pyne-McCluskey method.
	 * 
	 * @param unusedMasks set of masks that were not used in the creation of new masks in Quine-McCluskey method
	 * @param listOfIndexes indexes that must be covered
	 * @return returns the set of mask products representing the possible minimization solutions
	 */
	private static Set<MaskProduct> PMCMinimization(Set<Mask> unusedMasks, Set<Integer> listOfIndexes) {
		Map<Integer, List<Mask>> masksCoveringIndexes = new HashMap<>();
		for(Integer index: listOfIndexes) {
			masksCoveringIndexes.put(index, new ArrayList<Mask>());
		}
		// creating a map of covered indexes and maps covering them
		for(Mask mask: unusedMasks) {
			Set<Integer> coveredIndexes = mask.getCoveredIndexes();
			for(int index: coveredIndexes) {
				if(listOfIndexes.contains(index)) {
					List<Mask> mapsCoveringCurrentIndex = masksCoveringIndexes.get(index);
					mapsCoveringCurrentIndex.add(mask);
					masksCoveringIndexes.put(index, mapsCoveringCurrentIndex);
				}
			}
		}
		// creating a set of primary masks
		Set<Mask> primaryMasks = new HashSet<>();
		List<Integer> indexesNotCoveredByPrimary = new ArrayList<>();
		for(Integer index: masksCoveringIndexes.keySet()) {
			indexesNotCoveredByPrimary.add(index);
		}
		// removing indexes covered by primary masks
		for(Integer index: masksCoveringIndexes.keySet()) {
			if(masksCoveringIndexes.get(index).isEmpty()) {
				
			}
			if(masksCoveringIndexes.get(index).size() == 1) {
				primaryMasks.add(masksCoveringIndexes.get(index).get(0));
				indexesNotCoveredByPrimary.remove(index);
			}
		}
		unusedMasks.removeAll(primaryMasks);
		for(Mask mask: primaryMasks) {
			indexesNotCoveredByPrimary.removeAll(mask.getCoveredIndexes());
		}
		Set<MaskProduct> finalSolutionSet = new HashSet<>();		// no nazi pun intended
		// if all indexes are covered with primary masks
		if(indexesNotCoveredByPrimary.isEmpty()) {
			finalSolutionSet.add(new MaskProduct(primaryMasks));
			return finalSolutionSet;
		}
		Set<MaskProduct> initialSetOfProducts = new HashSet<>();
		for(Mask mask: masksCoveringIndexes.get(indexesNotCoveredByPrimary.get(0))) {
			Set<Mask> temp = new HashSet<>();
			temp.add(mask);
			initialSetOfProducts.add(new MaskProduct(temp));
		}
		// creating all possible map products covering all the indexes
		Set<MaskProduct> setOfMaskProducts = new HashSet<>(initialSetOfProducts);
		for(int i = 1; i < indexesNotCoveredByPrimary.size(); i++) {
			Set<MaskProduct> tempSet = new HashSet<>();
			for(Mask mask: masksCoveringIndexes.get(indexesNotCoveredByPrimary.get(i))) {
				for(MaskProduct maskProduct: setOfMaskProducts) {
					Set<Mask> temp = new HashSet<>();
					temp.add(mask);
					temp.addAll(maskProduct.getMasks());
					tempSet.add(new MaskProduct(temp));
				}
			}
			setOfMaskProducts = tempSet;
		}
		// determining the minimal number of needed masks to cover all the indexes
		int minimumRequiredProducts = BIG_NUMBER;
		for(MaskProduct maskProduct: setOfMaskProducts) {
			if(maskProduct.getCoveredIndexes().containsAll(indexesNotCoveredByPrimary)) {
				if(maskProduct.getMasks().size() < minimumRequiredProducts) {
					minimumRequiredProducts = maskProduct.getMasks().size();
				}
			}
		}
		// selecting all the solutions with minimal number of masks
		Set<MaskProduct> possibleSolutions = new HashSet<>();
		for(MaskProduct maskProduct: setOfMaskProducts) {
			if(maskProduct.getCoveredIndexes().containsAll(indexesNotCoveredByPrimary)) {
				if(maskProduct.getMasks().size() == minimumRequiredProducts) {
					possibleSolutions.add(maskProduct);
				}
			}
		}
		// adding primary masks to every solution
		finalSolutionSet = new HashSet<>();
		for(MaskProduct maskProduct: possibleSolutions) {
			Set<Mask> temp = maskProduct.getMasks();
			temp.addAll(primaryMasks);
			finalSolutionSet.add(new MaskProduct(temp));
		}
		return finalSolutionSet;
	}
}
