package de.heblich.kinect.swing.container;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.management.MXBean;
import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.AbstractBorder;

import res.Resource;

import com.primesense.nite.JointType;
import com.primesense.nite.Point3D;
import com.primesense.nite.Quaternion;
import com.primesense.nite.Skeleton;
import com.primesense.nite.SkeletonJoint;
import com.primesense.nite.SkeletonState;
import com.primesense.nite.UserData;
import com.primesense.nite.UserTrackerFrameRef;

import de.heblich.Conf;
import de.heblich.kinect.KinectReader;
import de.heblich.kinect.KinectReaderEvent;
import de.heblich.kinect.gestures.GestureAdapter;
import de.heblich.kinect.gestures.GestureListener;
import de.heblich.kinect.gestures.MotionAbel;
import de.heblich.kinect.gestures.MotionLine;
import de.heblich.logic.ImageHelper;
import de.heblich.logic.MathHelper;

public class GKinectContainer extends JPanel implements KinectReaderEvent {

	private JFrame frame;
	private MYGlassPane pane = new MYGlassPane();
	private Rectangle myRectangle;

	private Point2D.Double[] lastPoints;
	private int midpointCounter = 0;

	public GKinectContainer(JFrame frame) {
		lastPoints = new Point2D.Double[5];
		this.frame = frame;
		frame.setGlassPane(pane);
		pane.setVisible(true);

	}

	private Point2D.Double getMitpoint(){
		double x = 0;
		double y = 0;
		for (int i = 0; i < lastPoints.length; i++) {
			if(lastPoints[i] == null)
				return null;
			x += lastPoints[i].getX();
			y += lastPoints[i].getY();
		}
		x = x / lastPoints.length;
		y = y / lastPoints.length;
		return new Point2D.Double(x, y);
	}

	private void AddToMitpoint(Point2D.Double p){
		lastPoints[midpointCounter] = p;
		midpointCounter++;
		midpointCounter = midpointCounter % lastPoints.length;
	}

	private void ClearMitpoint(){
		for (int i = 0; i < lastPoints.length; i++) {
			lastPoints[i] = null;
		}
		midpointCounter = 0;
	}


	public void finishAdd(){
		myRectangle = this.getBounds();
		System.out.println(myRectangle);
	}

	@Override
	public void newFrame(UserTrackerFrameRef userTrackerRef, KinectReader reader) {
		calcPoint(userTrackerRef, reader);
		pane.repaint();
		pane.process();
		//System.out.println("frame");
	}

	private double getYaw(Quaternion q){
		double yaw = Math.atan2(2.0*(q.getY()*q.getZ() + q.getW()*q.getX()), q.getW()*q.getX() - q.getX()*q.getX() - q.getY()*q.getY() + q.getZ()*q.getZ());
		return yaw;
		//var pitch = asin(-2.0*(q.x*q.z - q.w*q.y));
		//var roll = atan2(2.0*(q.x*q.y + q.w*q.z), q.w*q.w + q.x*q.x - q.y*q.y - q.z*q.z);
	}

