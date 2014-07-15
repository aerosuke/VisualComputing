package de.heblich.kinect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.primesense.nite.UserTracker;
import com.primesense.nite.UserTrackerFrameRef;

import de.heblich.dummy.MyFrameRepainter;

public class KinectReader implements com.primesense.nite.UserTracker.NewFrameListener{

	private List<KinectReaderEvent> listener;
	private UserTracker ut;
	private Set<Short> trackUser = new HashSet<>();

	public KinectReader() {
		listener = Collections.synchronizedList(new ArrayList<KinectReaderEvent>());
		ut = UserTracker.create();
		ut.addNewFrameListener(this);
	}

	public void stopTracking(short userId){
		if(trackUser.contains(userId)){
			ut.stopSkeletonTracking(userId);
			trackUser.remove(userId);
		}
	}

	public void startTracking(short userId){
		if(!trackUser.contains(userId)){
			try{
				//System.out.println("SmoothingFactor: "+ut.getSkeletonSmoothingFactor());
				ut.setSkeletonSmoothingFactor(0.8f);
				System.out.println("Track user: "+userId);
				ut.startSkeletonTracking(userId);
				trackUser.add(userId);
			}catch(UnsupportedOperationException e){
				e.printStackTrace();
			}
		}
	}

	private void notifyAll(UserTrackerFrameRef userTrackerRef){
		for (int i = 0; i < listener.size(); i++) {
			listener.get(i).newFrame(userTrackerRef, this);
		}
	}

	public void register(KinectReaderEvent event){
		if(!listener.contains(event)){
			listener.add(event);
		}
	}

	public void remove(KinectReaderEvent event){
		if(listener.contains(event)){
			listener.remove(event);
		}
	}

	@Override
	public void onNewFrame(UserTracker userTracker) {
		notifyAll(userTracker.readFrame());
	}

	public void endAll(){
		for(Short ele : trackUser){
			ut.stopSkeletonTracking(ele);
		}
	}
}
