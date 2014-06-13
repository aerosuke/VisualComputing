package de.heblich.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.heblich.logic.Element;
import de.heblich.logic.controller.Speaker;
import de.heblich.logic.controller.SpeakerListener;

public class DummyComp extends JPanel implements SpeakerListener{

	private JButton randomSelector;
	private File path;
	
	public DummyComp(File path){
		this.path = path;
		randomSelector = new JButton("Select Random Node");
		randomSelector.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setRandom();		
			}
		});
		rebuild();
		Speaker.getInstance().register(this);
	}
	
	private void rebuild(){
		this.removeAll();
		this.add(randomSelector);
		List<Element> l =  Speaker.getInstance().getParents();
		l.add(Speaker.getInstance().getCurrent());
		for (Element element : l) {
			File f = new File(path, element.getImage());
			ImageIcon ii = new ImageIcon(f.getAbsolutePath());
			ii.setImage(ii.getImage().getScaledInstance(85, 64, java.awt.Image.SCALE_SMOOTH));
			JLabel ls = new JLabel(ii);
			this.add(ls);
		}
		validate();
		repaint();
	}
	
	private void setRandom(){
		Element e = selectRandom();
		Speaker.getInstance().select(this, e);
	}
	
	private Element selectRandom(){
		List<Element> l = Speaker.getInstance().getRoot().getChildren();
		Random r = new Random();
		int i = r.nextInt(l.size());
		Element back = Speaker.getInstance().getRoot();
		while(l != null && i != l.size()){
			back = l.get(i);
			l = back.getChildren();
			if(l != null)
				i = r.nextInt(l.size()+1);
		}
		return back;
		
		
		
	}
	
	@Override
	public void notifySpeakerChange(Object source, Element e) {
		rebuild();	
	}
	
}
