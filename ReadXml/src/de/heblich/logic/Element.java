package de.heblich.logic;

import java.awt.Image;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Element implements Cloneable{

	protected String id;
	protected String name;
	protected String image;
	protected String instance;
	protected Element parent;
	
	protected List<Element> children;
	
	public void AddChildren(Element e){
		if(children == null)
			children = new LinkedList<Element>();
		children.add(e);
		e.parent = this;
	}
	
	public Element getParent() {
		return parent;
	}

	public List<Element> getChildren() {
		return children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public String toString() {
		return "Element [id=" + id + ", name=" + name + ", image=" + image + ", instance=" + 
				instance + ", childrenCount="+((children == null)?"0":children.size())+" "+
				"parent="+((parent == null)?"null":parent.name) + "]";
	}

	@Override
	protected Object clone(){
		Element e = new Element();
		e.id = this.id;
		e.image = this.image;
		e.instance = this.instance;
		e.name = this.name;
		if(children != null){
			e.children = new LinkedList<>();
			Collections.copy(e.children, this.children);
		}
		return e;
	}
	
	
	
}
