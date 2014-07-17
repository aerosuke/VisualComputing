package de.heblich.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.heblich.kinect.swing.container.EButton;
import de.heblich.logic.Element;
import de.heblich.logic.controller.Speaker;
import de.heblich.logic.controller.SpeakerListener;




public class NavBar extends JPanel implements SpeakerListener,ActionListener{

	
	
	
	
	public ArrayList<Element> elementList;
	private File path;
	private Speaker sp = Speaker.getInstance();
	
	
	public NavBar(){
		//this.path = path;
		elementList = new ArrayList<Element>();
		elementList.add(Speaker.getInstance().getCurrent());
		this.setLayout(null);
		this.setBackground(Color.DARK_GRAY);
		
		setMinimumSize(new Dimension(85, 64));
		setPreferredSize(new Dimension(85, 64));
		
		createBar();
		this.setVisible(true);
		sp.register(this);
	}
	
	public void createBar(){
		
		this.removeAll();
		int multiply = 0;
		ArrayList<Element> l = this.getList();
		for (Element element : l){
			//File f = new File(path, element.getImage());
			//ImageIcon ii = new ImageIcon(f.getAbsolutePath());
			//ii.setImage(ii.getImage().getScaledInstance(85, 64, java.awt.Image.SCALE_SMOOTH));
			EButton childButton = new EButton(element);
			this.add(childButton);
			childButton.setBounds(7+multiply*87, 7, 85, 64);

			
			multiply++;
		}
		revalidate();
	}
	
	public void addToList(Element e){
		if(e!=null){
			if(!this.getList().contains(e)){
				elementList.add(e);
				System.out.println("Element erfolgreich hinzugefügt");
			}else{
				System.out.println("Tut uns leid, das geht nicht");
			}
		}
	}
	
	public ArrayList<Element> getList(){
		return elementList;
	}
	
	
	@Override
	public void notifySpeakerChange(Object source, Element e) {
		createBar();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	




}
