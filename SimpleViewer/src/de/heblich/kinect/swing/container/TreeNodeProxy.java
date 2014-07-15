package de.heblich.kinect.swing.container;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import de.heblich.logic.Element;

public class TreeNodeProxy implements TreeNode{

	Element ele;
	Object obj;

	public TreeNodeProxy(Element ele) {
		super();
		this.ele = ele;
	}

	public Element getEle() {
		return ele;
	}

	public void setEle(Element ele) {
		this.ele = ele;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return new TreeNodeProxy(ele.getChildren().get(childIndex));
	}

	@Override
	public int getChildCount() {
		if(ele.getChildren() == null)
			return 0;
		else
			return ele.getChildren().size();
	}

	@Override
	public TreeNode getParent() {
		return new TreeNodeProxy(ele.getParent());
	}

	@Override
	public int getIndex(TreeNode node) {
		if(node instanceof TreeNodeProxy){
			TreeNodeProxy tnp = (TreeNodeProxy)node;
			return ele.getChildren().indexOf(tnp.getEle());
		}else{
			return -1;
		}
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return getChildCount() < 1;
	}

	@Override
	public Enumeration children() {
		Enumeration<TreeNode> enumer = new Enumeration<TreeNode>() {

			int counter = 0;

			@Override
			public TreeNode nextElement() {
				if(counter >= ele.getChildren().size()){
					return null;
				}else{
					TreeNodeProxy tnp = new TreeNodeProxy(ele.getChildren().get(counter));
					counter++;
					return tnp;
				}

			}

			@Override
			public boolean hasMoreElements() {
				return counter+1 <= ele.getChildren().size();
			}
		};
		return enumer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ele == null) ? 0 : ele.hashCode());
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
		TreeNodeProxy other = (TreeNodeProxy) obj;
		if (ele == null) {
			if (other.ele != null)
				return false;
		} else if (!ele.equals(other.ele))
			return false;
		return true;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}


	
}
