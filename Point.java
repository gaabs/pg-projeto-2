
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
	
	public Point produtoVetorial(Point N){
		double[][] matriz = new double[3][3];
		matriz[0][0]=1;
		matriz[0][1]=1;
		matriz[0][2]=1;
		matriz[1][0]=x;
		matriz[1][1]=y;
		matriz[1][2]=z;
		matriz[2][0]=N.x;
		matriz[2][1]=N.y;
		matriz[2][2]=N.z;		
		double a1=matriz[1][0],	a2=matriz[1][1], a3=matriz[1][2],
				b1=matriz[1][0], b2=matriz[1][1], b3=matriz[2][2];
		
		Point det = new Point(a2*b3 - a3*b2, a3*b1 - a1*b3, a1*b2 - a2*b1);
		return det;
		
	}
	
	public String toString(){
		return String.format("(%f,%f,%f)", x,y,z);
	}

}
