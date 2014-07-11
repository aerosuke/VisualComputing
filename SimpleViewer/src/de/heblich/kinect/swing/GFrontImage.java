package de.heblich.kinect.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.DebugGraphics;
import javax.swing.JPanel;

import com.primesense.nite.JointType;
import com.primesense.nite.Point3D;
import com.primesense.nite.Skeleton;
import com.primesense.nite.SkeletonJoint;
import com.primesense.nite.SkeletonState;
import com.primesense.nite.UserData;
import com.primesense.nite.UserTrackerFrameRef;

import de.heblich.kinect.KinectReader;
import de.heblich.kinect.KinectReaderEvent;

public class GFrontImage extends JPanel implements KinectReaderEvent{

	BufferedImage bf;
	public float scaler = 0.5f;
	
	public GFrontImage() {
		bf = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bf, 0,0,getWidth(), getHeight(),null);
	}
	
	@Override
	public void newFrame(UserTrackerFrameRef userTrackerRef, KinectReader kinectReader) {
		renderImage(userTrackerRef, kinectReader);
		repaint();
	}
	
	private void renderImage(UserTrackerFrameRef userTrackerFrameRef, KinectReader kinectReader){
		Graphics2D g = (Graphics2D)bf.getGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, bf.getWidth(), bf.getHeight());
		
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
				SkeletonJoint neckJoint = skeleton.getJoint(JointType.NECK);
				SkeletonJoint headJoint = skeleton.getJoint(JointType.HEAD);
				
				SkeletonJoint leftElBowJoint = skeleton.getJoint(JointType.LEFT_ELBOW);
				SkeletonJoint leftFoot = skeleton.getJoint(JointType.LEFT_FOOT);
				SkeletonJoint leftHand = skeleton.getJoint(JointType.LEFT_HAND);
				SkeletonJoint leftHip = skeleton.getJoint(JointType.LEFT_HIP);
				SkeletonJoint leftKnee = skeleton.getJoint(JointType.LEFT_KNEE);
				SkeletonJoint leftShoulder = skeleton.getJoint(JointType.LEFT_SHOULDER);
				
				SkeletonJoint torso = skeleton.getJoint(JointType.TORSO);
				SkeletonJoint rightElBowJoint = skeleton.getJoint(JointType.RIGHT_ELBOW);
				SkeletonJoint rightFoot = skeleton.getJoint(JointType.RIGHT_FOOT);
				SkeletonJoint rightHand = skeleton.getJoint(JointType.RIGHT_HAND);
				SkeletonJoint rightHip = skeleton.getJoint(JointType.RIGHT_HIP);
				SkeletonJoint rightKnee = skeleton.getJoint(JointType.RIGHT_KNEE);
				SkeletonJoint rightShoulder = skeleton.getJoint(JointType.RIGHT_SHOULDER);
				
				
				g.setPaint(Color.WHITE);
				drawCross(g, center);
				g.drawString(""+ud.getId(), 320+center.getX()*scaler, 240-center.getY()*scaler);
				
				g.setPaint(Color.GREEN.darker());
				if(headJoint.getPositionConfidence() > 0.2f && neckJoint.getPositionConfidence() > 0.2f){
					drawLine(g, headJoint.getPosition(), neckJoint.getPosition());
				}
				if(leftHand.getPositionConfidence() > 0.2f && leftElBowJoint.getPositionConfidence() > 0.2f){
					drawLine(g, leftHand.getPosition(), leftElBowJoint.getPosition());
				}
				if(leftShoulder.getPositionConfidence() > 0.2f && leftElBowJoint.getPositionConfidence() > 0.2f){
					drawLine(g, leftShoulder.getPosition(), leftElBowJoint.getPosition());
				}
				if(leftShoulder.getPositionConfidence() > 0.2f && neckJoint.getPositionConfidence() > 0.2f){
					drawLine(g, leftShoulder.getPosition(), neckJoint.getPosition());
				}
				if(rightShoulder.getPositionConfidence() > 0.2f && neckJoint.getPositionConfidence() > 0.2f){
					drawLine(g, rightShoulder.getPosition(), neckJoint.getPosition());
				}
				if(rightShoulder.getPositionConfidence() > 0.2f && rightElBowJoint.getPositionConfidence() > 0.2f){
					drawLine(g, rightShoulder.getPosition(), rightElBowJoint.getPosition());
				}
				if(rightHand.getPositionConfidence() > 0.2f && rightElBowJoint.getPositionConfidence() > 0.2f){
					drawLine(g, rightHand.getPosition(), rightElBowJoint.getPosition());
				}
				if(rightShoulder.getPositionConfidence() > 0.2f && rightHip.getPositionConfidence() > 0.2f){
					drawLine(g, rightShoulder.getPosition(), rightHip.getPosition());
				}
				if(leftShoulder.getPositionConfidence() > 0.2f && leftHip.getPositionConfidence() > 0.2f){
					drawLine(g, leftShoulder.getPosition(), leftHip.getPosition());
				}
				if(rightHip.getPositionConfidence() > 0.2f && leftHip.getPositionConfidence() > 0.2f){
					drawLine(g, rightHip.getPosition(), leftHip.getPosition());
				}
				if(rightHip.getPositionConfidence() > 0.2f && rightKnee.getPositionConfidence() > 0.2f){
					drawLine(g, rightHip.getPosition(), rightKnee.getPosition());
				}
				if(leftKnee.getPositionConfidence() > 0.2f && leftHip.getPositionConfidence() > 0.2f){
					drawLine(g, leftKnee.getPosition(), leftHip.getPosition());
				}
				if(leftKnee.getPositionConfidence() > 0.2f && leftFoot.getPositionConfidence() > 0.2f){
					drawLine(g, leftKnee.getPosition(), leftFoot.getPosition());
				}
				if(rightFoot.getPositionConfidence() > 0.2f && rightKnee.getPositionConfidence() > 0.2f){
					drawLine(g, rightFoot.getPosition(), rightKnee.getPosition());
				}
				
				
				
				g.setPaint(Color.GREEN.brighter());
				if(headJoint.getPositionConfidence() > 0.2f){
					drawCross(g, headJoint.getPosition());
				}
				if(neckJoint.getPositionConfidence() > 0.2f){
					drawCross(g, neckJoint.getPosition());
				}
				if(leftHand.getPositionConfidence() > 0.2f){
					drawCross(g, leftHand.getPosition());
				}
				if(leftElBowJoint.getPositionConfidence() > 0.2f){
					drawCross(g, leftElBowJoint.getPosition());
				}
				if(leftShoulder.getPositionConfidence() > 0.2f){
					drawCross(g, leftShoulder.getPosition());
				}
				if(rightShoulder.getPositionConfidence() > 0.2f){
					drawCross(g, rightShoulder.getPosition());
				}
				if(rightElBowJoint.getPositionConfidence() > 0.2f){
					drawCross(g, rightElBowJoint.getPosition());
				}
				if(rightHand.getPositionConfidence() > 0.2f){
					drawCross(g, rightHand.getPosition());
				}
				if(rightHip.getPositionConfidence() > 0.2f){
					drawCross(g, rightHip.getPosition());
				}
				if(leftHip.getPositionConfidence() > 0.2f){
					drawCross(g, leftHip.getPosition());
				}
				if(rightKnee.getPositionConfidence() > 0.2f){
					drawCross(g, rightKnee.getPosition());
				}
				if(leftKnee.getPositionConfidence() > 0.2f){
					drawCross(g, leftKnee.getPosition());
				}
				if(rightFoot.getPositionConfidence() > 0.2f){
					drawCross(g, rightFoot.getPosition());
				}
				if(leftFoot.getPositionConfidence() > 0.2f){
					drawCross(g, leftFoot.getPosition());
				}
				//System.out.println(center.getZ());
				scaler = center.getZ()/ 6000;
				
			}else{
				g.setPaint(Color.WHITE);
				Point3D<Float> center = ud.getCenterOfMass();
				g.drawString(skeleton.getState().name() +" ("+ud.getId()+";"+userNr+")", 320+center.getZ(), 240-center.getY());
				if(skeleton.getState() == SkeletonState.NONE)
					kinectReader.stopTracking(ud.getId());
				kinectReader.startTracking(ud.getId());
				//System.out.println(center.getZ());
				
			}
		}else{
			g.setPaint(Color.WHITE);
			g.drawString("Nothing found", 300, 240);
		}
		
		bf.flush();
	}

	private void drawLine(Graphics2D g, Point3D<Float> p1, Point3D<Float> p2) {
		Line2D l = new Line2D.Float(320+p1.getX()*scaler, 240-p1.getY()*scaler, 320+p2.getX()*scaler, 240-p2.getY()*scaler);
		g.draw(l);
	}
	
	private void drawCross(Graphics2D g, Point3D<Float> point) {
		Line2D l1 = new Line2D.Float(320+point.getX()*scaler-3, 240-point.getY()*scaler, 320+point.getX()*scaler+3, 240-point.getY()*scaler);
		Line2D l2 = new Line2D.Float(320+point.getX()*scaler, 240-point.getY()*scaler-3, 320+point.getX()*scaler, 240-point.getY()*scaler+3);
		
		g.draw(l1);
		g.draw(l2);
	}
}
