package hr.fer.zemris.web.model;

/**
 * Class representing a single entry in the PollOptions table.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class PollOptionEntry {
	
	private long id;
	private String optionTitle;
	private String optionLink;
	private long pollID;
	private long votesCount;
	
	/**
	 * Class constructor.
	 */
	public PollOptionEntry() {
		
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
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}
	
	/**
	 * @param optionTitle the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	
	/**
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}
	
	/**
	 * @param optionLink the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}
	
	/**
	 * @return the pollID
	 */
	public long getPollID() {
		return pollID;
	}
	
	/**
	 * @param pollID the pollID to set
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}
	
	/**
	 * @return the votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}
	
	/**
	 * @param votesCount the votesCount to set
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
	
	/**
	 * Format: "PollOptionEntry=title"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PollOptionEntry="+optionTitle;
	}
}
