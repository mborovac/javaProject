package hr.fer.zemris.web.voting;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Servlet used as a servlet context listener. Once the servlet container is activated the listener
 * connects to the database and initializes the connection pool.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Inicijalizacija implements ServletContextListener {
	
	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 * 
	 * Method connects to the database and creates a connection pool.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String dbName="votingDB";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName + ";user=ivica;password=ivo";
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}
	
	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 * 
	 * Method destroys the connection pool.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
