/**
 * 
 */
package viewer;

import java.awt.Color;

import core.Vec2D;

/**
 * Essentially a Struct Class
 * 
 * @author snowgoon88@gmail.com
 */
public class JPoint2D {
	public Vec2D pt;
	public Color col;
	public int width;
	/**
	 * Basic Constructor.
	 */
	public JPoint2D(Vec2D pt, Color col, int width) {
		this.pt = pt;
		this.col = col;
		this.width = width;
	}
}
