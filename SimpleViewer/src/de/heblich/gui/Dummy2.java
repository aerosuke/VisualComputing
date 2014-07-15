package de.heblich.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;

import javax.print.attribute.standard.Chromaticity;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.heblich.kinect.gestures.GestureListener;
import de.heblich.kinect.gestures.MotionLine;
import de.heblich.kinect.swing.container.EButton;
import de.heblich.kinect.swing.container.GButton;
import de.heblich.kinect.swing.container.GKinectContainer;
import de.heblich.kinect.swing.container.GMotionComp;
import de.heblich.logic.Element;
import de.heblich.logic.MathHelper;
import de.heblich.logic.controller.Speaker;
import de.heblich.logic.controller.SpeakerListener;

public class Dummy2 extends JPanel implements SpeakerListener, ActionListener, GestureListener{
	
	private Speaker sp = Speaker.getInstance();
	int aX;
	int bY;
	int r;
	
	
	
	@Override
	public Dimension getPreferredSize() {
		return getSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getSize();
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getSize();
	}

	
	
	public Dummy2() {
//		this.setLayout(new BorderLayout());
		this.setLayout(null);
		setSize(800, 600);
		aX = getWidth() / 2;
		bY = getHeight() / 2;
		int m = Math.min(aX, bY);
        r = 4 * m / 5;
		
		rebuild();
		sp.register(this);
		
	}

	private GButton createChildBth(Element e, int x, int y){
		EButton d = new EButton(e);
		d.addActionListener(this);
		int xOffset = x - 42;
		int yOffset = y - 32;
		d.setBounds(xOffset, yOffset, 85, 64);
		
		Point2D.Double pointButton = new Point2D.Double(x,y);
		
		Point2D.Double panelPoint = new Point2D.Double(getWidth() / 2, getHeight() / 2);
		
		Point2D.Double dir = MathHelper.sub(panelPoint, pointButton);
		
		double dirR = Math.toDegrees(MathHelper.dirVectorToAngle(dir));
		System.out.println(dir + " is "+dirR);
		MotionLine line = new MotionLine(dirR);
		
		line.register(this);
		d.addMotion(line);
		
		return d;
	}
	
	public void rebuild(){
		removeAll();
		
		
		
// Das ist das Root-Panel		
		JPanel first = new JPanel();
		
		if(sp.getCurrent() != sp.getRoot()){
			EButton b = new EButton(sp.getCurrent().getParent());
			b.addActionListener(this);
			
			first.add(b);
			
		}
		this.add(first,BorderLayout.NORTH);
		
// Das ist das Panel für den Current-Node
		JPanel sec = new JPanel();
		EButton b =  new EButton(sp.getCurrent());
		System.out.println(sp.getCurrent().toString());
		b.addActionListener(this);
		sec.add(b);
//		this.add(sec,BorderLayout.CENTER);
		int rx = getWidth()/2 -50;
		int ry = getHeight()/2 -50;
		sec.setBounds(rx, ry, 100, 100);
		this.add(sec);
		
		
		int multiply = 0;
		
		List<Element> chs = sp.getCurrent().getChildren();
		
		if(chs != null){
			
			for (int i = 0; i<=chs.size()-1; i++){
				double t = 2 * Math.PI * multiply / chs.size();
				int x = (int) Math.round(aX + r * Math.cos(t));
				int y = (int) Math.round(bY + r * Math.sin(t));
				
				System.out.println(x+","+y);
				GButton d = createChildBth(chs.get(i), x, y);
				this.add(d);
				multiply++;
			}
		}
// Das ist das Panel für die Kinder
//		JPanel third = new JPanel();
//		List<Element> chs = sp.getCurrent().getChildren();
//		if(chs != null){
//			for (Element element : chs) {
//				EButton d = new EButton(getImage(element));
//				d.objs = element;
//				d.addActionListener(this);
//				third.add(d);
//			}
//		}
//		this.add(third,BorderLayout.SOUTH);
		repaint();
	}
	
	@Override
	public void notifySpeakerChange(Object source, Element e) {
		rebuild();
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof GMotionComp){
			success((GMotionComp)e.getSource());
		}
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
		
	}
	
	
}
