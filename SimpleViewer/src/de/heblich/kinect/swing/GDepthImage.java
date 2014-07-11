package de.heblich.kinect.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.openni.VideoFrameRef;
import org.openni.VideoStream;

import com.primesense.nite.UserTrackerFrameRef;

import de.heblich.kinect.KinectReader;
import de.heblich.kinect.KinectReaderEvent;

public class GDepthImage extends JPanel implements KinectReaderEvent{

	private BufferedImage bf;
	private ArrayList<Float> mHistogram = new ArrayList<>();
	
	public GDepthImage() {
		setPreferredSize(new Dimension(267, 200));
		bf = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(bf, 0,0,getWidth(), getHeight(),null);
	}
	
	@Override
	public void newFrame(UserTrackerFrameRef userTrackerRef, KinectReader kinectReader) {
		if(convert(userTrackerRef.getDepthFrame())){
			repaint();
		}else{
			//Ignore Frame
		}
	}
	
	public boolean convert(VideoFrameRef videoStreamRef){
        int width = videoStreamRef.getWidth();
        int height = videoStreamRef.getHeight();
    	bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	
        
        ByteBuffer frameData = videoStreamRef.getData().order(ByteOrder.LITTLE_ENDIAN);
        
        // make sure we have enough room
        int[] mImagePixels = new int[width * height];
        
        switch (videoStreamRef.getVideoMode().getPixelFormat())
        {
            case DEPTH_1_MM:
            case DEPTH_100_UM:
            case SHIFT_9_2:
            case SHIFT_9_3:
                calcHist(frameData);
                frameData.rewind();
                int pos = 0;
                while(frameData.remaining() > 0) {
                    int depth = (int)frameData.getShort() & 0xFFFF;
                    if(mHistogram.size() <= 0){
                    	return false;
                    }
                    short pixel = mHistogram.get(depth).shortValue();
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
                videoStreamRef = null;
        }
        
        bf.setRGB(0, 0, width, height, mImagePixels, 0, width);
        return true;
    }
	
	private void calcHist(ByteBuffer depthBuffer) {
        // make sure we have enough room
        /*if (mHistogram == null || mHistogram.length < mVideoStream.getMaxPixelValue()) {
            mHistogram = new float[mVideoStream.getMaxPixelValue()];
        }
		
        
        
        for (int i = 0; i < mHistogram.length; ++i)
            mHistogram[i] = 0;
         */
		// reset
		
		mHistogram.clear();
        int points = 0;
        while (depthBuffer.remaining() > 0) {
            int depth = depthBuffer.getShort() & 0xFFFF;
            if (depth != 0) {
            	if(mHistogram.size() < depth){
            		for (int i = mHistogram.size()-1; i < depth; i++){
            			mHistogram.add(new Float(0f));
            		}
            	}
            	try{mHistogram.set(depth, mHistogram.get(depth)+1);}catch(IndexOutOfBoundsException e){}
                //mHistogram[depth]++;
                points++;
            }
        }

        for (int i = 1; i < mHistogram.size(); i++) {
            mHistogram.set(i, mHistogram.get(i) + mHistogram.get(i - 1));
        }

        if (points > 0) {
            for (int i = 1; i < mHistogram.size(); i++) {
                mHistogram.set(i, new Float((int) (256 * (1.0f - (mHistogram.get(i) / (float) points)))));
            }
        }
    }
}
