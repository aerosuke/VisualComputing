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
}
