/**
 * 
 */
package core;

import java.text.DecimalFormat;

/**
 * Minimal 2D vector implementation.
 * 
 * @author snowgoon88@gmail.com
 */
public class Vec2D {
	/** Decimal formating */
	static DecimalFormat df3_2 = new DecimalFormat( "0.00" );
	
	/** Two coordinate */
	public double x; 
	public double y;
	/**
	 * Constructor
	 */
	public Vec2D() {
		this.x = 0;
		this.y = 0;
	}
	public Vec2D( double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vec2D( Vec2D other) {
		this.x = other.x;
		this.y = other.y;
	}
	@Override
	/**
	 * Format : (x; y)
	 */
	public String toString() {
		String res = "("+df3_2.format(this.x)+"; "+df3_2.format(this.y)+")";
		return res;
	}
	
	/**
	 * Basic operation
	 */
	public Vec2D minus( Vec2D other) {
		Vec2D res = new Vec2D( this.x-other.x, this.y-other.y);
		return res;
	}
	public Vec2D add( Vec2D other, double lambda) {
		Vec2D res = new Vec2D( this.x+lambda*other.x, this.y+lambda*other.y);
		return res;
	}
	/**
	 * Kind of vector product : 3rd composant of vector product
	 */
	public double vectorProduct( Vec2D v) {
		return this.x*v.y - this.y*v.x;
	}
	/**
	 * Dot product.
	 */
	public double dotProduct( Vec2D other ) {
		return this.x * other.x + this.y * other.y;
	}
}
