package de.heblich;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import de.heblich.gui.Dummy2;
import de.heblich.gui.DummyComp;
import de.heblich.logic.Element;
import de.heblich.logic.ReadElements;
import de.heblich.logic.controller.Speaker;

public class Conf {

	public static File PATH;
	public static boolean DEBUG = false;
	public static boolean SHOW_GESTURE = true;
	
	
	static {
		String top = Conf.class.getResource("../../.").getFile();
		String path = top + "../res";
		PATH = new File(path);
	}
	

}
