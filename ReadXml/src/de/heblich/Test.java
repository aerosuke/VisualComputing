package de.heblich;

import java.io.File;

import javax.swing.SwingUtilities;

import de.heblich.gui.TreeWindow;
import de.heblich.logic.ReadElements;
import de.heblich.logic.TreeElement;

public class Test {

	public static void main(String[] args) {
		
		String top = Test.class.getResource("../../.").getFile();
		String path = top + "../res";
		final File pathf = new File(path);
		File f = new File(pathf, "E-Bike_v4.xml");
		System.out.println(f.getAbsolutePath() + " "+f.exists());
		
		final TreeElement root = ReadElements.readElements(f);
		
		System.out.println(root);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TreeWindow tw = new TreeWindow(root, pathf);
			}
		});
	}

}
