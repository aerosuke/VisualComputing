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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result
		//		+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result
				+ ((instance == null) ? 0 : instance.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		//if (children == null) {
		//	if (other.children != null)
		//		return false;
		//} else if (!children.equals(other.children))
		//	return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (instance == null) {
			if (other.instance != null)
				return false;
		} else if (!instance.equals(other.instance))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	
	
	
}
