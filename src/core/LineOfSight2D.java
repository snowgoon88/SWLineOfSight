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
		
		// Check intersection with all Walls
		for (Segment2D wall : _listWall) {
			Segment2D inter1 = line1.intersectWith(wall, false);
			// No intersection
			if( inter1.start != null || inter1.end != null ) {
				System.out.println("line1 blocked by "+wall+" at "+inter1);
				_listBlocking.add( inter1 );
				_blocked = true;
			}
			Segment2D inter2 = line2.intersectWith(wall, false);
			// No intersection
			if( inter2.start != null || inter2.end != null ) {
				System.out.println("line2 blocked by "+wall+" at "+inter2);
				_listBlocking.add( inter2 );
				_blocked = true;
			}
		}
		setChanged();
		notifyObservers();
		return !_blocked;
	}
}
