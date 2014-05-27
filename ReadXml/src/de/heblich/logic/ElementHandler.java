package de.heblich.logic;

import java.lang.reflect.Method;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ElementHandler<E extends Element> extends DefaultHandler{

	private E prototyp;
	
	private E root;
	private E current;
	
	private String tag;
	private String lastKey;
	
	
	
	public ElementHandler(E e) {
		prototyp = e;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("N")){
			E n = (E) prototyp.clone();
			if(root == null)
				root = n;
			if(current != null)
				current.AddChildren(n);
			current = n;
		}else{
			tag = qName;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(tag != null){
			String content =  new String(ch, start, length);
			if(tag.equalsIgnoreCase("key") && !content.trim().equals("")){
				lastKey = content;
			}else if(tag.equalsIgnoreCase("Value")){
				if(lastKey != null){
					try {
						Method m = current.getClass().getMethod("set"+lastKey.substring(0,1).toUpperCase()+lastKey.substring(1), String.class);
						m.invoke(current, content);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void endElement(String uri, String localName, String qName)	throws SAXException {
		if(qName.equalsIgnoreCase("N")){
			current = (E) current.getParrent();
		}else if(qName.equalsIgnoreCase("Value")){
			lastKey = null;
		}
	}

	public Element getRoot() {
		return root;
	}
	
	
}
