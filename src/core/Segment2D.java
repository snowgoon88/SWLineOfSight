/**
 * 
 */
package core;

/**
 * 
 * @author snowgoon88@gmail.com
 */
public class Segment2D {

	public Vec2D start;
	public Vec2D end;
	/**
	 * 
	 */
	public Segment2D() {
		this.start = new Vec2D(0, 0);
		this.end = new Vec2D(1,0);
	}
	public Segment2D( double sx, double sy, double ex, double ey) {
		this.start = new Vec2D(sx,sy);
		this.end = new Vec2D(ex,ey);
	}
	public Segment2D( Vec2D start, Vec2D end) {
		this.start = start;
		this.end = end;
	}
	@Override
	public String toString() {
		String res = (this.start != null ? this.start.toString() : "N")
				+"->"
				+(this.end != null ? this.end.toString() : "N");
		return res;
	}

	/**
	 * Test for intersection with another Segment.
	 * 
	 * Returns : Segment2D res
	 * - if no intersection res = (null,null)
	 * - if one point res = (pt,null)
	 * - if common segment res = (pt1,pt2), oriented as this
	 */
	public Segment2D intersectWith( Segment2D other, boolean verb ) {
		Segment2D res;

		// Express segment as orig+vector
		Vec2D vec1 = this.end.minus( this.start);
		Vec2D vec2 = other.end.minus( other.start );
		if(verb) {
			System.out.println("vec1="+vec1+"\tvec2="+vec2);
		}

		double vp21 = vec2.vectorProduct(vec1);
		Vec2D o21 = other.start.minus( this.start );
		double vpO21V1 = o21.vectorProduct(vec1);

		// collinear
		if( vp21 == 0 && vpO21V1 == 0 ) {
			// Intersection points
			double t0 = o21.dotProduct(vec1) / vec1.dotProduct(vec1);
			double t1 = t0 + vec2.dotProduct(vec1) / vec1.dotProduct(vec1);
			if(verb) {
				System.out.println("t0="+t0+"\tt1="+t1);
			}

			// vec1 and vec2 in opposite direction ?
			if( vec2.dotProduct(vec1) < 0 ) {
				double tmp = t1;
				t1 = t0;
				t0 = tmp;
			}
			if( t0 <= 0 ) {
				if( t1 > 1) {
					res = new Segment2D( this.start, this.end );
					return res;
				}
				else if (t1 > 0 ) {
					Vec2D iEnd = new Vec2D( this.start.x + t1*vec1.x,
							this.start.y + t1*vec1.y);
					res = new Segment2D( this.start, iEnd );
					return res;
				}
				else if (t1 == 0) {
					res = new Segment2D( this.start, null );
					return res;
				}
				else {
					res  = new Segment2D(null, null);
					return res;
				}
			}
			else if( t0 < 1) {
				Vec2D iStart = new Vec2D(this.start.x+t0*vec1.x,
						this.start.y+t0*vec1.y);
				if( t1 > 1) {
					res = new Segment2D( iStart, this.end );
					return res;
				}
				else if (t1 > 0 ) {
					Vec2D iEnd = new Vec2D( this.start.x + t1*vec1.x,
							this.start.y + t1*vec1.y);
					res = new Segment2D( iStart, iEnd );
					return res;
				}
				else if (t1 == 0) {
					System.out.println("t0 < 1 et t1==0");
					return null;
				}
				else {
					System.out.println("t0 < 1 et t1 < 0");
					return null;
				}
			}
			else if( t0 == 1) {
				if( t1 >= t0 ) {
					res = new Segment2D( this.end, null);
					return res;
				}
				else {
					System.out.println("t0 == 1 et t1 < t0");
					return null;
				}
			}
			else {
				res  = new Segment2D(null, null);
				return res;
			}
		}
		// parallel and no intersect
		else if( vp21 == 0 && vpO21V1 != 0) {
			res  = new Segment2D(null, null);
			return res;
		}
		// possible intersection as( vp21 != 0 )
		else {
			double t = o21.vectorProduct(vec2) / vec1.vectorProduct(vec2);
			double u = o21.vectorProduct(vec1) / vec1.vectorProduct(vec2);

			// intersection
			if( (t >= 0) && (t <= 1) && (u >= 0) && (u <= 1)) {
				Vec2D pt = new Vec2D( this.start.x + t*vec1.x,
						this.start.y + t*vec1.y);
				res = new Segment2D( pt, null );
				return res;
			}
			// no intersection
			else {
				res  = new Segment2D(null, null);
				return res;
			}
		}
	}
}
