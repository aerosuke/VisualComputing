package de.heblich.dummy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.swing.JPanel;

import org.openni.Device;
import org.openni.PixelFormat;
import org.openni.SensorType;
import org.openni.VideoFrameRef;
import org.openni.VideoMode;
import org.openni.VideoStream;
import org.openni.VideoStream.NewFrameListener;

import com.primesense.nite.JointType;
import com.primesense.nite.Point3D;
import com.primesense.nite.PoseType;
import com.primesense.nite.Skeleton;
import com.primesense.nite.SkeletonJoint;
import com.primesense.nite.UserData;
import com.primesense.nite.UserTracker;
import com.primesense.nite.UserTrackerFrameRef;

public class DummyImage extends JPanel{

	private UserTracker ut;
	private UserTrackerFrameRef utfr;
	private VideoStream vs;
	private boolean trakking = false;
	
	public DummyImage(Device d) {
		
		
		ut = UserTracker.create();
			
		//vs = VideoStream.create(d, SensorType.DEPTH);
		
		
		ut.addNewFrameListener(new MyFrameRepainter(this));
		
		utfr = ut.readFrame();
		
		//VideoMode vm = new VideoMode();
		//vm.setResolution(640, 480);
		//vm.setPixelFormat(PixelFormat.GRAY16);
		//vm.setFps(30);
		//vs.setVideoMode(vm);
		//vs.start();
		//vs.setVideoMode(new VideoMode(arg0, arg1, arg2, arg3));
		
	}
	
	@Override
	protected void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D)gr;
		
		g.drawString(""+System.nanoTime(), 4, 500);
		
		if(vs != null){
			VideoFrameRef ref = vs.readFrame();
			BufferedImage bf = convert(ref);
			g.drawImage(bf, 0, 0, null);
		}
		
		if(ut != null){
			utfr = ut.readFrame();
			VideoFrameRef ref =utfr.getDepthFrame();
			BufferedImage bf = convert(ref);
			g.drawImage(bf, 0, 0, null);
			
			if(utfr.getUsers().size() > 0){
				UserData ud = utfr.getUsers().get(0);
				if(!trakking){
					ut.startSkeletonTracking(ud.getId());
					trakking = true;
				}
				
				Skeleton s =  ud.getSkeleton();
				
				
				
				Point3D<Float> center = ud.getCenterOfMass();
				g.setPaint(Color.RED);
				drawCross(g, center);
				g.drawString("center= "+PointToString(center), 4, 530);
				
				g.setPaint(Color.GREEN);
				SkeletonJoint hand_j = s.getJoint(JointType.RIGHT_HAND);
				Point3D<Float> right_handpos = hand_j.getPosition();
				drawCross(g, right_handpos);
				//Point2D p = new Point2D.Float(handpos.getX(), handpos.getY());
				g.drawString("right Hand= "+PointToString(center), 200, 530);
				g.setPaint(Color.BLACK);
				g.drawString(ud.isLost()?"Lost":"Found", 200, 500);
				g.drawString(s.getState().name(), 200, 515);
			}
		}
		
	}

	private void drawCross(Graphics2D g, Point3D<Float> point) {
		Line2D l1 = new Line2D.Float(320+point.getX()-3, 240-point.getY(), 320+point.getX()+3, 240-point.getY());
		Line2D l2 = new Line2D.Float(320+point.getX(), 240-point.getY()-3, 320+point.getX(), 240-point.getY()+3);
		
		g.draw(l1);
		g.draw(l2);
	}
	
	public static String PointToString(Point3D<Float> p){
		return String.format("x:%7.2f y:%7.2f z:%7.2f", p.getX(), p.getY(), p.getZ());
	}
	
	public BufferedImage convert(VideoFrameRef videoStreamRef){
        int width = videoStreamRef.getWidth();
        int height = videoStreamRef.getHeight();
    	BufferedImage mBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	
    	 ByteBuffer frameData = videoStreamRef.getData().order(ByteOrder.LITTLE_ENDIAN);
         
    	 int[] mImagePixels = new int[width * height];
    	 
    	 
    	
         switch (videoStreamRef.getVideoMode().getPixelFormat())
         {
             case DEPTH_1_MM:
             case DEPTH_100_UM:
             case SHIFT_9_2:
             case SHIFT_9_3:
                 //calcHist(frameData);
                 frameData.rewind();
                 int pos = 0;
                 while(frameData.remaining() > 0) {
                     int depth = (int)frameData.getShort() & 0xFFFF;
                     short pixel = (short) depth;
                     mImagePixels[pos] = 0xFF000000 | (pixel << 16) | (pixel << 8);
                     pos++;
                 }
                 break;
             case RGB888:
                 pos = 0;
                 while (frameData.remaining() > 0) {
                     int red = (int)frameData.get() & 0xFF;
                     int green = (int)frameData.get() & 0xFF;
                     int blue = (int)frameData.get() & 0xFF;
                     mImagePixels[pos] = 0xFF000000 | (red << 16) | (green << 8) | blue;
                     pos++;
                 }
                 break;
             default:
                 // don't know how to draw
            	 videoStreamRef.release();
                 //mLastFrame = null;
         }
    	
    	 if (mBufferedImage == null || mBufferedImage.getWidth() != width || mBufferedImage.getHeight() != height) {
             mBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
         }
         
         mBufferedImage.setRGB(0, 0, width, height, mImagePixels, 0, width);

         //int framePosX = (getWidth() - width) / 2;
         //int framePosY = (getHeight() - height) / 2;
         return mBufferedImage;
         //g.drawImage(mBufferedImage, framePosX, framePosY, null);
    }
}
