/**
 * Package holding all the classes and methods for 1st Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * Class which creates an improvised list made of 3 elements type CvorListe, prints the list in the original order,
 * sorts the list, prints the sorted list and prints the number of the elements in the list.
 * 
 * @author Marko
 * @version 1.0
 * 
 */
class ProgramListe {
	
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}
	
	/**
	 * Method main which calls all other needed methods.
	 * @param args program requires no arguments
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
	}
	
	/**
	 * Method for calculating the length of a list.
	 * @param cvor first element of the list
	 * @return returns number of elements in the given list
	 */
	static int velicinaListe(CvorListe cvor) {
		if(cvor == null) {
			return 0;
		}
		int brojac = 1;
		while(cvor.sljedeci != null) {
			brojac++;
			cvor = cvor.sljedeci;
		}
		return brojac;
	}
	
	/**
	 * Method for adding a new element to the list.
	 * @param prvi first element of the list
	 * @param podatak string value of the new element
	 * @return returns the first element of the list
	 */
	static CvorListe ubaci(CvorListe prvi, String podatak) {
		if(prvi == null) {
			prvi = new CvorListe();
			prvi.podatak = podatak;
			prvi.sljedeci = null;
		} else {
			CvorListe trenutniCvor = prvi;
			while(trenutniCvor.sljedeci != null) {
				trenutniCvor = trenutniCvor.sljedeci;
			}
			CvorListe noviCvor = new CvorListe();
			noviCvor.podatak = podatak;
			noviCvor.sljedeci = null;
			trenutniCvor.sljedeci = noviCvor;
		}
		return prvi;
	}
	
	/**
	 * Method for printing the elements of the list.
	 * @param cvor first element of the list
	 */
	static void ispisiListu(CvorListe cvor) {
		if(cvor == null) {
			return;
		} else {
			do {
				System.out.println(cvor.podatak);
				cvor = cvor.sljedeci;
			} while(cvor != null);
		}
	}
	
	/**
	 * Method for sorting the elements of the list in the ascending order.
	 * @param cvor first element of the list
	 * @return return the first element of the list
	 */
	static CvorListe sortirajListu(CvorListe cvor) {
		CvorListe prvi = cvor;
		CvorListe prijasnjiCvor;
		CvorListe trenutniCvor;
		CvorListe sljedeciCvor;
		boolean sortirano = false;
		if(prvi == null) {			// if the list is empty
			return null;
		} else if(prvi.sljedeci == null) {				// if the list has only one element
			return prvi;
		} else if(prvi.sljedeci.sljedeci == null) {		// if the list has only two elements
			if(prvi.sljedeci.podatak.compareTo(prvi.podatak) < 0) {
				prvi = prvi.sljedeci;
				prvi.sljedeci = cvor;
				cvor.sljedeci = null;
			}
			return prvi;
		} else {				// the list has at least 3 elements
			while(!sortirano) {
				sortirano = true;
				prijasnjiCvor = prvi;
				trenutniCvor = prvi.sljedeci;
				sljedeciCvor = trenutniCvor.sljedeci;
				while(sljedeciCvor != null) {
					if(prvi.sljedeci.podatak.compareTo(prvi.podatak) < 0) {
						sortirano = false;
						CvorListe pom = prvi;
						prvi = prvi.sljedeci;
						pom.sljedeci = prvi.sljedeci;
						prvi.sljedeci = pom;
						prijasnjiCvor = prvi;
						trenutniCvor = prvi.sljedeci;
						sljedeciCvor = trenutniCvor.sljedeci;
					}
					if(sljedeciCvor.podatak.compareTo(trenutniCvor.podatak) < 0) {
						sortirano = false;
						prijasnjiCvor.sljedeci = sljedeciCvor;
						trenutniCvor.sljedeci = sljedeciCvor.sljedeci;
						sljedeciCvor.sljedeci = trenutniCvor;
					}
					prijasnjiCvor = prijasnjiCvor.sljedeci;
					trenutniCvor = prijasnjiCvor.sljedeci;
					sljedeciCvor = trenutniCvor.sljedeci;
				}
			}
			return prvi;
		}
	}
}