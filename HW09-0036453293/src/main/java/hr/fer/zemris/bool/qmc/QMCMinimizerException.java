/**
 * Package holding all the classes needed to implement QMC minimizer.
 */
package hr.fer.zemris.bool.qmc;

/**
 * Exception describing the error that occurred during the QMC minimization.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class QMCMinimizerException extends RuntimeException {
	
private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public QMCMinimizerException() {
	}
	
	/**
	 * Constructor.
	 * @param message error description
	 */
	public QMCMinimizerException(String message) {
		super(message);
	}
}
