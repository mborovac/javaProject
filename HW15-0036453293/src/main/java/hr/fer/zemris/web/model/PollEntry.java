package hr.fer.zemris.web.model;

/**
 * Class representing a single entry in the Polls table.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class PollEntry {
	
	private long id;
	private String title;
	private String message;
	
	/**
	 * Class constructor.
	 */
	public PollEntry() {
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Format: "PollEntry=title"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PollEntry="+title;
	}
}
