package de.heblich.logic;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.swing.tree.TreeNode;

public class TreeElement extends Element implements TreeNode {

	@Override
	public TreeNode getChildAt(int childIndex) {
		return (TreeElement) children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return (children == null)?0:children.size();
	}

	@Override
	public TreeNode getParent() {
		return (TreeElement) super.getParrent();
	}

	@Override
	public int getIndex(TreeNode node) {
		return (children == null)?-1:children.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return getChildCount() == 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration children() {
		return Collections.enumeration(children);
	}
	
	@Override
	protected Object clone() {
		TreeElement e = new TreeElement();
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
