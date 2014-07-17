package de.heblich.kinect.gestures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;

import de.heblich.logic.ImageHelper;
import de.heblich.logic.MathHelper;

public class MotionLine extends MotionBase {

	private long lastTestTime;
	private long testInterval = 50 * 1000000;
	private long extraTime = 1000 * 1000000;
	private long extraTestTime;
	public static final int POINT_COUNT = 5;
	public static final double FUZZY_DISTACE = 50;

	private List<Point2D.Double> points = Collections.synchronizedList(new ArrayList<Point2D.Double>());

	private List<Point2D.Double> calcTemp = null;

	private boolean dyingBreath = true;
	private boolean hadSucces = false;

	//0 = runter
	//90 = rechts
	//180 = oben
	// 270 = links
	private double rotation = 45; 

	private double minDistace = 100;

	public MotionLine(double rotation) {
		this.rotation = rotation;
		Resume();
	}

	public MotionLine(double rotation, double minDistace) {
		this(rotation);
		this.minDistace = minDistace;
	}

	public MotionLine(double rotation, double minDistace, long extraTime) {
		this(rotation, minDistace);
		this.extraTime = extraTime;
	}



	@Override
	public void update(Point2D.Double hand){		
		if(lastTestTime + testInterval < System.nanoTime() && isEnabled()){
			lastTestTime = System.nanoTime();
			if(points.size() > POINT_COUNT){
				if(points.size() > 0)
					points.remove(0);
			}
			calcTemp = null;
			points.add(hand);

			if(points.size() > 1){
				if(match() && isEnabled()){
					//System.out.println("successAll");
					successAll();
					hadSucces = true;
					EndImiditly();
				}
			}
		}
	}

	public List<Point2D.Double> getRawPoints(){
		return points;
	}

	@Override
	public List<Point2D.Double> getPoints(){
		if(calcTemp == null){
			List<Point2D.Double> calc = new ArrayList<Point2D.Double>();
			Point2D.Double[] points = this.points.toArray(new Point2D.Double[0]);
			Point2D.Double last = null;
			for (int i = 0; i < points.length; i++) {
				if(i == 0){
					calc.add(points[0]);
				}else if(i == 1){
					last = points[1];
				}else{
					calc.add(MathHelper.midpoint(last, points[i]));
					last = points[i];
				}

			}
			calcTemp = calc;
		}
		return calcTemp;

	}

	public double length(){
		List<Point2D.Double> ps = getPoints();
		if(ps.size() > 2){
			return ps.get(0).distance(ps.get(ps.size()-1));
		}else{
			return 0;
		}

	}

	public Point2D.Double perfectPoint(Point2D.Double start, double length){
		double x = Math.sin(Math.toRadians(rotation));
		double y = Math.cos(Math.toRadians(rotation));
		x *= length;
		y *= length;
		Point2D.Double back = new Point2D.Double(x,y);
		back = MathHelper.add(back, start);
		return back;
	}

	public Point2D.Double perfectPoint(double length){
		if(points.size() > 1){
			return perfectPoint(points.get(0), length);
		}else{
			return new Point2D.Double();
		}
	}

	public Point2D.Double perfectEndPoint(){
		if(points.size() > 1){
			return perfectPoint(length());
		}else{
			return new Point2D.Double();
		}
	}

	@Override
	public boolean match() {
		if(points.size() > 1 && minDistace <= length()){
			List<Point2D.Double> ps = getPoints();
			for (int i = 1; i < ps.size(); i++) {
				double distaceToOrigen = ps.get(0).distance(ps.get(i));
				Point2D.Double perfect = perfectPoint(distaceToOrigen);
				if(ps.get(i).distance(perfect) > FUZZY_DISTACE){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void destroy(){
		if(!hadSucces){
			EndImiditly();
			aboutAll();
		}
	}

	@Override
	public void Clear() {
		points.clear();
	}

	@Override
	public void End() {
		extraTestTime = System.nanoTime()  + extraTime;
		dyingBreath = true;
	}

	public void EndImiditly() {
		extraTestTime = System.nanoTime()- 20;
		dyingBreath = true;
	}

	@Override
	public void Resume() {
		extraTestTime = Long.MAX_VALUE;
		dyingBreath = false;
		hadSucces = false;
	}

	@Override
	public boolean isDyingBreath() {
		return dyingBreath;
	}

	@Override
	public boolean isEnabled(){
		return !(dyingBreath && extraTestTime < System.nanoTime());
	}

	public boolean Empty() {
		if(!dyingBreath && points.size() == 0)
			return true;
		return false;
	}

	@Override
	public MotionAbel clone(){
		MotionLine newLine = new MotionLine(rotation, minDistace);	
		newLine.listeners = listeners;
		return newLine;
	}

	@Override
	public void debug(Graphics2D graphics) {

		super.debug(graphics);

		List<Point2D.Double> ps = getPoints();
		Point2D.Double perfect;
		for (int i = 1; i < ps.size(); i++) {
			double distaceToOrigen = ps.get(0).distance(ps.get(i));
			perfect = perfectPoint(distaceToOrigen);
			graphics.setPaint(Color.BLUE);
			graphics.draw(new Line2D.Double(ps.get(0), perfect));
			graphics.setPaint(Color.BLACK);
			graphics.draw(new Line2D.Double(ps.get(i-1), ps.get(i)));
		}
	}

	@Override
	public void printMotion(Graphics2D graphics, BufferedImage hand, int wigth, int hight) {

		if(getSource() instanceof JComponent){
			JComponent c = (JComponent)getSource();
			Rectangle r =  c.getBounds();
			Point2D.Double button = new Point2D.Double(r.getCenterX(), r.getCenterY());
			if(c.getParent() != null){
				r =  c.getParent().getBounds();
				button.setLocation(button.getX() + r.x, button.getY()+ r.y);
			}

			BufferedImage a1 = ImageHelper.transparentImage(hand, 0.5f);
			BufferedImage a2 = ImageHelper.transparentImage(hand, 0.4f);
			BufferedImage a3 = ImageHelper.transparentImage(hand, 0.5f);
			BufferedImage a4 = ImageHelper.transparentImage(hand, 0.2f);
			BufferedImage a5 = ImageHelper.transparentImage(hand, 0.1f);

			button.setLocation(button.getX() - wigth /2, button.getY() - hight /2);

			double step = minDistace / 5f;

			graphics.drawImage(a1, (int)button.getX(), (int)button.getY(), wigth, hight, null);
			button =  perfectPoint(button, step*2);

			graphics.drawImage(a2, (int)button.getX(), (int)button.getY(), wigth, hight, null);
			button =  perfectPoint(button, step*3);

			graphics.drawImage(a3, (int)button.getX(), (int)button.getY(), wigth, hight, null);
			button =  perfectPoint(button, step*4);

			graphics.drawImage(a4, (int)button.getX(), (int)button.getY(), wigth, hight, null);
			button =  perfectPoint(button, step*5);

			graphics.drawImage(a5, (int)button.getX(), (int)button.getY(), wigth, hight, null);
		}
	}
}
