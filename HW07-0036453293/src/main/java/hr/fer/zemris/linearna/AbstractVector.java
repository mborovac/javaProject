/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

import java.util.Locale;

/**
 * Abstract class used to implement all of the general vector methods. Specific methods must be
 * implemented in the classes extending this one. Class implements IVector interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class AbstractVector implements IVector {
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#get(int)
	 */
	public abstract double get(int index);
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#set(int, double)
	 */
	public abstract IVector set(int index, double value)
			throws UnmodifiableObjectException;
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#getDimension()
	 */
	public abstract int getDimension();
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#copy()
	 */
	public abstract IVector copy();
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#copyPart(int)
	 * 
	 * Method creates a new vector of the given dimensions and fills it with the elements
	 * of the current vector as much as it can. If the new vector is greater than the current one,
	 * the rest of the elements are 0.
	 */
	public IVector copyPart(int n) {
		IVector newVector = newInstance(n);
		for(int i = 0; i < this.getDimension(); i++) {
			if(i == n) {
				break;
			}
			newVector.set(i, this.get(i));
		}
		if(n > this.getDimension()) {
			for(int i = this.getDimension(); i < n; i++) {
				newVector.set(i, 0);
			}
		}
		return newVector;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#newInstance(int)
	 */
	public abstract IVector newInstance(int dimension);
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#add(hr.fer.zemris.linearna.IVector)
	 * 
	 * Method adds 2 vectors. Method returns current modified vector.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for(int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#nAdd(hr.fer.zemris.linearna.IVector)
	 * 
	 * Method adds 2 vectors. Method returns a new vector, the resulting vector.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#sub(hr.fer.zemris.linearna.IVector)
	 * 
	 * Method subtracts 2 vectors. Method returns current modified vector.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for(int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#nSub(hr.fer.zemris.linearna.IVector)
	 * 
	 * Method subtracts 2 vectors. Method returns a new vector, the resulting vector
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return this.copy().sub(other);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#scalarMultiply(double)
	 * 
	 * Method multiplies the current vector with a scalar. Method returns the
	 * current modified vector.
	 */
	@Override
	public IVector scalarMultiply(double byValue) {
		for(int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#nScalarMultiply(double)
	 * 
	 * Method multiplies the current vector with a scalar. Method returns a new vector, 
	 * the resulting vector
	 */
	@Override
	public IVector nScalarMultiply(double byValue) {
		return this.copy().scalarMultiply(byValue);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#norm()
	 * 
	 * Method calculates the L2-norm of a vector.
	 */
	@Override
	public double norm() {
		int sumOfSquares = 0;
		for(int i = 0; i < this.getDimension(); i++) {
			sumOfSquares += Math.pow(this.get(i), 2);
		}
		return Math.sqrt(sumOfSquares);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#normalize()
	 * 
	 * Method normalizes the vector. Method returns the
	 * current modified vector.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector normalize() {
		double norm = this.norm();
		if(norm == 0) {
			throw new IncompatibleOperandException();
		}
		for(int i = 0; i < this.getDimension(); i++) {
			this.set(i, this.get(i)/norm);
		}
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#nNormalize()
	 * 
	 * Method normalizes the vector. Method returns a new vector, 
	 * the resulting vector
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#cosine(hr.fer.zemris.linearna.IVector)
	 * 
	 * Method calculates the cosinus of the angle between 2 vectors.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		if(this.norm() == 0 || other.norm() == 0) {
			throw new IncompatibleOperandException();
		}
		return this.scalarProduct(other) / (this.norm()*other.norm());
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#scalarProduct(hr.fer.zemris.linearna.IVector)
	 * 
	 * Method calculates the scalar product of 2 vectors.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		double scalarProduct = 0;
		for(int i = 0; i < this.getDimension(); i++) {
			scalarProduct += this.get(i) * other.get(i);
		}
		return scalarProduct;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#nVectorProduct(hr.fer.zemris.linearna.IVector)
	 * 
	 * Method calculates the vector product of 2 vectors. Method returns a new vector,
	 * the resulting vector.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if(this.getDimension() != 3 || other.getDimension() != 3) {
			throw new IncompatibleOperandException();
		}
		IVector vectorProduct = this.newInstance(this.getDimension());
		vectorProduct.set(0, this.get(1)*other.get(2) - this.get(2)*other.get(1));
		vectorProduct.set(1, this.get(2)*other.get(0) - this.get(0)*other.get(2));
		vectorProduct.set(2, this.get(0)*other.get(1) - this.get(1)*other.get(0));
		return vectorProduct;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#nFromHomogeneus()
	 * 
	 * Method creates a new vector calculated from the current vector as if it were in a 
	 * homogeneus space.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector nFromHomogeneus() {
		if(this.getDimension() < 2) {
			throw new IncompatibleOperandException();
		}
		IVector homogenousVector = this.newInstance(this.getDimension() - 1);
		double lastElement = this.get(this.getDimension() - 1);
		if(lastElement == 0) {
			throw new IncompatibleOperandException();
		}
		for(int i = 0; i < this.getDimension() - 1; i++) {
			homogenousVector.set(i, this.get(i)/lastElement);
		}
		return homogenousVector;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#toRowMatrix(boolean)
	 * 
	 * Method creates a single-row matrix from the current vector.
	 * Method can return a live view of the current vector or a matrix
	 * completely separated from the current vector, depending on the argument.
	 */
	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		if(liveView) {
			return new MatrixVectorView(this, true);
		} else {
			return LinAlgDefaults.defaultMatrix(1, this.getDimension());
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#toColumnMatrix(boolean)
	 * 
	 * Method creates a single-column matrix from the current vector.
	 * Method can return a live view of the current vector or a matrix
	 * completely separated from the current vector, depending on the argument.
	 */
	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		if(liveView) {
			return new MatrixVectorView(this, false);
		} else {
			return LinAlgDefaults.defaultMatrix(this.getDimension(), 1);
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IVector#toArray()
	 */
	@Override
	public double[] toArray() {
		double[] vectorAsArray = new double[this.getDimension()];
		for(int i = 0; i < this.getDimension(); i++) {
			vectorAsArray[i] = this.get(i);
		}
		return vectorAsArray;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * 
	 * Creates a String representation of the vactor.
	 * 
	 * Format of a 3D vector would be:
	 * 		[element1, element2, element3]
	 */
	public String toString() {
		return toString(3);
	}
	
	/**
	 * More specific version of the toString() method. Method specifies the precision of the
	 * printed vector' elements.
	 * 
	 * Format of a 3D vector would be:
	 * 		[element1, element2, element3]
	 * 
	 * @param precision the specified precision of the printed elements
	 * @return returns the string representation of the vector with desired precision
	 */
	public String toString(int precision) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < getDimension(); i++) {
			sb.append(String.format(Locale.ENGLISH, "%." + precision + "f", get(i)));
			if(i != getDimension() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
