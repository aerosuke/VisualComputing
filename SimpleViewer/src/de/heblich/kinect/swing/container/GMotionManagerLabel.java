package de.heblich.kinect.swing.container;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import de.heblich.kinect.gestures.MotionAbel;

public class GMotionManagerLabel extends JLabel implements Observer{

	
	public GMotionManagerLabel() {
		setText("CurrentMotions: 0");
		Font f = getFont();
		setFont(new Font(f.getName(), Font.PLAIN, 24));
		MotionManager.Instace.ME.me.addObserver(this);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		MotionManager mm = (MotionManager)o;
		StringBuffer sb = new StringBuffer();
		sb.append("CurrentMotions: ");
		sb.append(mm.currentMotions.size());
		if(mm.currentMotions.size() > 0){
			for (MotionAbel motionAbel : mm.currentMotions) {
				sb.append(motionAbel.getSource().toString()+", ");
			}
		}
		setText(sb.toString());
		repaint();
	}

	
}
