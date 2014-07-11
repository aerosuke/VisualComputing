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

public class Test2 {

	public static void main(String[] args) {
		
		String top = Test2.class.getResource("../../.").getFile();
		String path = top + "../res";
//		String path = new String("C://Dokumente und Einstellungen//mmg//workspace//ReadXml//res");
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
				
				

//				f.setLayout(null);
//				Dummy2 dummy = new Dummy2(pathf);
//				dummy.setBounds(5,5,600,400);
//				dummy.setBackground(Color.BLACK);
//				f.add(dummy);
//				f.setPreferredSize(new Dimension(800, 600));
				
				f.add(new Dummy2(f, pathf), BorderLayout.CENTER);
				f.setSize(800, 600);
				
				f.setVisible(true);
//				f.pack();
				
			}
		});
	}

}
