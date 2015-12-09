package hr.fer.zemris.web.dao.sql;

import hr.fer.zemris.web.dao.DAO;
import hr.fer.zemris.web.dao.DAOException;
import hr.fer.zemris.web.model.PollEntry;
import hr.fer.zemris.web.model.PollOptionEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * CLass implementing the DAO interface using the SQL requests to communicate with the database.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SQLDAO implements DAO {

	/**
	 * @see hr.fer.zemris.web.dao.DAO#getAllPolls()
	 */
	@Override
	public List<PollEntry> getAllPolls() throws DAOException {
		List<PollEntry> pollEntrys = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs != null && rs.next()) {
						PollEntry pollEntry = new PollEntry();
						pollEntry.setId(rs.getLong(1));
						pollEntry.setTitle(rs.getString(2));
						pollEntry.setMessage(rs.getString(3));
						pollEntrys.add(pollEntry);
					}
				} finally {
					try { 
						rs.close();
					} catch(Exception e1) {
						System.out.println("An exception occured.");
					}
				}
			} finally {
				try { 
					pst.close();
				} catch(Exception e1) {
					System.out.println("An exception occured.");
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste anketa.");
		}
		return pollEntrys;
	}

	/**
	 * @see hr.fer.zemris.web.dao.DAO#getPoll(long)
	 */
	@Override
	public List<PollEntry> getPoll(long id) throws DAOException {
		List<PollEntry> polls = new ArrayList<>();
		PollEntry pollEntry;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						pollEntry = new PollEntry();
						pollEntry.setId(rs.getLong(1));
						pollEntry.setTitle(rs.getString(2));
						pollEntry.setMessage(rs.getString(3));
						polls.add(pollEntry);
					}
				} finally {
					try { rs.close(); } catch(Exception e1) {
						System.out.println("An exception occured.");
					}
				}
			} finally {
				try { pst.close(); } catch(Exception e1) {
					System.out.println("An exception occured.");
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata ankete.");
		}
		return polls;
	}

	/**
	 * @see hr.fer.zemris.web.dao.DAO#getAllPollOptions()
	 */
	@Override
	public List<PollOptionEntry> getAllPollOptions() throws DAOException {
		List<PollOptionEntry> pollOptionEntrys = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from " +
					"PollOptions order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs != null && rs.next()) {
						PollOptionEntry pollOptionEntry = new PollOptionEntry();
						pollOptionEntry.setId(rs.getLong(1));
						pollOptionEntry.setOptionTitle(rs.getString(2));
						pollOptionEntry.setOptionLink(rs.getString(3));
						pollOptionEntry.setPollID(rs.getLong(4));
						pollOptionEntry.setVotesCount(rs.getLong(5));
						pollOptionEntrys.add(pollOptionEntry);
					}
				} finally {
					try { 
						rs.close();
					} catch(Exception e1) {
						System.out.println("An exception occured.");
					}
				}
			} finally {
				try { 
					pst.close();
				} catch(Exception e1) {
					System.out.println("An exception occured.");
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste opcija.");
		}
		return pollOptionEntrys;
	}

	/**
	 * @see hr.fer.zemris.web.dao.DAO#getPollOption(long)
	 */
	@Override
	public List<PollOptionEntry> getPollOption(long id) throws DAOException {
		return acquireOption(id, false); 
	}
	
	/**
	 * @see hr.fer.zemris.web.dao.DAO#getPollOptionsByPollID(long)
	 */
	@Override
	public List<PollOptionEntry> getPollOptionsByPollID(long id) throws DAOException {
		return acquireOption(id, true);
	}
	
	/**
	 * Method used to acquire a poll option from the database. Based on the variable byPollID the method
	 * will search for all the options with the poll ID or options with teir id.
	 * 
	 * @param id option or poll id, based on the byPollID variable
	 * @param byPollID true if the given id is pollID, false if it is the option ID
	 * @return returns a list of all found options
	 */
	private List<PollOptionEntry> acquireOption(long id, boolean byPollID) {
		PollOptionEntry pollOptionEntry;
		List<PollOptionEntry> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			if(byPollID) {
				pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from" +
					" PollOptions where pollID=? order by votesCount desc, optionTitle");
			} else {
				pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from" +
						" PollOptions where id=? order by votesCount desc, optionTitle");
			}
			
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs != null && rs.next()) {
						pollOptionEntry = new PollOptionEntry();
						pollOptionEntry.setId(rs.getLong(1));
						pollOptionEntry.setOptionTitle(rs.getString(2));
						pollOptionEntry.setOptionLink(rs.getString(3));
						pollOptionEntry.setPollID(rs.getLong(4));
						pollOptionEntry.setVotesCount(rs.getLong(5));
						pollOptions.add(pollOptionEntry);
					}
				} finally {
					try { rs.close(); } catch(Exception e1) {
						System.out.println("An exception occured.");
					}
				}
			} finally {
				try { pst.close(); } catch(Exception e2) {
					System.out.println("An exception occured.");
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata opcija.");
		}
		return pollOptions;
	}
	
	/**
	 * @see hr.fer.zemris.web.dao.DAO#updatePollOption(long, long)
	 */
	@Override
	public boolean updatePollOption(long id, long newVotesCount) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		int numberOfAffectedRows = 0;
		try {
			pst = con.prepareStatement("UPDATE PollOptions set votesCount=? WHERE id=?");
			pst.setLong(2, id);
			pst.setLong(1, newVotesCount);

			numberOfAffectedRows = pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		if(numberOfAffectedRows > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @see hr.fer.zemris.web.dao.DAO#insertPoll(java.lang.String, java.lang.String)
	 */
	@Override
	public Long insertPoll(String title, String message) throws DAOException {
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("INSERT INTO Polls (title, message) values " +
					"(?,?)", Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, title);
			pst.setString(2, message);

			pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1
			
			ResultSet rset = pst.getGeneratedKeys();
			
			try {
				if(rset != null && rset.next()) {
					long noviID = rset.getLong(1);
					return noviID;
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * @see hr.fer.zemris.web.dao.DAO#insertPollOption(java.lang.String, java.lang.String, long, long)
	 */
	@Override
	public Long insertPollOption(String title, String link, long pollID, long votesCount) throws DAOException {
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values " +
					"(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, title);
			pst.setString(2, link);
			pst.setLong(3, pollID);
			pst.setLong(4, votesCount);

			pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1
			
			ResultSet rset = pst.getGeneratedKeys();
			
			try {
				if(rset != null && rset.next()) {
					long noviID = rset.getLong(1);
					return noviID;
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
