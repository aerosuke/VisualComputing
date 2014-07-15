package de.heblich.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import de.heblich.kinect.swing.container.GKinectContainer;
import de.heblich.kinect.swing.container.GMotionManagerLabel;
import de.heblich.kinect.swing.container.GTree;
import de.heblich.logic.Element;

public class MyPanel extends GKinectContainer {

	public MyPanel(JFrame frame, Element root) {
		super(frame);
		setLayout(new BorderLayout());
		add(new Dummy2(), BorderLayout.CENTER);
		add(new JScrollPane(new GTree(root)), BorderLayout.WEST);
		add(new GMotionManagerLabel(), BorderLayout.SOUTH);
	}

}
