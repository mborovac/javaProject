package hr.fer.zemris.web.radionice;

/**
 * Class representing a single Option in the creation of a workshop.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Opcija implements Comparable<Opcija> {
	
	private String id;
	private String vrijednost;
	
	/**
	 * Class constructor.
	 * 
	 * @param id option's ID
	 * @param vrijednost option's value
	 */
	public Opcija(String id, String vrijednost) {
		if(id == null || id.isEmpty()) {
			System.out.println("ID can not be null or empty.");
			return;
		}
		if(vrijednost == null || vrijednost.isEmpty()) {
			System.out.println("Vrijednost can not be null or empty.");
			return;
		}
		try {
			Long temp = Long.parseLong(id);
			if(temp < 0) {
				System.out.println("ID must be positive.");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("ID is illegal.");
			return;
		}
		this.id = id;
		this.vrijednost = vrijednost;
	}
	
	/**
	 * @return the vrijednost
	 */
	public String getVrijednost() {
		return vrijednost;
	}
	
	/**
	 * @param vrijednost the vrijednost to set
	 */
	public void setVrijednost(String vrijednost) {
		this.vrijednost = vrijednost;
	}
	
	/**
	 * @return the iD
	 */
	public String getID() {
		return id;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((vrijednost == null) ? 0 : vrijednost.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Opcija)) {
			return false;
		}
		Opcija other = (Opcija) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (vrijednost == null) {
			if (other.vrijednost != null) {
				return false;
			}
		} else if (!vrijednost.equals(other.vrijednost)) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * Compares by ID.
	 */
	@Override
	public int compareTo(Opcija o) {
		return Long.valueOf(id).compareTo(Long.valueOf(o.id));
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.id + ": " + this.vrijednost;
	}
}