	private void calcPoint(UserTrackerFrameRef userTrackerFrameRef, KinectReader kinectReader){
		if(userTrackerFrameRef.getUsers().size() > 0){
			short userNr = 0;
			UserData ud = null;
			Skeleton skeleton = null;
			while(userNr < userTrackerFrameRef.getUsers().size() && (skeleton == null || skeleton.getState() == SkeletonState.NONE)){
				ud = userTrackerFrameRef.getUsers().get(userNr);
				skeleton =  ud.getSkeleton();
				userNr++;
			}

			if(skeleton.getState() == SkeletonState.TRACKED){

				Point3D<Float> center = ud.getCenterOfMass();

				SkeletonJoint rightHand = skeleton.getJoint(JointType.RIGHT_HAND);
				SkeletonJoint rightElbow = skeleton.getJoint(JointType.RIGHT_ELBOW);

				if(rightHand.getPositionConfidence() > 0.2f){
					Point3D<Float> p = rightHand.getPosition();
					//Quaternion elbowRot = rightElbow.getOrientation();		
					if(rightElbow.getPositionConfidence() > 0.2f){
						Point3D<Float> rightElbowPoint = rightElbow.getPosition();
						Point2D.Double dir = new Point2D.Double(p.getX() - rightElbowPoint.getX(), p.getY() - rightElbowPoint.getY());
						dir = MathHelper.normalise(dir);
						double dirAngle =  Math.atan2(dir.getY(), dir.getX());
						pane.rotation =  -Math.PI - Math.PI/2 -dirAngle;
					}

					if(pane.handyPoint == null){
						ClearMitpoint();
						pane.handyPoint = new Point2D.Double();
						//						gestures.Clear();
					}else{
						/*
						if(elbowRot != null){
							//test.setText(elbowRot.getX() +" "+elbowRot.getY()+" "+elbowRot.getZ()+ " "+elbowRot.getW());
							pane.rotation = Math.PI -getYaw(elbowRot) ;
						}else{
							pane.rotation = 0;
						}*/
						AddToMitpoint(new Point2D.Double(p.getX(), -p.getY()));
						Point2D.Double newPoint = getMitpoint();

						if(newPoint != null){
							pane.handyPoint =  newPoint; 
							//if(pane.handyPoint == null)
							//	pane.handyPoint =  newPoint; 
						}
					}
				}else{
					pane.handyPoint = null;
				}


			}else{
				if(skeleton.getState() == SkeletonState.NONE)
					kinectReader.stopTracking(ud.getId());
				kinectReader.startTracking(ud.getId());
				ClearMitpoint();
				pane.handyPoint = null;
			}
		}
	}

	class MYGlassPane extends JComponent{

		private static final long serialVersionUID = 1L;

		public Point2D.Double handyPoint;
		public MotionManager manager = MotionManager.Instace.ME.me;
		private Image hand;
		public double rotation;

		public MYGlassPane() {
			super();
			hand = Resource.getImage("hand.png");
		}


		public List<GMotionComp> GetMotionComponents(Point2D.Double hand, JComponent currentParent){
			List<GMotionComp> back = new ArrayList<GMotionComp>();

			JComponent parent = currentParent;
			for (Component child : parent.getComponents()) {
				if(child instanceof GMotionComp && ((GMotionComp)child).isPointOverThis(hand)){
					back.add(((GMotionComp)child));
					if(child instanceof GButton){
						((GButton)child).setSelected(true);
					}
				}else{
					if(child instanceof GButton){
						((GButton)child).setSelected(false);
					}
					else if(child instanceof JScrollPane){
						JScrollPane sp = (JScrollPane)child;
						JViewport viewport = sp.getViewport();
						Point2D.Double nP = new Point2D.Double(hand.x - child.getBounds().x, hand.y - child.getBounds().y);
						Point dummy = new Point((int)nP.getX(), (int)nP.getY());
						dummy = viewport.toViewCoordinates(dummy);
						nP = new Point2D.Double(dummy.x, dummy.y);
						back.addAll(GetMotionComponents(nP, (JComponent)viewport.getView()));

					}else if(child instanceof JComponent){
						Point2D.Double nP = new Point2D.Double(hand.x - child.getBounds().x, hand.y - child.getBounds().y);
						back.addAll(GetMotionComponents(nP, (JComponent)child));
					}
				}
			}
			return back;
		}

		public GMotionComp[] GetMotionComponents(Point2D.Double hand){
			List<GMotionComp> back = GetMotionComponents(hand, GKinectContainer.this);
			return back.toArray(new GMotionComp[0]);
		}

		private Point2D.Double FromScreenToCompSpace(double x, double y){
			x = x- myRectangle.getX();
			y = y- myRectangle.getY();
			return new Point2D.Double(x, y);
		}

