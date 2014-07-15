package de.heblich;

import java.io.File;
import java.util.List;

import de.heblich.logic.Element;
import de.heblich.logic.ReadElements;

public class PrintTree {

	public static void main(String[] args) {
		String top = Conf.class.getResource("../../.").getFile();
		String path = top + "../res";
		final File pathf = new File(path);
		File f = new File(pathf, "E-Bike_v4.xml");
		System.out.println(f.getAbsolutePath() + " "+f.exists());
		
		final Element root = ReadElements.readElements(f);
		print(root, "");
	}
	
	private static void print(Element e, String schift){
		//System.out.println(schift + " "+e.getId()+" "+e.getName());
		System.out.println(schift + " "+e.toString());
		List<Element> cs = e.getChildren();
		if(cs != null && cs.size() > 0){
			for (Element ele : cs) {
				print(ele, schift + "  ");
			}
		}
	}
}
