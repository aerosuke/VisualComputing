package de.heblich.kinect.swing.container;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import de.heblich.Conf;
import de.heblich.logic.Element;

public class EButton extends GButton{

	private Element element;

	public EButton() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EButton(Element e){
		this(e, false);
	}
	
	public EButton(Element e, boolean id){
		element = e;
		ShowIcon();
		if(id){
			reSizeImage(32,32);
			setText(e.getId());    
			setVerticalTextPosition(SwingConstants.BOTTOM);
		    setHorizontalTextPosition(SwingConstants.CENTER);
		    
		}
		setToolTipText(e.getId()+" "+e.getName());
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
	
	public void ShowIcon(){
		super.setIcon(getImage());
	}
	
	public ImageIcon getImage(){
		File f = new File(Conf.PATH, element.getImage());
		//System.out.println("Image at "+f.getAbsolutePath());
		ImageIcon ii = new ImageIcon(f.getAbsolutePath());
		ii.setImage(ii.getImage().getScaledInstance(85, 64, java.awt.Image.SCALE_SMOOTH));
		return ii;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
	
	public void reSizeImage(int wigth, int hight){
		Icon i = super.getIcon();
		BufferedImage bf = new BufferedImage(i.getIconWidth(), i.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		i.paintIcon(null, bf.getGraphics(),0,0);
		bf.flush();
		
		BufferedImage bf2 = new BufferedImage(wigth, hight, BufferedImage.TYPE_4BYTE_ABGR);
		bf2.getGraphics().drawImage(bf, 0, 0, bf2.getWidth(), bf2.getHeight(), null);
		bf.flush();
		
		setIcon(new ImageIcon(bf2));
	}
	
	@Override
	public String toString() {
		return "EButton ("+element.getId()+" "+element.getName()+" "+element.getInstance()+")";
	}
}
