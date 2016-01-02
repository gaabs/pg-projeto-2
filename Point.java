
public class Point {
	double x,y,z;

	public Point(){}

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point copy(){
		Point p = new Point(x,y,z);
		return p;
	}

	public Point add(Point p2){
		double x,y,z;
		Point p;
		
		x = this.x + p2.x;
		y = this.y + p2.y;
		z = this.z + p2.z;
		
		p = new Point(x, y, z);
		return p;
	}

	public Point subtract(Point p2){
		return this.add(p2.multiply(-1));
	}

	public Point multiply(double k){
		double x,y,z;
		Point p;
		
		x = this.x * k;
		y = this.y * k;
		z = this.z * k;
		
		p = new Point(x, y, z);
		return p;
	}
	
	public Point divide(double k){
		return this.multiply(1/k);
	}
	
	public double dotProduct(Point p2){
		double ret = x*p2.x + y*p2.y + z*p2.z;
		
		return ret;
	}
	
	public String toString(){
		return String.format("(%f,%f,%f)", x,y,z);
	}

}
