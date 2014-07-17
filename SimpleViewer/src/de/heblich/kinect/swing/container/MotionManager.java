package de.heblich.kinect.swing.container;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import de.heblich.kinect.gestures.MotionAbel;

public class MotionManager extends Observable {

	protected List<MotionAbel> currentMotions = new ArrayList<MotionAbel>();
	
	
	public static enum Instace{
		ME(new MotionManager());
		
		public MotionManager me;
		
		private Instace(MotionManager me) {
			this.me = me;
		}
		
		
	}
	
	private MotionManager() {
		currentMotions = Collections.synchronizedList(currentMotions);
	}
	
	public void update(Point2D.Double hand){
		//Look for completet Gestures;
		for (int i = 0; i < currentMotions.size(); i++) {
			if(!currentMotions.get(i).isEnabled()){
				currentMotions.get(i).destroy();
				currentMotions.remove(i);
				i--;
			}
		}
		
		//Test if Hand is over Component
		for (int i = 0; i < currentMotions.size(); i++) {
			if(!currentMotions.get(i).isDyingBreath()){
				if(!currentMotions.get(i).getSource().isPointOverThis(hand)){
					currentMotions.get(i).End();
				}
			}
		}
		
		//Update Gestures
		for (int i = 0; i < currentMotions.size(); i++) {
			currentMotions.get(i).update(hand);
		}
		setChanged();
		notifyObservers(this);
		//System.out.println("current anz: "+currentMotions.size());
	}
	
	public void AddMotion(GMotionComp comp){
		List<MotionAbel> ms = comp.getMotions();
		if(ms != null){
			for (MotionAbel motionAbel : ms) {
				if(motionAbel != null){
					if(!currentMotions.contains(motionAbel)){
						currentMotions.add(motionAbel);
					}
					motionAbel.Resume();
				}
			}
		}
	}
}
