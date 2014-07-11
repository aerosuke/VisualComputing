package de.heblich.kinect.swing.container;

import java.awt.Component;
import java.awt.geom.Point2D;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class GTree extends JTree{

	public GTree() {
		// TODO Auto-generated constructor stub
	
	
	
	setExpandsSelectedPaths(true); 
    setToggleClickCount(1);
    setEditable(true);
    //Make the cell render buttons with mouse listeners.
    setCellRenderer(new DefaultTreeCellRenderer(){
         
         public Component getTreeCellRendererComponent ( JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus )
         {
              final GButton b = new GButton(value.toString());
              
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
	
}
