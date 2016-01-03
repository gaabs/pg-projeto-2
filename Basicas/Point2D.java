package Basicas;

public class Point2D {
	public double x,y;

	public Point2D(){}

	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D copy(){
		Point2D p = new Point2D(x,y);
		return p;
	}

	public Point2D add(Point2D p2){
		double x,y,z;
		Point2D p;
		
		x = this.x + p2.x;
		y = this.y + p2.y;
		
		p = new Point2D(x, y);
		return p;
	}

	public Point2D subtract(Point2D p2){
		return this.add(p2.multiply(-1));
	}

	public Point2D multiply(double k){
		double x,y;
		Point2D p;
		
		x = this.x * k;
		y = this.y * k;
				
		p = new Point2D(x, y);
		return p;
	}
	
	public Point2D divide(double k){
		return this.multiply(1/k);
	}
	
	public double dotProduct(Point2D p2){
		double ret = x*p2.x + y*p2.y;
		
		return ret;
	}
	
	public String toString(){
		return String.format("(%f||%f)", x,y);
	}

}
