package hr.fer.zemris.java.sorters;

/**
 * Razred koji implementira sortiranje polja podataka quick sortom ili insertion sortom, ovisno o broju elemenata.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class QSortParallel {
	
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
		new QSortJob(array, 0, array.length-1).run();
	}
	
	/**
	* Model posla sortiranja podpolja čiji su elementi na pozicijama
	* koje su veće ili jednake <code>startIndex</code> i manje
	* ili jednake <code>endIndex</code>.
	*/
	static class QSortJob implements Runnable {

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
		 * @see java.lang.Runnable#run()
		 * 
		 * Metoda za sortiranje. Ako je preostali broj elemenata manji ili jednak <code>CUT_OFF</code>
		 * koristi se insertion sort. Ako je broj elemenata veći, koristi se quick sort. Ako je korišten 
		 * quick sort, ako je broj preostalih elemenata veći od <code>P_THRESHOLD</code> koriste se dvije dretve.
		 */
		@Override
		public void run() {
			if(endIndex - startIndex + 1 > CUT_OFF) {
				boolean doInParallel = endIndex - startIndex + 1 > P_THRESHOLD;
				// pronađi pivot, razdijeli polje u lijevi i desni dio
				int pivotIndex = selectPivot();
				int i = partition(startIndex, endIndex, pivotIndex);
				// Neka je ovdje 'i' konačna lokacija na kojoj je završio pivot
				Thread t1 = null;
				if(startIndex < i) {
					QSortJob job = new QSortJob(array, startIndex, i - 1);
					t1 = executeJob(doInParallel, job);
				}
				Thread t2 = null;
				if(endIndex > i) {
					QSortJob job = new QSortJob(array, i + 1, endIndex);
					t2 = executeJob(doInParallel, job);
				}
				if(t1 != null) {
					try {
						t1.join();
					} catch (InterruptedException e) {
						System.out.print("Thread 1 join interrupted.\n");
					}
				}
				if(t2 != null) {
					try {
						t2.join();
					} catch (InterruptedException e) {
						System.out.print("Thread 2 join interrupted.\n");
					}
				}
			} else {
				insertionSort(array, startIndex, endIndex);
			}
		}
		
		/**
		* Direktno izvodi zadani posao pozivom run() i tada vraća <code>null</code>
		* ili pak stvara novu dretvu, njoj daje taj posao i pokreće je te vraća
		* referencu na stvorenu dretvu (u tom slučaju ne čeka da posao završi).
		* @param doInParallel treba li posao pokrenuti u novoj dretvi
		* @param job posao
		* @return <code>null</code> ili referencu na pokrenutu dretvu
		*/
		private Thread executeJob(boolean doInParallel, final QSortJob job) {
			if(doInParallel) {
				Thread thread = new Thread(job);
				thread.start();
				return thread;
			} else {
				job.run();
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
