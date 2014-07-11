package de.heblich.dummy;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.heblich.kinect.gestures.GestureAdapter;
import de.heblich.kinect.gestures.MotionLine;
import de.heblich.kinect.swing.container.GButton;
import de.heblich.kinect.swing.container.GKinectContainer;
import de.heblich.kinect.swing.container.GMotionComp;

public class TestPanel extends GKinectContainer{

	private JLabel test;
	private JLabel bthLabel;
	
	public TestPanel(JFrame frame) {
		super(frame);
		
		test = new JLabel("bla bla");
		test.setFont(new Font(test.getFont().getName(), Font.PLAIN, 18));
		bthLabel = new JLabel("bla bla");
		bthLabel.setFont(new Font(test.getFont().getName(), Font.PLAIN, 18));
		GButton b = new  GButton("bla asdlja sjdlaj ljsal jlsaj dljasld jlsajd ljasld jasldj lasjd ");
		
		MotionLine mline1 = new MotionLine(0);
		mline1.register(new GestureAdapter(){
			@Override
			public void success(GMotionComp source) {
				bthLabel.setText("mline1 success");
				System.out.println("mline1 "+ source.getMotions().toString() +" success");
			}
			
			@Override
			public void abort(GMotionComp source) {
				bthLabel.setText("mline1 abort");
				System.out.println("mline1 "+ source.getMotions().toString() +" abort");
			}
		});
		b.addMotion(mline1);
		
		MotionLine mline2 = new MotionLine(0);
		mline2.register(new GestureAdapter(){
			@Override
			public void success(GMotionComp source) {
				bthLabel.setText("asdasdas success");
				System.out.println("asdasdas "+ source.getMotions().toString() +" success");
			}
			
			@Override
			public void abort(GMotionComp source) {
				bthLabel.setText("asdasdas abort");
				System.out.println("asdasdas "+ source.getMotions().toString() +" abort");
			}
		});
		b.addMotion(mline1);
		
		GButton b1 = new  GButton("234 2342342 42 342 423 42 34 234234234234 234 23 42 3 4234 234 2 ");
		b1.addMotion(mline2);
		b.setPreferredSize(new Dimension(150, 100));
		b1.setPreferredSize(new Dimension(150, 100));
		this.add(b);
		this.add(b1);
		this.add(test);
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bthLabel.setText("Clicked");				
			}
		});
		
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bthLabel.setText("_______");				
			}
		});
		this.add(bthLabel);
		
	}

	
}
