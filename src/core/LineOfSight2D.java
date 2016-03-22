/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Compute Line Of Sight
 * 
 * Idea : translate Wall to the inside by a very small amount.
 * @author snowgoon88@gmail.com
 */

public class LineOfSight2D extends Observable  {

	public ArrayList<Segment2D> _listWall;
	public Segment2D _targetEdge = null;
	public Vec2D _origin = null;
	public ArrayList<Segment2D> _listBlocking;
	public boolean _blocked = false;
	
	public Cell2D _cellStart = null;
	public Cell2D _cellEnd = null;
		
	static double _DELTA_WALL = 0.000001;
	
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
	public boolean compute( Cell2D start, Cell2D end ) {
		boolean res = false;
		_cellStart = start;
		_cellEnd = end;
		
		// for every segment of the end Cell
		for (int idEnd = 0; idEnd < end._corners.length; idEnd++) {
			// for every corner of the start Cell
			int idEnd1 = idEnd;
			int idEnd2 = (idEnd+1) % end._corners.length;
			Segment2D edge = new Segment2D(end._corners[idEnd1], end._corners[idEnd2]);
			System.out.println("**** SEGMENT : "+edge);
			for (int idStart = 0; idStart < start._corners.length; idStart++) {
				Vec2D pt = start._corners[idStart];
				System.out.println("**** __PT="+pt);
				// Valid directions from start
				if( start._blocked[idStart] ) {
					Vec2D dir1 = edge.start.minus(pt).normed();
					if( dir1.dotProduct(start._dirBlocked[idStart]) < start._valCosAngle[idStart] ) {
						System.out.println("__DIR1="+dir1+" wrong angle at cos="+dir1.dotProduct(start._dirBlocked[idStart])+" cosMax="+start._valCosAngle[idStart]);
						continue;
					}
					Vec2D dir2 = edge.end.minus(pt).normed();
					if( dir2.dotProduct(start._dirBlocked[idStart]) < start._valCosAngle[idStart] ) {
						System.out.println("__DIR2="+dir2+" wrong angle at cos="+dir2.dotProduct(start._dirBlocked[idStart])+" cosMax="+start._valCosAngle[idStart]);
						continue;
					}
				}
				// Valid direction to end1
				if( end._blocked[idEnd1] ) {
					Vec2D dir3 = pt.minus(edge.start).normed();
					if( dir3.dotProduct(end._dirBlocked[idEnd1]) < end._valCosAngle[idEnd1] ) {
						System.out.println("__DIR3="+dir3+" wrong angle at cos="+dir3.dotProduct(end._dirBlocked[idEnd1])+" cosMax="+end._valCosAngle[idEnd1]);
						continue;
					}
				}
				// Valid direction to end2
				if( end._blocked[idEnd2] ) {
					Vec2D dir4 = pt.minus(edge.end).normed();
					if( dir4.dotProduct(end._dirBlocked[idEnd2]) < end._valCosAngle[idEnd2] ) {
						System.out.println("__DIR4="+dir4+" wrong angle at cos="+dir4.dotProduct(end._dirBlocked[idEnd2])+" cosMax="+end._valCosAngle[idEnd2]);
						continue;
					}
				}
				// test segment
				res = compute( pt, edge );
				System.out.println("__los = "+res);
				if( res ) {
					setChanged();
					notifyObservers();
					return true;
				}
			}
		}
		setChanged();
		notifyObservers();
		return false;
	}
	public boolean compute( Cell2D cell, Segment2D edge) {
		boolean res = false;
		_cellStart = cell;
		// Check the 4 corners of the Cell
		for (int i = 0; i < cell._corners.length; i++) {
			Vec2D pt = cell._corners[i];
			System.out.println("__FROM pt="+pt);
			// Valid direction if blocked
			if( cell._blocked[i] ) {
				Vec2D dir1 = edge.start.minus(pt).normed();
				if( dir1.dotProduct(cell._dirBlocked[i]) < cell._valCosAngle[i] ) {
					System.out.println("__DIR1="+dir1+" wrong angle at cos="+dir1.dotProduct(cell._dirBlocked[i])+" cosMax="+cell._valCosAngle[i]);
					continue;
				}
				Vec2D dir2 = edge.end.minus(pt).normed();
				if( dir2.dotProduct(cell._dirBlocked[i]) < cell._valCosAngle[i] ) {
					System.out.println("__DIR2="+dir2+" wrong angle at cos="+dir2.dotProduct(cell._dirBlocked[i])+" cosMax="+cell._valCosAngle[i]);
					continue;
				}
			}
			// test segment
			res = compute( pt, edge );
			if( res ) {
				setChanged();
				notifyObservers();
				return true;
			}
		}
		setChanged();
		notifyObservers();
		return false;
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
	
	/**
	 * Add a wall that has a solid impassable material at its left.
	 */
	public void addThickWall( double sx, double sy, double ex, double ey ) {
		Vec2D start = new Vec2D( sx, sy);
		Vec2D end = new Vec2D( ex, ey );
		// orthonormal vector
		Vec2D orth = new Vec2D( -(ey-sy), (ex-sx));
		orth = orth.normed();
		// add translated Wall
		Segment2D wall = new Segment2D(start.add(orth, _DELTA_WALL), end.add(orth, _DELTA_WALL));
		_listWall.add( wall );
		System.out.println("ADD thickWall "+wall);
	}
}
