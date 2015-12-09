package hr.fer.zemris.java.sorters;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Razred koji implementira sortiranje polja podataka quick sortom ili insertion sortom, ovisno o broju elemenata.
 * Modifikacija 9. DZ iz Jave s korištenjem ForkJoin radnog okvira.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class QSortParallel2 {
	
	/**
	* Prag koji govori koliko elemenata u podpolju minimalno
	* mora biti da bi se sortiranje nastavilo paralelno;
	* ako elemenata ima manje, algoritam prelazi na klasično
	* rekurzivno (slijedno) sortiranje.
	*/
	private static final int P_THRESHOLD = 6000;
	
	/**
	* Prag za prekid rekurzije. Ako elemenata ima više od
	* ovoga, quicksort nastavlja rekurzivnu dekompoziciju.
	* U suprotnom ostatak sortira algoritmom umetanja.
	*/
	private static final int CUT_OFF = 5;
	
	/**
	* Sučelje prema klijentu: prima polje i vraća se
	* tek kada je polje sortirano. Primjenjujući gornje
	* pragove najprije posao paralelizira a kada posao
	* postane dovoljno mali, rješava ga slijedno.
	*
	* @param array polje koje treba sortirati
	*/
	public static void sort(int[] array) {
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new QSortJob(array, 0, array.length - 1));
		pool.shutdown();
	}
	
	/**
	* Model posla sortiranja podpolja čiji su elementi na pozicijama
	* koje su veće ili jednake <code>startIndex</code> i manje
	* ili jednake <code>endIndex</code>.
	*/
	static class QSortJob extends RecursiveAction {
		
		private static final long serialVersionUID = 1L;
		private int[] array;
		private int startIndex;
		private int endIndex;
		
		/**
		 * Konstruktor.
		 * 
		 * @param array polje elemenata koje treba sortirati
		 * @param startIndex početni indeks elemenata za sortiranje
		 * @param endIndex krajni indeks elemenata za sortiranje
		 */
		public QSortJob(int[] array, int startIndex, int endIndex) {
			
			super();
			this.array = array;
			if(startIndex > endIndex) {
				throw new IllegalArgumentException("Starting index can not be greater than ending index.");
			}
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		/**
		 * @see java.util.concurrent.RecursiveAction#compute()
		 * 
		 * Metoda za sortiranje. Ako je preostali broj elemenata manji ili jednak <code>CUT_OFF</code>
		 * koristi se insertion sort. Ako je broj elemenata veći, koristi se quick sort. Ako je korišten 
		 * quick sort, ako je broj preostalih elemenata veći od <code>P_THRESHOLD</code> koriste se dvije dretve.
		 */
		@Override
		public void compute() {
			if(endIndex - startIndex + 1 > CUT_OFF) {
				boolean doInParallel = endIndex - startIndex + 1 > P_THRESHOLD;
				// pronađi pivot, razdijeli polje u lijevi i desni dio
				int pivotIndex = selectPivot();
				int i = partition(startIndex, endIndex, pivotIndex);
				// Neka je ovdje 'i' konačna lokacija na kojoj je završio pivot
				QSortJob qsj1 = null;
				if(startIndex < i) {
					qsj1 = new QSortJob(array, startIndex, i - 1);
					qsj1 = executeJob(doInParallel, qsj1);
				}
				QSortJob qsj2 = null;
				if(endIndex > i) {
					qsj2 = new QSortJob(array, i + 1, endIndex);
					qsj2 = executeJob(doInParallel, qsj2);
				}
				if(qsj1 != null && qsj2 != null) {
					invokeAll(qsj1, qsj2);
				}
				else if(qsj1 != null) {
					qsj1.compute();
				}
				else if(qsj2 != null) {
					qsj2.compute();
				}
			} else {
				insertionSort(array, startIndex, endIndex);
			}
		}
		
		/**
		* Direktno izvodi zadani posao pozivom compute() i tada vraća <code>null</code>
		* ili pak samo vraća predani posao.
		* @param doInParallel treba li posao pokrenuti u novoj dretvi
		* @param job posao
		* @return <code>null</code> ili predani posao
		*/
		private QSortJob executeJob(boolean doInParallel, final QSortJob job) {
			if(doInParallel) {
				return job;
			}
			else {
				job.compute();
				return null;
			}
		}
		
		/**
		* Odabir pivota metodom medijan-od-tri u dijelu polja
		* <code>array</code> koje je ograđeno indeksima
		* <code>startIndex</code> i <code>endIndex</code>
		* (oba uključena).
		*
		* @return vraća indeks na kojem se nalazi odabrani pivot
		*/
		public int selectPivot() {
			int middleIndex = startIndex + (endIndex - startIndex) / 2;
			if(array[endIndex] < array[startIndex]) {
				swap(array, startIndex, endIndex);
			}
			if(array[middleIndex] < array[startIndex]) {
				swap(array, startIndex, middleIndex);
			}
			if(array[endIndex] < array[middleIndex]) {
				swap(array, endIndex, middleIndex);
			}
			return middleIndex;
		}
		
		/**
		* U predanom polju <code>array</code> zamjenjuje
		* elemente na pozicijama <code>i</code> i <code>j</code>.
		* @param array polje u kojem treba zamijeniti elemente
		* @param i prvi indeks
		* @param j drugi indeks
		*/
		public static void swap(int[] array, int i, int j) {
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		
		/**
		 * Podjela inicijalnog polja podataka na dva jednaka dijela tako da su manji od pivota
		 * lijevo, a veći desno.
		 * 
		 * @param left početak pod-polja
		 * @param right kraj pod-polja
		 * @param pivotIndex index pivot elementa
		 * @return vraća index pivot elementa
		 */
		private int partition(int left, int right, int pivotIndex) {
			int pivotValue = array[pivotIndex];
			swap(array, pivotIndex, right);
			int storeIndex = left;
			for(int i = left; i < right; i++) {
				if (array[i] <= pivotValue) {
					swap(array, i, storeIndex);
					storeIndex = storeIndex + 1;
				}
			}
			swap(array, storeIndex, right);  // Move pivot to its final place
			return storeIndex;
		}
		
		/**
		 * Implementacija insertion sorta.
		 * 
		 * @param array
		 * @param startIndex
		 * @param endIndex
		 */
		private void insertionSort(int[] array, int startIndex, int endIndex) {
			for(int i = startIndex + 1; i < endIndex + 1; i++) {
				int temp = array[i];
				int j;
				for(j = i - 1; j >= 0 && temp < array[j]; j--) {
					array[j + 1] = array[j];
				}
				array[j + 1] = temp;
			}
		}
	}
	
	/**
	* Pomoćna metoda koja provjerava je li predano polje
	* doista sortirano uzlazno.
	*
	* @param array polje
	* @return <code>true</code> ako je, <code>false</code> inače
	*/
	public static boolean isSorted(int[] array) {
		boolean sorted = true;
		for(int i = 0; i < array.length - 1; i++) {
			if(array[i] > array[i+1]) {
				sorted = false;
				break;
			}
		}
		return sorted;
	}
}
