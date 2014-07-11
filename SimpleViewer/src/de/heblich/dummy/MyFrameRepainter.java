package de.heblich.dummy;

import javax.swing.JComponent;

import org.openni.VideoStream;
import org.openni.VideoStream.NewFrameListener;

import com.primesense.nite.HandTracker;
import com.primesense.nite.UserTracker;

public class MyFrameRepainter implements NewFrameListener, com.primesense.nite.HandTracker.NewFrameListener, com.primesense.nite.UserTracker.NewFrameListener{

	private JComponent update;
	
	
	
	public MyFrameRepainter(JComponent update) {
		super();
		this.update = update;
	}

	@Override
	public void onNewFrame(UserTracker arg0) {
		update.repaint();
	}

	@Override
	public void onNewFrame(HandTracker arg0) {
		update.repaint();
	}

	@Override
	public void onFrameReady(VideoStream arg0) {
		update.repaint();
	}
	

}
