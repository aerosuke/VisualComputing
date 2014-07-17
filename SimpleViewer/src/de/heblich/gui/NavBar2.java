package de.heblich.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import de.heblich.kinect.gestures.GestureListener;
import de.heblich.kinect.gestures.MotionLine;
import de.heblich.kinect.swing.container.EButton;
import de.heblich.kinect.swing.container.GMotionComp;
import de.heblich.logic.Element;
import de.heblich.logic.controller.Speaker;
import de.heblich.logic.controller.SpeakerListener;

public class NavBar2 extends JPanel implements SpeakerListener, ActionListener, GestureListener{

	private Speaker sp;
	
	public NavBar2() {
		sp = Speaker.getInstance();
		sp.register(this);
		createButtons();
		setMinimumSize(new Dimension(85, 90));
		setPreferredSize(new Dimension(85, 90));
		
	}
	
	void createButtons(){
		removeAll();
		List<Element> e = sp.getParents();
		System.out.println("NavBar2.createButtons()");
		for (Element element : e) {
			EButton butt = new EButton(element);
			butt.addActionListener(this);
			MotionLine ml1 = new MotionLine(0);
			ml1.register(this);
			butt.addMotion(ml1);
			add(butt);
			
		}
		EButton buf = new EButton(sp.getCurrent());
		buf.addActionListener(this);
		MotionLine ml1 = new MotionLine(0);
		ml1.register(this);
		buf.addMotion(ml1);
		add(buf);
		repaint();
	}
	
	@Override
	public void notifySpeakerChange(Object source, Element e) {
		createButtons();
	}

	@Override
	public void success(GMotionComp source) {
		if(source instanceof EButton){
			EButton ebth = (EButton)source;
			sp.select(this, (Element)ebth.getElement());
		}
	}

	@Override
	public void abort(GMotionComp source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() instanceof EButton){
			success((GMotionComp)e.getSource());
		}
	}
	
	
}