		public void process(){
			if (handyPoint != null) {
				double x = handyPoint.getX() + myRectangle.getCenterX();
				double y = handyPoint.getY() + myRectangle.getCenterY();
				Point2D.Double hand = FromScreenToCompSpace(x, y);
				GMotionComp[] comps = GetMotionComponents(hand);
				for (GMotionComp gMotionComp : comps) {
					manager.AddMotion(gMotionComp);
				}
				manager.update(hand);
			}
		}

		protected void paintComponent(Graphics old_g) {
			Graphics2D g = (Graphics2D)old_g;
			//g.clipRect((int)myRectangle.getX(), (int)myRectangle.getY(), (int)myRectangle.getWidth(), (int)myRectangle.getHeight());
			//double midX = myRectangle.getCenterX()


			if (handyPoint != null) {
				//g.drawImage(hand, (int)handyPoint.getX(), (int)handyPoint.getY(), null);
				double x = handyPoint.getX() + myRectangle.getCenterX();
				double y = handyPoint.getY() + myRectangle.getCenterY();

				Image rotHand = hand;
				if(rotation != 0){
					rotHand = ImageHelper.rotateAroundImage(hand, rotation);
					//rotHand = ImageHelper.transparentImage(ImageHelper.toBufferedImage(rotHand), 0.5f);
				}
				int imgW = rotHand.getWidth(null);
				int imgH = rotHand.getHeight(null);


				MotionManager motionManager = MotionManager.Instace.ME.me;
				if(Conf.SHOW_GESTURE){
					for (int i = 0; i <  motionManager.currentMotions.size(); i++) {
						motionManager.currentMotions.get(i).printMotion(g, ImageHelper.toBufferedImage(rotHand),imgW*2,imgH*2);
					}
				}
				/*
				g.setPaint(color);
				Ellipse2D e = new Ellipse2D.Double(x -10, y -10, 20, 20);
				g.fill(e);
				 */
				g.drawImage(rotHand, (int)x -imgW/2, (int)y-imgH/2,imgW*2,imgH*2, null);


				if(Conf.DEBUG){
					for (int i = 0; i <  motionManager.currentMotions.size(); i++) {
						motionManager.currentMotions.get(i).debug(g);
					}
				}
				/*
				g.setPaint(color);
				Ellipse2D e = new Ellipse2D.Double(x -10, y -10, 20, 20);
				g.fill(e);
				 */
			}
			/*			
			//drawGesture
			List<Point2D.Double> l = dummy.getPoints();
			if(l != null && l.size() > 0){
				if(l.size() == 1){
					double x = l.get(0).getX() + myRectangle.getCenterX();
					double y = l.get(0).getY() + myRectangle.getCenterY();
					g.setPaint(Color.DARK_GRAY);
					Ellipse2D e = new Ellipse2D.Double(x -5, y -5, 10, 10);
					g.fill(e);
				}else{
					Point2D.Double last = (Point2D.Double)l.get(0).clone();
					last.setLocation(last.x + myRectangle.getCenterX(), last.y + myRectangle.getCenterY());
					for (int i = 1; i < l.size(); i++) {
						Point2D.Double current = (Point2D.Double)l.get(i).clone();
						if(dummy.match()){
							g.setPaint(Color.GREEN);
						}else{
							g.setPaint(Color.DARK_GRAY);	
						}
						current.setLocation(current.x + myRectangle.getCenterX(), current.y + myRectangle.getCenterY());
						Line2D.Double line = new Line2D.Double(last, current);
						g.draw(line);
						last = current;
					}

				}
				Point2D.Double perfect = ((MotionLine)dummy).perfectEndPoint();
				Point2D.Double last = (Point2D.Double)l.get(0).clone();
				last.setLocation(last.x + myRectangle.getCenterX(), last.y + myRectangle.getCenterY());
				perfect.setLocation(perfect.x + myRectangle.getCenterX(), perfect.y + myRectangle.getCenterY());
				g.setPaint(Color.BLUE);
				Line2D.Double line = new Line2D.Double(last, perfect);
				g.draw(line);
			}*/
		}

	}

}
