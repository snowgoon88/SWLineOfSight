/**
 * 
 */
package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import core.LineOfSight2D;
import core.Segment2D;
import core.Vec2D;

import viewer.JPlane2D;
import viewer.JPoint2D;
import viewer.JSegment2D;

/**
 * Une façon qui peut marcher de détecter les cas compliqué.
 * - toujours orienter les murs avec l'intérieur à main gauche
 * - les murs sans épaisseurs sont représentés par 2 segments, mais il faut 
 *   prendre le cas le plus arrangeant...
 * - on peut savoir si on est colinéaire à deux segments
 *     + dans le même sens => ok on suit un mur
 *     + dans des sens différents => ligne passe intérerieur ET extérieur : pas bon
 * @author snowgoon88@gmail.com
 */
public class TestJPlane {

	/** Fenetre principale de l'application */
	JFrame _frame;
	/** Pour afficher */
	JPlane2D _jPlane;
	
	/**
	 * 
	 */
	public TestJPlane() {
		// Setup window
		_frame = new JFrame("JPlane2D");
		_frame.setSize(800,800);
		_frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		_frame.setLayout(new BorderLayout());
		
		_jPlane = new JPlane2D(-5, 5, -5, 5);
//		_jPlane.listSeg.add(new JSegment2D(
//				new Segment2D(1, 1, 2, 3),
//				Color.BLUE, 1));
		
		_frame.add( _jPlane, BorderLayout.CENTER );
		
		_frame.setVisible(true);
	}
	/** 
	 * Tests intersections between 2 segments and dispay results.
	 */
	public void showIntersect( Segment2D s1, Segment2D s2) {
		Segment2D res = s1.intersectWith(s2, false);
		System.out.println("res="+res);
		_jPlane.listSeg.add(new JSegment2D(
				s1, Color.BLUE, 1));
		_jPlane.listSeg.add(new JSegment2D(
				s2, Color.GREEN, 1));
		
		if( res.start != null && res.end != null ) {
			_jPlane.listSeg.add(new JSegment2D(
					res, Color.RED, 1));
		}
		else if( res.start != null ) {
			_jPlane.listPoint.add( new JPoint2D(
					res.start, Color.RED, 1));
		}
	}
	public void testLoS() {
		LineOfSight2D los = new LineOfSight2D();
		
		// Some Walls
		los._listWall.add(new Segment2D(0, 1, 0, 2));
		los._listWall.add(new Segment2D(0, 2, 1, 2));
		los._listWall.add(new Segment2D(1, 2, 1, 3));
		los._listWall.add(new Segment2D(1, 3, 0, 3));
		
		// MVC components
		_jPlane._model = los;
		los.addObserver( _jPlane );
		
		//los.compute( new Vec2D(1, 0), new Segment2D(0, 3, 1, 3));
		los.compute( new Vec2D(1, 0), new Segment2D(1, -1, 1, -2));
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestJPlane app = new TestJPlane();
		
//		app.showIntersect(new Segment2D(1, 1, 3, 2),
//				new Segment2D(2, 0, 0, 3));
//		
//		app.showIntersect(new Segment2D(1, 0, 2, 1),
//				new Segment2D(1.5, 0.5, 3, 2));
		
		app.testLoS();
		while (true) {	
		}
	}

}
