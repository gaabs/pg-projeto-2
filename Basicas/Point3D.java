package Basicas;

public class Point3D {
	public double x,y,z;
	public int indice;
	public Point3D(){}

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D copy(){
		Point3D p = new Point3D(x,y,z);
		return p;
	}

	public Point3D add(Point3D p2){
		double x,y,z;
		Point3D p;
		
		x = this.x + p2.x;
		y = this.y + p2.y;
		z = this.z + p2.z;
		
		p = new Point3D(x, y, z);
		return p;
	}

	public Point3D subtract(Point3D p2){
		return this.add(p2.multiply(-1));
	}

	public Point3D multiply(double k){
		double x,y,z;
		Point3D p;
		
		x = this.x * k;
		y = this.y * k;
		z = this.z * k;
		
		p = new Point3D(x, y, z);
		return p;
	}
	
	public Point3D divide(double k){
		return this.multiply(1/k);
	}
	
	public double dotProduct(Point3D p2){
		double ret = x*p2.x + y*p2.y + z*p2.z;
		
		return ret;
	}
	
	public Point3D produtoVetorial(Point3D N){
		double a1=x,a2=y,a3=z,b1=N.x, b2=N.y, b3=N.z;
		Point3D det = new Point3D(a2*b3 - a3*b2, a3*b1 - a1*b3, a1*b2 - a2*b1);
		return det;
		
	}
	
	public String toString(){
		return String.format("(%f||%f||%f)", x,y,z);
	}

}
