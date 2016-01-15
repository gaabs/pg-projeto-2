package entidades;

public class Point {
	public double x,y,z;
	public int indice;
	public Point normal;
	public boolean is3D;
	public Point(){}

	// Construtor 3D
	public Point(double x, double y, double z, Point normal) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.normal=normal;
		this.is3D = true;
	}
	
	public Point(double x, double y, double z) {
		this(x,y,z,null);
	}
	
	// Construtor 2D
	public Point(double x, double y) {
		this(x,y,0,null);
		this.is3D = false;
	}

	public Point copy(){
		Point p = new Point(x,y,z,normal);
		return p;
	}

	public Point normalize(){
		Point Vn=this.divide(this.norma());
		return Vn;
	}

	public double norma(){
		return Math.sqrt(this.dotProduct(this));
	}
	
	public Point add(Point p2){
		double x,y,z;
		Point p,n;

		x = this.x + p2.x;
		y = this.y + p2.y;
		z = this.z + p2.z;
		if(this.normal==null){
			n=p2.normal;
		}else if(p2.normal==null){
			n=this.normal;
		}else{
			n = this.normal.add(p2.normal);
		}

		p = new Point(x, y, z, n);
		return p;
	}

	public Point subtract(Point p2){
		return this.add(p2.multiply(-1));
	}

	public Point multiply(double k){
		double x,y,z;
		Point p,n;

		x = this.x * k;
		y = this.y * k;
		z = this.z * k;

		if(this.normal==null){
			n=null;
		}else{
			n = this.normal.multiply(k);
		}

		p = new Point(x, y, z, n);
		return p;
	}

	public Point divide(double k){
		return this.multiply(1/k);
	}

	// Produto interno ou escalar
	public double dotProduct(Point p2){

		double ret = x*p2.x + y*p2.y + z*p2.z;

		return ret;
	}

	// crossProduct
	public Point produtoVetorial(Point N){

		double a1=x,a2=y,a3=z,b1=N.x, b2=N.y, b3=N.z;
		Point det = new Point(a2*b3 - a3*b2, a3*b1 - a1*b3, a1*b2 - a2*b1);
		return det;	
	}

	public Point kronecker(Point p2){
		p2.x = this.x*p2.x;
		p2.y = this.y*p2.y;
		p2.z = this.z*p2.z;
		return p2;
	}
	
	public String toString(){
		return String.format("(%f||%f||%f)", x,y,z);
	}

}
