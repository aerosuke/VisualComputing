package de.heblich.kinect.gestures;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import de.heblich.kinect.swing.container.GMotionComp;

public interface MotionAbel {

	void update(Point2D.Double hand);

	List<Point2D.Double> getPoints();

	void Clear();
	
	boolean match();
	
	void register(GestureListener listener);
	
	void remove(GestureListener listener);
	
	void End();
	
	void Resume();
	
	void destroy();
	
	boolean isEnabled();
	
	boolean isDyingBreath();
	
	void setSource(GMotionComp source);
	
	GMotionComp getSource();
	
	MotionAbel clone();
	
	void debug(Graphics2D graphics);
	
	void printMotion(Graphics2D graphics, BufferedImage hand, int wigth, int hight);
}