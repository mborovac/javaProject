package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to create a table with 2 columns. 1st column are normal numbers from a to b, 2nd
 * column are the 1st coumn's squares.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SquaresServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method requires 2 parameters: a and b. It creates a table with 2 columns. 1st Column contains
	 * numbers from a to b and the 2nd column consists of the 1st column number's squares.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Integer a = null;
		Integer b = null;
		
		try {
			a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception e) {
			a = 0;
		}
		
		try {
			b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception e) {
			b = 0;
		}
		
		if(a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		if(a - b > 20) {
			b = a + 20;
		}
		
		List<Pair> elements = new ArrayList<>();
		for(int i = a; i <= b; i++) {
			elements.add(new Pair(i, i*i));
		}
		
		req.setAttribute("result", elements);
		req.getRequestDispatcher("/WEB-INF/pages/squares.jsp").forward(req, resp);
	}
	
	/**
	 * Static class used to represent a pair of numbers, the original number and it's square value.
	 */
	public static class Pair {
		int number;
		int square;
		
		/**
		 * Class constructor.
		 * 
		 * @param number
		 * @param square
		 */
		public Pair(int number, int square) {
			super();
			this.number = number;
			this.square = square;
		}
		
		/**
		 * Number getter.
		 * 
		 * @return the number
		 */
		public int getNumber() {
			return number;
		}
		
		/**
		 * Square value getter.
		 * 
		 * @return the square
		 */
		public int getSquare() {
			return square;
		}
	}
}
