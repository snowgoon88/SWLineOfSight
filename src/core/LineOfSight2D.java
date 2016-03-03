/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Compute Line Of Sight
 * 
 * @author snowgoon88@gmail.com
 */
public class LineOfSight2D extends Observable  {

	public ArrayList<Segment2D> _listWall;
	public Segment2D _targetEdge = null;
	public Vec2D _origin = null;
	public ArrayList<Segment2D> _listBlocking;
	public boolean _blocked = false;
		
	
	/**
	 * 
	 */
	public LineOfSight2D() {
		_listWall = new ArrayList<Segment2D>();
		_listBlocking = new ArrayList<Segment2D>();
		
		initWalls();
	}
	public void initWalls() {
		
	}
	/**
	 * Very basic test (not right, to be updated).
	 * @param origin
	 * @param edge
	 * @return
	 */
	public boolean compute( Vec2D origin, Segment2D edge ) {
		_origin = origin;
		_targetEdge = edge;
		_listBlocking.clear();
		_blocked = false;
		
		// Must have non-zero angle
		//DEBUG System.out.println( "angle="+edge.end.minus(origin).vectorProduct(edge.start.minus(origin)) );
		if( edge.end.minus(origin).vectorProduct(edge.start.minus(origin)) == 0 ) {
			System.out.println("No angle");
			_blocked = true;
			setChanged();
			notifyObservers();
			return false;
		}
		
		// Segments from origin to edge extremities
		Segment2D line1 = new Segment2D( origin, edge.start );
		Segment2D line2 = new Segment2D( origin, edge.end );
		Segment2D line3 = new Segment2D( origin, edge.start.add(edge.end.minus(edge.start), 0.5) );
		System.out.println("e-s="+edge.end.minus(edge.start));
		System.out.println("s+(e-s)="+edge.start.add(edge.end.minus(edge.start), 0.5));
		System.out.println("line2="+line2);
		System.out.println("line3="+line3);
		
		// Check intersection with all Walls
		for (Segment2D wall : _listWall) {
			Intersection2D inter1 = line1.intersectWith(wall, false);
			// Check for "real" intersection
			if( inter1.seg.end == null && inter1.seg.start != null ) {
				if( inter1.t != 0 && inter1.t != 1 && inter1.u != 0 && inter1.u != 1 ) {
					System.out.println("line1 blocked");
					_blocked = true;
				}
				else {
					System.out.println("line1 NOT blocked : extremities");
				}
			}
			// Debug intersection
			if( inter1.seg.start != null || inter1.seg.end != null ) {
				System.out.println("line1 blocked by "+wall+" at "+inter1.seg);
				_listBlocking.add( inter1.seg );
			}
			
			Intersection2D inter2 = line2.intersectWith(wall, false);
			// Check for "real" intersection
			if( inter2.seg.end == null && inter2.seg.start != null ) {
				if( inter2.t != 0 && inter2.t != 1 && inter2.u != 0 && inter2.u != 1 ) {
					System.out.println("line2 blocked");
					_blocked = true;
				}
				else {
					System.out.println("line2 NOT blocked : extremities");
				}
			}
			// Debug intersection
			if( inter2.seg.start != null || inter2.seg.end != null ) {
				System.out.println("line2 blocked by "+wall+" at "+inter2.seg);
				_listBlocking.add( inter2.seg );
			}
			
			Intersection2D inter3 = line3.intersectWith(wall, false);
			// Check for "real" intersection
			if( inter3.seg.end == null && inter3.seg.start != null ) {
				if( inter3.t != 0 && inter3.t != 1 && inter3.u != 0 && inter3.u != 1 ) {
					System.out.println("line3 blocked");
					_blocked = true;
				}
				else {
					System.out.println("line3 NOT blocked : extremities");
				}
			}
			// Debug intersection
			if( inter3.seg.start != null || inter3.seg.end != null ) {
				System.out.println("line3 blocked by "+wall+" at "+inter3.seg);
				_listBlocking.add( inter3.seg );
			}
		}
		setChanged();
		notifyObservers();
		return !_blocked;
	}
}
