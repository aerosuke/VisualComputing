package de.heblich.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.heblich.logic.Element;
import de.heblich.logic.controller.Speaker;
import de.heblich.logic.controller.SpeakerListener;

public class Dummy2 extends JPanel implements SpeakerListener, ActionListener{
	
	private Speaker sp = Speaker.getInstance();
	private File path;
	int aX;
	int bY;
	int r;

	
	class EButton extends JButton{
		
		Object objs;

		public EButton() {
			super();
			// TODO Auto-generated constructor stub
		}

		public EButton(Action a) {
			super(a);
			// TODO Auto-generated constructor stub
		}

		public EButton(Icon icon) {
			super(icon);
			// TODO Auto-generated constructor stub
		}

		public EButton(String text, Icon icon) {
			super(text, icon);
			// TODO Auto-generated constructor stub
		}

		public EButton(String text) {
			super(text);
			// TODO Auto-generated constructor stub
		}
		
		
	}
	
	public Dummy2(File path) {
		this.path = path;
//		this.setLayout(new BorderLayout());
		this.setLayout(null);
		aX = 400;
		bY = 300;
		int m = Math.min(aX, bY);
        r = 4 * m / 5;
		
		rebuild();
		sp.register(this);
		
		
		
		
		
	}
	
	public void rebuild(){
		removeAll();
		
		
		
// Das ist das Root-Panel		
		JPanel first = new JPanel();
		
		if(sp.getCurrent() != sp.getRoot()){
			EButton b = new EButton(getImage(sp.getCurrent().getParent()));
			b.objs = sp.getCurrent().getParent();
			b.addActionListener(this);
			
			first.add(b);
			
		}
		this.add(first,BorderLayout.NORTH);
		
// Das ist das Panel f�r den Current-Node
		JPanel sec = new JPanel();
		EButton b =  new EButton(getImage(sp.getCurrent()));
		b.objs = sp.getCurrent();
		System.out.println(sp.getCurrent().toString());
		b.addActionListener(this);
		sec.add(b);
//		this.add(sec,BorderLayout.CENTER);
		sec.setBounds(350, 250, 100, 100);
		this.add(sec);
		
		
		int multiply = 0;
		
		List<Element> chs = sp.getCurrent().getChildren();
		
		if(chs != null){
			
			for (int i = 0; i<=chs.size()-1; i++){
				EButton d = new EButton(getImage(chs.get(i)));
				d.objs = chs.get(i);
				d.addActionListener(this);
				
				
				double t = 2 * Math.PI * multiply / chs.size();
				int x = (int) Math.round(aX + r * Math.cos(t));
				int y = (int) Math.round(bY + r * Math.sin(t));
				
				System.out.println(x+","+y);
				int xOffset = x-(85/2);
				int yOffset = y-(64/2);
				
//				System.out.println(xOffset+","+yOffset);

				d.setBounds(xOffset, yOffset, 85, 64);
				this.add(d);
				
				multiply++;
			}
		}
// Das ist das Panel f�r die Kinder
//		JPanel third = new JPanel();
//		List<Element> chs = sp.getCurrent().getChildren();
//		if(chs != null){
//			for (Element element : chs) {
//				EButton d = new EButton(getImage(element));
//				d.objs = element;
//				d.addActionListener(this);
//				third.add(d);
//			}
//		}
//		this.add(third,BorderLayout.SOUTH);
		repaint();
	}
	
	private ImageIcon getImage(Element element){
		File f = new File(path, element.getImage());
		ImageIcon ii = new ImageIcon(f.getAbsolutePath());
		ii.setImage(ii.getImage().getScaledInstance(85, 64, java.awt.Image.SCALE_SMOOTH));
		return ii;
	}
	
	@Override
	public void notifySpeakerChange(Object source, Element e) {
		rebuild();
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof EButton){
			EButton ebth = (EButton)e.getSource();
			sp.select(this, (Element)ebth.objs);
		}
	}
}
