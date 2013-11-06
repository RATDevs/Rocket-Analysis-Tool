package rat.calculation.math;

import java.io.Serializable;

/**
 * n-dimensional vector for system of ODEs. Implemented with standard java
 * array.
 * 
 * @author Michael Sams
 */

@SuppressWarnings("serial")
public class DoubleVectorN implements Serializable {

	private double vec[];
	private int size = 0;

	/**
	 * Constructor: defines size of vector and set all values to zero
	 * 
	 * @param size
	 *            - size of n-dimensional vector
	 */
	public DoubleVectorN(int size) {
		this.size = size;
		vec = new double[size];
		// set values to zero
		for (int i = 0; i < size; i++) {
			vec[i] = 0.0d;
		}
	}

	public DoubleVectorN(DoubleVectorN vector) {
		this.size = vector.getSize();
		vec = new double[size];
		// set values to zero
		for (int i = 0; i < size; i++) {
			vec[i] = vector.getValue(i);
		}
	}

	public DoubleVectorN(double values[]) {
		this.size = values.length;
		vec = new double[size];
		for (int i = 0; i < size; i++) {
			vec[i] = values[i];
		}
	}

	/**
	 * Getter: get size of vector
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Setter: set value on specified position of vector
	 * 
	 * @param pos
	 *            - int: position
	 * @param value
	 *            - double: value
	 */
	public void setValue(int pos, double value) {
		vec[pos] = value;
	}

	/**
	 * Setter: set all values of vector via array
	 * 
	 * @param values
	 *            - double[] of values
	 * @returns - True if the size of the vector was the same and the vector was
	 *          set.
	 */
	public boolean setValues(double values[]) {
		if (size == values.length) {
			for (int i = 0; i < size; i++) {
				vec[i] = values[i];
			}
			return true;
		}
		return false;
	}

	/**
	 * Setter: set all values of vector via another vector
	 * 
	 * @param vec
	 *            - DoubleVectorN vec
	 * @returns - True if the size of the vector was the same and the vector was
	 *          set.
	 */
	public boolean setVector(DoubleVectorN vec) {
		if (size == vec.getSize()) {
			for (int i = 0; i < size; i++) {
				this.vec[i] = vec.getValue(i);
			}
			return true;
		}
		return false;
	}

	/**
	 * Getter: get value at specified position
	 * 
	 * @param pos
	 *            - int: position
	 * @return - double: vec[pos]
	 */
	public double getValue(int pos) {
		return vec[pos];
	}

	/**
	 * Setter: scalar multiplication of vector
	 * 
	 * @param scalar
	 *            - double: scalar
	 */
	public void ScalarMult(double scalar) {
		for (int i = 0; i < size; i++) {
			vec[i] *= scalar;
		}
	}

	/**
	 * Setter: add vector to existing vector
	 * 
	 * @param vec
	 *            - DoubleVectorN: added to this.vec
	 * @return true when the size matches
	 */
	public boolean VecAdd(DoubleVectorN vec) {
		if (this.size == vec.getSize()) {
			for (int i = 0; i < size; i++) {
				this.vec[i] += vec.getValue(i);
			}
			return true;
		}
		return false;
	}
}