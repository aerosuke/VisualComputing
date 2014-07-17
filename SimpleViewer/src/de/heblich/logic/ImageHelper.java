package de.heblich.logic;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class ImageHelper {

	public static Image rotateAroundImage(Image input, double angle) {
		int h = input.getHeight(null);
		int w = input.getWidth(null);
		if(h != -1){
			BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

			double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
			int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
			//GraphicsConfiguration gc = getDefaultConfiguration();
			//BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
			Graphics2D g = result.createGraphics();
			//g.translate((neww-w)/2, (newh-h)/2);
			g.rotate(angle, w/2, h/2);
			g.drawImage(input, 0, 0, null);
			g.dispose();
			return result;
		}else{
			return input;
		}
	}
	
	public static BufferedImage transparentImage(BufferedImage input, float alfa){
		int h = input.getHeight(null);
		int w = input.getWidth(null);
		if(h != -1){
			BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					int px = input.getRGB(x, y);
					
					int aA = (px & 0xff000000) >> 24;
					int rA = (px & 0x00ff0000) >> 16;
					int gA = (px & 0x0000ff00) >> 8;
					int bA = (px & 0x000000ff);
					
					int alfaI;
					
					if(aA != 0){
						alfaI = (int)(255 * alfa);
						alfaI = alfaI << 24;
					}else{
						alfaI = 0;
					}
					
					int rgb = alfaI | 
							rA << 16 | 
							gA << 8 |  
							bA;
					result.setRGB(x, y, rgb);
				}
			}
			return result;
		}
		return input;
	}
	
	public static BufferedImage toBufferedImage(Image input){
		if(input instanceof BufferedImage){
			return (BufferedImage)input;
		}else{
			int h = input.getHeight(null);
			int w = input.getWidth(null);
			if(h != -1){
				BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
				result.getGraphics().drawImage(input, 0, 0, null);
				return result;
			}else{
				return new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
			}
		}
	}
}
