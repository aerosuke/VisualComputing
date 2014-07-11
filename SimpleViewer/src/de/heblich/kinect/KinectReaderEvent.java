package de.heblich.kinect;

import com.primesense.nite.UserTrackerFrameRef;

public interface KinectReaderEvent {

	public void newFrame(UserTrackerFrameRef userTrackerRef, KinectReader reader);
}
