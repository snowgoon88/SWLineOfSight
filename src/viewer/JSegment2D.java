/**
 * 
 */
package viewer;

import java.awt.Color;

import core.Segment2D;

/**
 * Essentially a 'struct' Class
 * - Segment2D
 * - Color
 * - Width
 * 
 * @author snowgoon88@gmail.com
 */
public class JSegment2D {
	public Segment2D segment;
	public Color col;
	public int width;
	/**
	 * Basic Constructor.
	 */
	public JSegment2D(Segment2D segment, Color col, int width) {
		this.segment = segment;
		this.col = col;
		this.width = width;
	}
}
