package hr.fer.zemris.web.dao;

import hr.fer.zemris.web.model.PollEntry;
import hr.fer.zemris.web.model.PollOptionEntry;

import java.util.List;

/**
 * Interface defining all the available methods as means of communication with the database.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface DAO {
	
	/**
	 * Method returns a list of all the polls from the Polls table.
	 * 
	 * @return returns a list of all the polls
	 * @throws DAOException in case of an error
	 */
	public List<PollEntry> getAllPolls() throws DAOException;
	
	/**
	 * Method returns a list of polls with the given id from the Polls table.
	 * 
	 * @param id poll id
	 * @return returns a list of all the polls with the given id
	 * @throws DAOException in case of an error
	 */
	public List<PollEntry> getPoll(long id) throws DAOException;
	
	/**
	 * Method returns a list of poll options from the PollOptions table. 
	 * 
	 * @return returns a list of poll options
	 * @throws DAOException in case of an error
	 */
	public List<PollOptionEntry> getAllPollOptions() throws DAOException;
	
	/**
	 * Method returns a list of poll options with the given id from the PollOptions table.
	 * 
	 * @param id poll option id
	 * @return returns a list of poll options with the given id
	 * @throws DAOException in case of an error
	 */
	public List<PollOptionEntry> getPollOption(long id) throws DAOException;
	
	/**
	 * Method returns a list of poll options with the given poll id from the PollOptions table.
	 * 
	 * @param id poll id
	 * @return returns a list of poll options with the given poll id
	 * @throws DAOException in case of an error
	 */
	public List<PollOptionEntry> getPollOptionsByPollID(long id) throws DAOException;
	
	/**
	 * Method updates the number of votes of a poll option with the given id
	 * 
	 * @param id poll option id
	 * @param newVotesCount new value of the votesCount parameter
	 * @return returns true if any updates were made, false otherwise
	 */
	public boolean updatePollOption(long id, long newVotesCount) throws DAOException;
	
	/**
	 * Method adds a poll to the Polls table.
	 * 
	 * @param title poll title
	 * @param message poll message
	 * @return returns id of the new poll
	 * @throws DAOException in case of an error
	 */
	public Long insertPoll(String title, String message) throws DAOException;
	
	/**
	 * Method adds a poll option to the PollOptions table
	 * 
	 * @param title poll option title
	 * @param link poll option link
	 * @param pollID poll id
	 * @param votesCount poll option votes count
	 * @return returns id of the new poll option
	 * @throws DAOException in case of an error
	 */
	public Long insertPollOption(String title, String link, long pollID, long votesCount) throws DAOException;
}