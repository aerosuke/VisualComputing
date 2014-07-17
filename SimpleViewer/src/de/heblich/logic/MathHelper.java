package de.heblich.logic;

import java.awt.geom.Point2D;

public class MathHelper {

	public static double length(Point2D p){
		double x = p.getX();
		double y = p.getY();
		return Math.sqrt((x * x) + (y * y));
	}
	
	public static Point2D.Double sub(Point2D.Double a, Point2D.Double b){
		Point2D.Double back = new Point2D.Double();
		back.x = a.x - b.x;
		back.y = a.y - b.y;
		return back;
	}
	
	public static Point2D.Double add(Point2D.Double a, Point2D.Double b){
		Point2D.Double back = new Point2D.Double();
		back.x = a.x + b.x;
		back.y = a.y + b.y;
		return back;
	}
	
	public static double dirVectorToAngle(Point2D.Double targetDir){
		Point2D.Double RIGHT = new Point2D.Double(1, 0);
		double dot = dotProduct(RIGHT ,targetDir);
		double winkel = dot / length(targetDir);
		if(winkel == 0)
			return 0;
		winkel = Math.atan2(targetDir.x, targetDir.y);
		return winkel;
	}
	
	public static double dotProduct(Point2D.Double a, Point2D.Double b){
		return a.getX()*b.getX() + a.getY()*b.getY();
	}
	
	public static Point2D.Double normalise(Point2D.Double a) {
		double length;
		double x = a.x;
		double y = a.y;
		length = Math.sqrt((x * x) + (y * y));

		if (length == 0.0f)
			length = 1.0f;

		Point2D.Double back = new Point2D.Double((float)(x / length),(float)(y / length));
		return back;
	}
	
	public static Point2D.Double midpoint(Point2D.Double a, Point2D.Double b){
		Point2D.Double dir = sub(a, b);
		double length = length(dir);
		dir = normalise(dir);
		return add(a, dir);
	}
}
