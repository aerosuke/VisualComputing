package de.heblich.kinect.swing.container;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

import de.heblich.kinect.gestures.MotionAbel;

public class GButton extends JButton implements GMotionComp {

	protected List<MotionAbel> motion;
	
	public GButton() {
		super();
		setSelected(false);
	}

	public GButton(Action a) {
		super(a);
		setSelected(false);
	}

	public GButton(Icon icon) {
		super(icon);
		setSelected(false);
	}

	public GButton(String text, Icon icon) {
		super(text, icon);
		setSelected(false);
	}

	public GButton(String text) {
		super(text);
		setSelected(false);
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
		if(value)
			setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.DARK_GRAY));
		else
			setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.DARK_GRAY));
		getModel().setRollover(value);
		getModel().setSelected(value);
	}
}
