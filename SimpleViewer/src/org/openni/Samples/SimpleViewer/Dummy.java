package org.openni.Samples.SimpleViewer;

import java.io.File;

public class Dummy {

	public static void main(String[] args) {
		String s = System.getProperty("java.library.path");
		String[] ss = s.split(";");
		for (int i = 0; i < ss.length; i++) {
			File f = new File(ss[i]);
			File[] fs = f.listFiles();
			System.out.println(f.getAbsolutePath());
			for (int j = 0; j < fs.length; j++) {
				System.out.println(fs[j].getName());
			}
		}
		
	}
}
