import java.awt.geom.Line2D;

public class Ball {
	private Vector dir;
	private double[] pos;
	private double[] last;
	
	private boolean stop;
	
	private int[] s;
	
	private int[] dims;
	
	private int r;
	
	public Ball(double dir_, int w, int h, int randomness){
		stop = true;
		dir = new Vector(dir_, 0);
		dir.rotate((int) (Math.random() * 90 - 45));
		//dir.rotate(45);
		pos = new double[]{(w/2.0), (h/2.0) - 5};
		s = new int[]{w, h};
		last = new double[] {0, 0};
		dims = new int[]{10, 10};
		r = randomness;
	}
	public Ball(double dir_, int w, int h){
		this(dir_, w, h, 0);
	}
	public void move(){
		if(!stop) {
			last = pos.clone();
			pos[0] += dir.getX();
			pos[1] -= dir.getY();
		}
	}
	public void chDir(int[][] p, boolean wall) {
		int dNow = Integer.MAX_VALUE;
		int[] pp = null;
		for(int[] i: p) {
			if(pp == null || Math.hypot(pos[0] - i[0], pos[1] - i[1]) < dNow) {
				dNow = (int) Math.hypot(pos[0] - i[0], pos[1] - i[1]);
				pp = i;
			}
		}
		Line2D l1 = extend(new Line2D.Double(last[0], last[1], pos[0], pos[1]));
		Line2D l2 = new Line2D.Double(pp[0], pp[1], pp[0] + pp[3], (pp[1] + pp[2]));
		
		double angle = getAngle(l1, l2, wall);
		
		if(!wall) {
			dir.rotate(2 * angle + (int)(Math.random() * 2 * r - r));
		} else {
			dir.rotate(2 * angle);
		}
	}
	public double getAngle(Line2D line1, Line2D line2, boolean wall)
	{
	    double angle1 = Math.atan2((line1.getY2() - line1.getY1()),
	    						   (line1.getX2() - line1.getX1()));
	    double angle2 = Math.atan2((line2.getY2() - line2.getY1()),
	    						   (line2.getX2() - line2.getX1()));
	    //System.out.println(Math.toDegrees(angle1) + " " + Math.toDegrees(angle2));
		
	    
//	    if(wall && (angle1 == 90.0 || angle2 == 90.0)){
//	    	System.out.println("WALL");
//	    }
	    
	    if(angle1 == 0.0){
	    	return Math.toDegrees(angle2);
	    } else if(angle2 == 0.0){
	    	return Math.toDegrees(angle1);
	    }
	    double ret = Math.abs(angle1) - Math.abs(angle2);
	    
	    double tmp = Math.toDegrees(angle1);
	   
	    
	    //System.out.println("Rounded " + Math.round(tmp));
	    
	    if(tmp != 90.0 && tmp != 180.0 && tmp != 0.0){
	    	//System.out.println("Chose angle1");
	    } else {
	    	tmp = Math.toDegrees(angle2);
	    }
	    while(tmp < 0){
	    	tmp += 360;
	    }
	    
	    //System.out.println(tmp);
	    //System.out.println(Math.toDegrees(ret));
	    
		if(tmp >= 0 && tmp < 90){
			//System.out.println("Q1");
		    ret = -Math.abs(ret);
		} else if(tmp > 90 && tmp < 180){
		    //System.out.println("Q2");
		   	ret = Math.abs(ret);
		} else if(tmp > 180 && tmp < 270){
		    //System.out.println("Q3");
		   	ret = -Math.abs(ret);
		} else if(tmp > 270 && tmp < 360){
		    //System.out.println("Q4");
		   	ret = Math.abs(ret);
		}
	    return Math.toDegrees(ret);
	}
	public double getAngle(Line2D l1, Line2D l2){
		return getAngle(l1, l2, false);
	}
	
	public Line2D extend(Line2D l) {
		//System.out.println(last[0] + " " + last[1]);
		double len = Math.sqrt(Math.pow(l.getX1() - l.getX2(), 2) + Math.pow(l.getY1() - l.getY2(), 2));
		//System.out.println(l.getX1() + " " + l.getY1() + " " + l.getX2() + " " + l.getY2() + " " + len);
		double nX = l.getX2() + (l.getX2() - l.getX1()) / len * 10;
		double nY = l.getY2() + (l.getY2() - l.getY1()) / len * 10;
		//System.out.println(nX + " " + nY);
		return new Line2D.Double(l.getX1(), l.getY1(), nX, nY);
	}
	public int bind(){
		if (pos[0] <= 0 ) {
			return -1;
		} else if(pos[0] >= s[0]) {
			return 1;
		} else {
			return 0;
		}
	}
	public void reset(int i, boolean stop) {
		if(i==0) {
			return;
		} else {
			pos = new double[] {s[0]/2, s[1]/2};
		}
		if(i < 0) {
			dir = new Vector(-dir.mag(), 0);
			//System.out.println("Heading " + dir.heading());
		} else {
			dir = new Vector(dir.mag(), 0);
			//System.out.println("Heading " + dir.heading());
		}
		dir.rotate(Math.random() * 90 - 45);
		if(stop) {
			stop();
		}
	}
	public void chMag(double d){
		dir.setMag(dir.mag() + d);
	}
	public void setMag(double d){
		dir.setMag(d);
	}
	public void setRandomness(int r_) {
		r = r_;
	}
	public void reset(int i) {
		reset(i, true);
	}
	public int getX(){
		return (int)(pos[0] - dims[0]/2);
	}
	public int getY(){
		return (int)(pos[1] - dims[1] / 2);
	}
	public int getW(){
		return dims[0];
	}
	public int getH(){
		return dims[1];
	}
	public void start() {
		stop = false;
	}
	public void stop() {
		stop = true;
	}
	public boolean isStopped() {
		return stop;
	}
	public double getDir(){
		double tmp = Math.toDegrees(dir.heading());
		while(tmp < 0){
			tmp += 360;
		}
		return tmp;
	}
}

class Vector{
	private double[] pos;
	
	public Vector(double x, double y){
		pos = new double[]{x, y};
	}
	public double heading() {
		//System.out.println(Math.toDegrees(Math.atan2(pos[1], pos[0])));
		double a = Math.atan2(pos[1], pos[0]);
		return a;
	}
	public void rotate(double angle){
		double mag = mag();
		//System.out.println(mag);
		double newHeading = (heading() + Math.toRadians(angle));
		if(newHeading < 0){
			newHeading += 2 * Math.PI;
		}
		pos[0] = Math.cos(newHeading) * mag;
		pos[1] = Math.sin(newHeading) * mag;
	}
	public void rotate2(double angle){
		double mag = mag();
		//System.out.println(mag);
		//System.out.println("Rotating to " + angle);
		double newHeading = (Math.toRadians(angle));
		pos[0] = Math.cos(newHeading) * mag;
		pos[1] = Math.sin(newHeading) * mag;
	}
	public Vector normalize(){
		return this.mag() == 0? this: this.div(this.mag());
	}
	public Vector div(double n){
		if(n == 0){
			return null;
		}
		pos[0] /= n;
		pos[1] /= n;
		return this;
	}
	public Vector mult(double d){
		pos[0] *= d;
		pos[1] *= d;
		return this;
	}
	public void setMag(double d){
		this.normalize().mult(d);
	}
	public double mag() {
		return Math.sqrt(Math.pow(pos[0], 2) + Math.pow(pos[1], 2));
	}
	public double getX(){
		return pos[0];
	}
	public double getY(){
		return pos[1];
	}
}