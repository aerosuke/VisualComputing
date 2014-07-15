package de.heblich.kinect.swing.container;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import de.heblich.kinect.gestures.GestureListener;
import de.heblich.kinect.gestures.MotionLine;
import de.heblich.logic.Element;

public class GTree extends JTree{

	private List<EButton> buttons = new ArrayList<EButton>();
	
	public GTree(Element root) {
		// TODO Auto-generated constructor stub
	super(new TreeNodeProxy(root));
	
	setMinimumSize(new Dimension(300, 300));
	setEditable(false);
	setExpandsSelectedPaths(true); 
    setToggleClickCount(1);
    setEditable(true);
    //Make the cell render buttons with mouse listeners.
    setCellRenderer(new DefaultTreeCellRenderer(){
         
         public Component getTreeCellRendererComponent ( JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus )
         {
        	 TreeNodeProxy tnp = (TreeNodeProxy)value;
        	 EButton b = (EButton)tnp.getObj();
        	 if(tnp.getObj() == null){
        		 tnp.setObj(new EButton(tnp.getEle(), true));
        		 b = (EButton)tnp.getObj();
        		 buttons.add(b);
        		 MotionLine ml = new MotionLine(90);
        		 ml.register(new GestureListener() {
					
					@Override
					public void success(GMotionComp source) {
						System.out.println("Sucsess");
					}
					
					@Override
					public void abort(GMotionComp source) {
						// TODO Auto-generated method stub
						
					}
				});
        		 b.addMotion(ml);
        	 }
        	 
              //b.
              
              /*
              b.addMouseMotionListener(new MouseMotionListener(){

                   public void mouseDragged ( MouseEvent e )
                   {
                        System.out.println("Dragged.");
                   }

                   public void mouseMoved ( MouseEvent e )
                   {
                        System.out.println("Moving: Button: " + b.getBounds());
                        System.out.println("Moving: e point: " + e.getPoint());
                        System.out.println("---------------");
                   }
                   
              });
              b.addMouseListener(new MouseListener(){

                   public void mouseClicked ( MouseEvent e )
                   {
                   }

                   public void mousePressed ( MouseEvent e )
                   {
                        System.out.println("B bounds: " + b.getBounds());
                        System.out.println("e point: " + e.getPoint());
                        System.out.println("---------------");
                   }

                   public void mouseReleased ( MouseEvent e )
                   {
                   }

                   public void mouseEntered ( MouseEvent e )
                   {
                        System.out.println("Entered");
                   }

                   public void mouseExited ( MouseEvent e )
                   {
                        System.out.println("Exited");
                   }
              });
              */
              return b;
         }
         
         
    });
    
	}
	
    public boolean isPointOverThis(Point2D point){
		return getBounds().contains(point);
	}
	
    @Override
    public Component[] getComponents() {
    	return buttons.toArray(new EButton[0]);
    }
}
