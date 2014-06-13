package de.heblich;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import de.heblich.gui.DummyComp;
import de.heblich.logic.Element;
import de.heblich.logic.ReadElements;
import de.heblich.logic.controller.Speaker;

public class Test {

	public static void main(String[] args) {
		
		String top = Test.class.getResource("../../.").getFile();
		String path = top + "../res";
		final File pathf = new File(path);
		File f = new File(pathf, "E-Bike_v4.xml");
		System.out.println(f.getAbsolutePath() + " "+f.exists());
		
		final Element root = ReadElements.readElements(f);
		Speaker.getInstance().SetRoot(root);
		
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame f = new JFrame("Dummy");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.add(new DummyComp(pathf), BorderLayout.NORTH);
				f.add(new DummyComp(pathf), BorderLayout.SOUTH);
				f.setSize(300, 300);
				f.setVisible(true);
			}
		});
	}

}
