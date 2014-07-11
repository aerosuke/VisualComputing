package de.heblich.kinect.gestures;

import java.awt.geom.Point2D;
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
}