package de.heblich.logic.controller;

import java.util.ArrayList;
import java.util.List;

import de.heblich.logic.Element;

public class Speaker {

	protected Element root; 
	protected Element current;
	protected List<SpeakerListener> listener = new ArrayList<SpeakerListener>();
	
	private enum ME{
		INSTANCE;
		public Speaker me = new Speaker();
	}
	
	public static Speaker getInstance(){
		return ME.INSTANCE.me;
	}
	
	private Speaker(){
	}
	
	public void SetRoot(Element root){
		this.root = root;
		current = root;
	}
	
	public void remove(SpeakerListener listener){
		this.listener.remove(listener);
	}
	
	public void register(SpeakerListener listener){
		if(!this.listener.contains(listener)){
			this.listener.add(listener);
		}
	}
	
	private void notifyListeners(Object source){
		for (int i = 0; i < listener.size(); i++) {
			listener.get(i).notifySpeakerChange(source, current);
		}
	}
	
	public void select(Object source, Element select){
		current = select;
		notifyListeners(source);
	}
	
	public List<Element> getParents(){
		List<Element> back = new ArrayList<Element>();
		Element currentWork = current.getParent();
		while(currentWork != null){
			back.add(0, currentWork);
			currentWork = currentWork.getParent();
		}
		return back;
	}

	public Element getRoot() {
		return root;
	}

	public Element getCurrent() {
		return current;
	}
	
	
}
