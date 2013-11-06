package rat.controller.dataObjects;

/**
 * 
 * A Bean-Class that contains all the general settings and values. Is used to
 * transport those values for a better seperation.
 * 
 * @author Nils Vißmann
 * 
 */
public class GeneralValueBean {

	private double timeStep;
	private double cancelTime;
	private double firstOutIteration;

	private double startTime;
	private double heightAboveEllipsoid;
	private double initialHeight;
	private double initialSpeed;

	private double longitude;
	private double latitude;

	private double alpha;
	private double direction;

	public GeneralValueBean(double timeStep, double cancelTime,
			double outIterations, double startTime,
			double heightAboveEllipsoid, double initialHeight,
			double initialSpeed, double longitude, double latitude,
			double alpha, double direction) {
		super();
		this.timeStep = timeStep;
		this.cancelTime = cancelTime;
		this.firstOutIteration = outIterations;
		this.startTime = startTime;
		this.heightAboveEllipsoid = heightAboveEllipsoid;
		this.initialHeight = initialHeight;
		this.initialSpeed = initialSpeed;
		this.longitude = longitude;
		this.latitude = latitude;
		this.alpha = alpha;
		this.direction = direction;
	}

	public double getTimeStep() {
		return timeStep;
	}

	public double getCancelTime() {
		return cancelTime;
	}

	public double getFirstOutIteration() {
		return firstOutIteration;
	}

	public double getStartTime() {
		return startTime;
	}

	public double getHeightAboveEllipsoid() {
		return heightAboveEllipsoid;
	}

	public double getInitialHeight() {
		return initialHeight;
	}

	public double getInitialSpeed() {
		return initialSpeed;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getAlpha() {
		return alpha;
	}

	public double getDirection() {
		return direction;
	}

}
