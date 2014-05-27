package de.heblich.logic;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ReadElements {

	public static TreeElement readElements(File f) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			ElementHandler h = new ElementHandler(new TreeElement());
			saxParser.parse(f, h);
			return (TreeElement)h.getRoot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}
