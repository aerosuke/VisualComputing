package de.heblich.kinect.gestures;

import de.heblich.kinect.swing.container.GMotionComp;

public interface GestureListener {

	void success(GMotionComp source);
	
	void abort(GMotionComp source);
}
