/**
 * 
 */
package core;

/**
 * Simple struct
 * 
 * @author snowgoon88@gmail.com
 */
public class Intersection2D {
	public Segment2D seg;
	public double t;
	public double u;
	/**
	 * @param seg
	 * @param t
	 * @param u
	 */
	public Intersection2D(Segment2D seg, double t, double u) {
		this.seg = seg;
		this.t = t;
		this.u = u;
	}
}
