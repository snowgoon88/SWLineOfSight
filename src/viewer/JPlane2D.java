/**
 * 
 */
package viewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * 
 * @author snowgoon88@gmail.com
 */
@SuppressWarnings("serial")
public class JPlane2D extends JPanel implements Observer {

	/** Bounds of the model_canvas */
	double _minX = -1.0, _maxX = 1.0;
	/** Bounds of the model_canvas */
	double _minY = -1.0, _maxY = 1.0;
	/** Bounds of the window_canvas */
	Dimension _size;
	
	/** Draw a bunch of Segments */
	public ArrayList<JSegment2D> listSeg;
	/** Draw a bunch of Points */
	public ArrayList<JPoint2D> listPoint;
	
	/**
	 * 
	 */
	public JPlane2D( double minX, double maxX, double minY, double maxY ) {
		super();
		_minX = minX;
		_maxX = maxX;
		_minY = minY;
		_maxY = maxY;
		
		listSeg = new ArrayList<JSegment2D>();
		listPoint = new ArrayList<JPoint2D>();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
        _size = this.getSize();
        
        // Essayer de tracer une croix -1,1; -1,1
        g.drawLine(xWin(-0.5),yWin(0.0), xWin(0.5), yWin(0.0) );
        g.drawLine(xWin(0.0),yWin(-0.5), xWin(0.0), yWin(0.5) );
        
        // Small crosses at integer coord
        for ( double posX = Math.floor(_minX); posX <= Math.ceil(_maxX);
        		posX = posX + 1.0 ) {
        	for ( double posY = Math.floor(_minY); posY <= Math.ceil(_maxY);
            		posY = posY + 1.0 ) {
        		drawRefPt(g, posX, posY);
        	}
        }
        
        // All the segments
        for (JSegment2D seg : listSeg) {
			g.setColor(seg.col);
			g.drawLine(xWin(seg.segment.start.x),yWin(seg.segment.start.y),
					xWin(seg.segment.end.x), yWin(seg.segment.end.y));
		}
        // All the points
        for (JPoint2D point : listPoint) {
        	g.setColor(point.col);
			drawArc(g, point.pt.x, point.pt.y,
					0.05, 0, Math.PI*2);
		}
    }
	
	/**
	 * Draw a small cross at a given point.
	 * @return 
	 */
	private void drawRefPt( Graphics g, double posX, double posY) {
		
		g.drawLine(xWin(posX-0.05),yWin(posY),
				xWin(posX+0.05), yWin(posY) );
		g.drawLine(xWin(posX),yWin(posY-0.05),
				xWin(posX), yWin(posY+0.05) );
		
	}
	
	/**
	 * Compute the x_window_point from the x_model_point
	 * @return window x 
	 */
	private int xWin( double x) {
		double size = Math.min(_size.width, _size.height);
		return (int) ((x - _minX)/(_maxX-_minX) * size);
	}
	/**
	 * Compute the y_window_point from the y_model_point
	 * @return window y 
	 */
	private int yWin( double y) {
		double size = Math.min(_size.width, _size.height);
		return (int) (size - (y - _minY)/(_maxY-_minY) * size);
	}
	/** 
	 * Draw an arc the the center of the circle q
	 * With starAngle and arcAngle in radian
	 */
	private void drawArc(Graphics g, double centre_x, double centre_y,
			double radius, double startAngle, double arcAngle) {
		int x = xWin(centre_x - radius);
		int y = yWin(centre_y + radius);
		int width = xWin(centre_x + radius) - x;
		int height = yWin(centre_y - radius) - y;
		
		g.drawArc(x, y, width, height, (int) Math.toDegrees(startAngle),
				(int) Math.toDegrees(arcAngle));
	}

}
