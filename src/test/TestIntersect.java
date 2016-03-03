/**
 * 
 */
package test;

import core.Segment2D;

/**
 * @author snowgoon88@gmail.com
 *
 */
public class TestIntersect {

	/**
	 * 
	 */
	public TestIntersect() {
		System.out.println("__COLLINEAR");
		Segment2D s1 = new Segment2D(1, 0, 2, 0);
		System.out.println("// ----");
		Segment2D c01 = s1.intersectWith(new Segment2D(0, 0, 3, 0), false);
		System.out.println("(1,0,2,0)="+c01);
		
		Segment2D c02 = s1.intersectWith(new Segment2D(0, 0, 1.6, 0), false);
		System.out.println("(1,0,1.6,0)="+c02);
		
		Segment2D c03 = s1.intersectWith(new Segment2D(0, 0, 1, 0), false);
		System.out.println("(1,0,NULL)="+c03);
		
		Segment2D c04 = s1.intersectWith(new Segment2D(0, 0, 0.9, 0), false);
		System.out.println("(NULL,NULL)="+c04);
		System.out.println("// ---");
		Segment2D c11 = s1.intersectWith(new Segment2D(1, 0, 3, 0), false);
		System.out.println("(1,0,2,0)="+c11);
		
		Segment2D c12 = s1.intersectWith(new Segment2D(1, 0, 1.6, 0), false);
		System.out.println("(1,0,1.6,0)="+c12);
		
		Segment2D c13 = s1.intersectWith(new Segment2D(1, 0, 1, 0), false);
		System.out.println("(1,0,NULL)="+c13);
		
		Segment2D c14 = s1.intersectWith(new Segment2D(1, 0, 0.9, 0), false);
		System.out.println("(NULL,NULL)="+c14);
		System.out.println("// ---");
		Segment2D c21 = s1.intersectWith(new Segment2D(1.2, 0, 3, 0), false);
		System.out.println("(1.2,0,2,0)="+c21);

		Segment2D c22 = s1.intersectWith(new Segment2D(1.2, 0, 1.6, 0), false);
		System.out.println("(1.2,0,1.6,0)="+c22);

		Segment2D c23 = s1.intersectWith(new Segment2D(1.2, 0, 1, 0), false);
		System.out.println("(1,0,1.2, 0)="+c23);

		Segment2D c24 = s1.intersectWith(new Segment2D(1.2, 0, 0.9, 0), false);
		System.out.println("(1,0,1.2, 0)="+c24);
		System.out.println("// ---");
		Segment2D c31 = s1.intersectWith(new Segment2D(2, 0, 3, 0), false);
		System.out.println("(2,0,NULL)="+c31);

		Segment2D c32 = s1.intersectWith(new Segment2D(2, 0, 1.6, 0), false);
		System.out.println("(1.6,0,2,0)="+c32);

		Segment2D c33 = s1.intersectWith(new Segment2D(2, 0, 1, 0), false);
		System.out.println("(1,0,2, 0)="+c33);

		Segment2D c34 = s1.intersectWith(new Segment2D(2, 0, 0.9, 0), false);
		System.out.println("(1,0,2, 0)="+c34);
		System.out.println("// ---");
		Segment2D c41 = s1.intersectWith(new Segment2D(2.2, 0, 3, 0), false);
		System.out.println("(NULL,NULL)="+c41);

		Segment2D c42 = s1.intersectWith(new Segment2D(2.2, 0, 1.6, 0), false);
		System.out.println("(1.6,0,2,0)="+c42);

		Segment2D c43 = s1.intersectWith(new Segment2D(2.2, 0, 1, 0), false);
		System.out.println("(1,0,2, 0)="+c43);

		Segment2D c44 = s1.intersectWith(new Segment2D(2.2, 0, 0.9, 0), false);
		System.out.println("(1,0,2, 0)="+c44);
		
		System.out.println("__PARALLEL");
		Segment2D c101 = s1.intersectWith(new Segment2D(1, 1, 2, 1), false);
		System.out.println("(NULL,NULL)="+c101);

		Segment2D c102 = s1.intersectWith(new Segment2D(2, 1, 1.6, 1), false);
		System.out.println("(NULL,NULL)="+c102);

		Segment2D c103 = s1.intersectWith(new Segment2D(-2, -1, 10, -1), false);
		System.out.println("(NULL,NULL)="+c103);

		Segment2D c104 = s1.intersectWith(new Segment2D(2, 0.2, 0.9, 0.2), false);
		System.out.println("(NULL,NULL)="+c104);
		
		System.out.println("__POSSIBLE INTERSECT");
		Segment2D c201 = s1.intersectWith(new Segment2D(1, -1, 2, 1), false);
		System.out.println("(1.5,0,NULL)="+c201);

		Segment2D c202 = s1.intersectWith(new Segment2D(1, -1, 2, 0), false);
		System.out.println("(2,0,NULL)="+c202);

		Segment2D c203 = s1.intersectWith(new Segment2D(1, -1, 1.5, -0.1), false);
		System.out.println("(NULL,NULL)="+c203);

		Segment2D c204 = s1.intersectWith(new Segment2D(1, 1, 1.1, 0.1), false);
		System.out.println("(NULL,NULL)="+c204);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestIntersect app = new TestIntersect();

	}

}
