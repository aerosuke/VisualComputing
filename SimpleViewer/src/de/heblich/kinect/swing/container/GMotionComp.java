package de.heblich.kinect.swing.container;

import java.awt.geom.Point2D;
import java.util.List;

import de.heblich.kinect.gestures.MotionAbel;

public interface GMotionComp {

	boolean isPointOverThis(Point2D point);
	
	List<MotionAbel> getMotions();
}
