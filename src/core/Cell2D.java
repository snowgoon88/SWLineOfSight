/**
 * 
 */
package core;

import java.util.ArrayList;

/**
 * Vitual Cell with 'origin' and 'corners'
 * 
 * @author snowgoon88@gmail.com
 */
public class Cell2D {

	public Vec2D _origin;
	Vec2D[] _corners;
	
	public enum Edge { NONE, THICK, SLIM };
	Edge _sideType[] = { Edge.NONE, Edge.NONE, Edge.NONE, Edge.NONE };
	boolean _blocked[] = {false, false, false, false };
	
	final static Vec2D _vec[] = { new Vec2D(1,1).normed(), new Vec2D(-1,1).normed(),
		new Vec2D(-1,-1).normed(), new Vec2D(1,-1).normed() };
	/**
	 * 
	 */
	public Cell2D( Vec2D origin) {
		_origin = origin;
		
		// make Corners
		_corners = new Vec2D[4];
		_corners[0] = _origin;
		_corners[1] = _origin.add(new Vec2D(1, 0), 1);
		_corners[2] = _corners[1].add( new Vec2D(0,1), 1);
		_corners[3] = _corners[2].add( new Vec2D(-1,0), 1);
	}
	public String toString() {
		String res = "[ori="+_origin+" : ";
		for (int i = 0; i < _blocked.length; i++) {
			res += _blocked[i]+"; ";
		}
		res += "]";
		return res;
	};
	
	/**
	 * Check every _corners to see if "blocked"
	 */
	public void findPbCorners( ArrayList<Segment2D> walls ) {
		for (int i = 0; i < _corners.length; i++) {
			if( findWall(_corners[i], _corners[(i+1)%4], walls) 
					&& 	findWall(_corners[(i+1)%4], _corners[(i+2)%4], walls)) {
				_blocked[(i+1)%4] = true;
			}	
		}
	}
	/**
	 * true if a Wall between pt1 and pt2.
	 */
	boolean findWall( Vec2D pt1, Vec2D pt2, ArrayList<Segment2D> walls ) {
		for (Segment2D seg : walls) {
			if( (seg.start.isNear(pt1) && seg.end.isNear(pt2)) ||
					(seg.start.isNear(pt2) && seg.end.isNear(pt1)) ) {
				return true;
			}
		}
		return false;
	}
}
