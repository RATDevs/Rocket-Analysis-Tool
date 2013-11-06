package rat.calculation.calculator.calculators.rungekutta;

import java.io.Serializable;

import rat.calculation.math.DoubleMath;
import rat.calculation.math.DoubleVector3;
import rat.calculation.math.DoubleVectorN;
import rat.calculation.planet.Planet;

/**
 * Helper class for simulation.
 * 
 * @author Miachel Sams
 * 
 *         System an nichtlinearen DGLs 1.Ordnung
 * 
 *         [ lambda_dot ] | Phi_dot | | h_dot | f^dot = | V_dot | | chi_dot | |
 *         gamma_dot | [ s_dot ]
 * 
 */

@SuppressWarnings("serial")
public class DotVec implements Serializable {

	// calculated variables
	private double lambda_dot, Phi_dot, h_dot, V_dot, chi_dot, gamma_dot,
			s_dot;
	private DoubleVectorN SysVec;

	public DotVec() {
		SysVec = new DoubleVectorN(7);
	}

	public void CalcDots(DoubleVectorN InpVec, DoubleVector3 F_T_K, double tn,
			Planet planet, double m, double timeGamma, double startChi) {
		double sinWGSy = DoubleMath.square(Math.sin(InpVec.getValue(1)));
		double N_phi = planet.a / Math.sqrt(1 - planet.e2 * sinWGSy);
		double M_phi = N_phi * (1 - planet.e2) / (1 - planet.e2 * sinWGSy);
		double temp1, temp2, temp3;

		lambda_dot = Math.sin(InpVec.getValue(4))
				* Math.cos(InpVec.getValue(5)) * InpVec.getValue(3)
				/ ((N_phi + InpVec.getValue(2)) * Math.cos(InpVec.getValue(1)));

		Phi_dot = Math.cos(InpVec.getValue(4)) * Math.cos(InpVec.getValue(5))
				* InpVec.getValue(3) / (M_phi + InpVec.getValue(2));

		h_dot = Math.sin(InpVec.getValue(5)) * InpVec.getValue(3);

		V_dot = (2 * F_T_K.x + m
				* DoubleMath.square(planet.getOmega())
				* (DoubleMath.square(2 * Math.cos(InpVec.getValue(1)))
						* Math.sin(InpVec.getValue(5)) - Math.cos(InpVec
						.getValue(5))
						* Math.cos(InpVec.getValue(4))
						* Math.sin(2 * InpVec.getValue(1)))
				* (InpVec.getValue(2) + N_phi)) / (2 * m);

		temp1 = 1 / (InpVec.getValue(3));
		temp2 = (2 * planet.getOmega() + lambda_dot);
		if (InpVec.getValue(5) < startChi) {
			chi_dot = temp1
					* (F_T_K.y / (Math.cos(InpVec.getValue(5)) * m) + 1 * (Math
							.sin(InpVec.getValue(1))
							* InpVec.getValue(3)
							* temp2
							- Math.cos(InpVec.getValue(1))
							* Math.cos(InpVec.getValue(4))
							* InpVec.getValue(3)
							* temp2 * Math.tan(InpVec.getValue(5)) + Math
							.sin(InpVec.getValue(4))
							* ((DoubleMath.square(planet.getOmega())
									* Math.cos(InpVec.getValue(1))
									* Math.sin(InpVec.getValue(1)) * (InpVec
									.getValue(2) + N_phi))
									/ Math.cos(InpVec.getValue(5)) + InpVec
									.getValue(3)
									* Phi_dot
									* Math.tan(InpVec.getValue(5)))));
		} else {
			chi_dot = 0.0;
		}

		temp3 = Math.cos(InpVec.getValue(5)) * Math.cos(InpVec.getValue(1))
				+ Math.cos(InpVec.getValue(4)) * Math.sin(InpVec.getValue(5))
				* Math.sin(InpVec.getValue(1));
		if (tn > timeGamma) {
			gamma_dot = temp1
					* (-F_T_K.z / m + InpVec.getValue(2)
							* DoubleMath.square(planet.getOmega())
							* Math.cos(InpVec.getValue(1)) * temp3
							+ DoubleMath.square(planet.getOmega())
							* Math.cos(InpVec.getValue(1)) * temp3 * N_phi + InpVec
							.getValue(3)
							* (Math.cos(InpVec.getValue(1))
									* Math.sin(InpVec.getValue(4)) * temp2 + Math
									.cos(InpVec.getValue(4)) * Phi_dot));
		} else {
			gamma_dot = 0;
		}

		s_dot = InpVec.getValue(3) * Math.cos(InpVec.getValue(5)) * N_phi
				/ (N_phi + InpVec.getValue(2));

		// set SysVec
		// SysVec = new DoubleVectorN(7);
		SysVec.setValue(0, lambda_dot);
		SysVec.setValue(1, Phi_dot);
		SysVec.setValue(2, h_dot);
		SysVec.setValue(3, V_dot);
		SysVec.setValue(4, chi_dot);
		SysVec.setValue(5, gamma_dot);
		SysVec.setValue(6, s_dot);
	}

	public DoubleVectorN getSysVec() {
		return SysVec;
	}
}