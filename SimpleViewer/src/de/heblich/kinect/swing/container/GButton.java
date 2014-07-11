package de.heblich.kinect.swing.container;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import de.heblich.kinect.gestures.MotionAbel;

public class GButton extends JButton implements GMotionComp {

	protected List<MotionAbel> motion;
	
	public GButton() {
		super();
	}

	public GButton(Action a) {
		super(a);
	}

	public GButton(Icon icon) {
		super(icon);
	}

	public GButton(String text, Icon icon) {
		super(text, icon);
	}

	public GButton(String text) {
		super(text);
	}

	@Override
	public List<MotionAbel> getMotions() {
		return motion;
	}

	public void addMotion(MotionAbel motion) {
		if(this.motion == null)
			this.motion = new ArrayList<MotionAbel>();
		MotionAbel copy = motion.clone();
		this.motion.add(copy);
		copy.setSource(this);
	}
	
	@Override
	public boolean isPointOverThis(Point2D point){
		return getBounds().contains(point);
	}
	
	public void setSelected(boolean value){
		getModel().setRollover(value);
		getModel().setSelected(value);
	}
}
