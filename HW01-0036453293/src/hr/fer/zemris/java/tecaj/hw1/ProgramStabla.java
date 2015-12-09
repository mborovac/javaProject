/**
 * Package holding all the classes and methods for 1st Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * Class which creates and improvised binary tree made of six elements type CvorStabla, prints the tree "inorder",
 * flips the tree, prints it "postorder", prints the number of elements in the tree and checks whether the tree contains 
 * and element with value "Ivana".
 * 
 * @author Marko
 * @version 1.0
 * 
 */
class ProgramStabla {
	
	static class CvorStabla {
		CvorStabla lijevi;
		CvorStabla desni;
		String podatak;
	}
	
	/**
	 * Method main which calls all other needed methods.
	 * @param args program requires no arguments
	 */
	public static void main(String[] args) {
		CvorStabla cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Anamarija");
		cvor = ubaci(cvor, "Vesna");
		cvor = ubaci(cvor, "Kristina");
		System.out.println("Ispisujem stablo inorder:");
		ispisiStablo(cvor);
		cvor = okreniPoredakStabla(cvor);
		System.out.println("Ispisujem okrenuto stablo inorder:");
		ispisiStablo(cvor);
		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata: "+vel);
		boolean pronaden = sadrziPodatak(cvor, "Ivana");
		System.out.println("Trazeni podatak je pronaden: "+pronaden);
	}
	
	/**
	 * Method for checking whether a binary tree contains an element with the required value.
	 * @param korijen root of the binary tree
	 * @param podatak required element string value
	 * @return returns true if the tree contains an element with the given value, otherwise returns false
	 */
	static boolean sadrziPodatak(CvorStabla korijen, String podatak) {
		boolean vrijednost = false;
		if(korijen == null) {
			return false;
		}
		if(korijen.podatak.equals(podatak)) {
			return true;
		}
		if(vrijednost == false) {
			vrijednost = sadrziPodatak(korijen.lijevi, podatak);
		}
		if(vrijednost == false) {
			vrijednost = sadrziPodatak(korijen.desni, podatak);
		}
		return vrijednost;
	}
	
	/**
	 * Method for calculating the number of elements in a binary tree.
	 * @param cvor root of the tree
	 * @return returns number of elements in the given tree
	 */
	static int velicinaStabla(CvorStabla cvor) {
		int brojac = 0;
		if(cvor == null) {
			return 0;
		}
		brojac += velicinaStabla(cvor.lijevi);
		brojac += velicinaStabla(cvor.desni);
		return brojac + 1;
	}
	
	/**
	 * Method for adding a new element in to a binary tree.
	 * @param korijen root of the tree
	 * @param podatak string value of the new element
	 * @return returns the root of the tree with the added element
	 */
	static CvorStabla ubaci(CvorStabla korijen, String podatak) {
		if(korijen == null) {
			CvorStabla noviCvor = new CvorStabla();
			noviCvor.lijevi = null;
			noviCvor.desni = null;
			noviCvor.podatak = podatak;
			korijen = noviCvor;
			return korijen;
		}
		if(korijen.podatak.equals(podatak)) {
			System.err.println("Podatak " + podatak +" je vec u stablu, ne mogu ubaciti!");
			return korijen;
		}
		if(podatak.compareTo(korijen.podatak) < 0) {
			if(korijen.lijevi == null) {
				CvorStabla noviCvor = new CvorStabla();
				noviCvor.lijevi = null;
				noviCvor.desni = null;
				noviCvor.podatak = podatak;
				korijen.lijevi = noviCvor;
			} else {
				korijen.lijevi = ubaci(korijen.lijevi, podatak);
			}
		} else if(podatak.compareTo(korijen.podatak) > 0) {
			if(korijen.desni == null) {
				CvorStabla noviCvor = new CvorStabla();
				noviCvor.lijevi = null;
				noviCvor.desni = null;
				noviCvor.podatak = podatak;
				korijen.desni = noviCvor;
			} else {
				korijen.desni = ubaci(korijen.desni, podatak);
			}
		}
		return korijen;
	}
	
	/**
	 * Method for printing a binary tree inorder
	 * @param cvor root of the tree
	 */
	static void ispisiStablo(CvorStabla cvor) {
		if(cvor == null) {
			return;
		}
		ispisiStablo(cvor.lijevi);
		System.out.println(cvor.podatak);
		ispisiStablo(cvor.desni);
	}
	
	/**
	 * Method for rearranging the elements of a binary tree so that the elements with values greater than that of
	 * the current element are to the left of the current element, and the ones with values lower than that of the
	 * current element are to the right of it.
	 * @param korijen root of the binary tree
	 * @return returns the root of the rearranged tree
	 */
	static CvorStabla okreniPoredakStabla(CvorStabla korijen) {
		if(korijen == null) {
			return korijen;
		}
		korijen.lijevi = okreniPoredakStabla(korijen.lijevi);
		korijen.desni = okreniPoredakStabla(korijen.desni);
		CvorStabla pom = korijen.lijevi;
		korijen.lijevi = korijen.desni;
		korijen.desni = pom;
		return korijen;
	}
}