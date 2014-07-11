package de.heblich.kinect.gestures;

import java.util.ArrayList;
import java.util.List;

import de.heblich.kinect.swing.container.GMotionComp;

public abstract class MotionBase implements MotionAbel {

	protected List<GestureListener> listeners = new ArrayList<GestureListener>();
	protected GMotionComp source;
	
	@Override
	public void Clear() {}

	public void setSource(GMotionComp source){
		this.source = source;
	}
	
	@Override
	public GMotionComp getSource(){
		return source;
	}

	@Override
	public void register(GestureListener listener) {
		listeners.add(listener);
	}

	@Override
	public void remove(GestureListener listener) {
		listeners.remove(listener);
	}
	
	public void destroy(){
		aboutAll();
	}
	
	protected void aboutAll(){
		for (GestureListener l : listeners) {
			l.abort(source);
		}
	}
	
	protected void successAll(){
		for (GestureListener l : listeners) {
			l.success(source);
		}
	}

	@Override
	public abstract MotionAbel clone();
}
