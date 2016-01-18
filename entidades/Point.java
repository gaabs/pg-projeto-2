package entidades;

public class Point {
	public double x,y,z;
	public int indice;
	public Point normal;
	public boolean is3D;
	public Point color;
	public Point(){}

	// Construtor 3D
	public Point(double x, double y, double z, Point normal, Point color) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.normal=normal;
		this.is3D = true;
		this.color = color;
	}
	
	public Point(double x, double y, double z) {
		this(x,y,z,null,null);
	}
	
	// Construtor 2D
	public Point(double x, double y) {
		this(x,y,0,null,null);
		this.is3D = false;
	}

	public Point copy(){
		Point p = new Point(x,y,z,normal,color);
		p.indice = this.indice;
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
		Point p, normal, color;

		x = this.x + p2.x;
		y = this.y + p2.y;
		z = this.z + p2.z;
		
		if(this.normal==null){
			normal=p2.normal;
		}else if(p2.normal==null){
			normal=this.normal;
		}else{
			normal = this.normal.add(p2.normal);
		}
		
		if(this.color==null){
			color=p2.color;
		}else if(p2.color==null){
			color=this.color;
		}else{
			color = this.color.add(p2.color);
		}
		
		//if (color != null) color.trucateColor();

		p = new Point(x, y, z, normal,color);
		return p;
	}

	public Point subtract(Point p2){
		return this.add(p2.multiply(-1));
	}

	public Point multiply(double k){
		double x,y,z;
		Point p,normal;

		x = this.x * k;
		y = this.y * k;
		z = this.z * k;

		if(this.normal==null){
			normal=null;
		}else{
			normal = this.normal.multiply(k);
		}

		p = new Point(x, y, z, normal, this.color);
		p.indice = this.indice;
		return p;
	}

	public Point divide(double k){
		return this.multiply(1/k);
	}
	
	public Point multiplyWithColor(double k) {
		if (this.color == null) this.color = getColor();
		Point p = multiply(k);
		
		p.color = p.color.multiply(k);
		p.trucateColor();
		return p;
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

	public Point getColor(){
		Point p = this;
		p.normal = p.normal.normalize();
		Iluminacao.Od=Iluminacao.Od.normalize();
		Point L = p.subtract(Iluminacao.Pl).normalize();
		Point VdoPonto = p.subtract(Camera.C).normalize();
		Point Id = Iluminacao.Il.multiply(Math.abs(L.dotProduct(p.normal))*Iluminacao.kd).kronecker(Iluminacao.Od);						
		Point R = p.normal.multiply(2).multiply(p.normal.dotProduct(L)).subtract(L).normalize();
		Point Ie = Iluminacao.Il.multiply(Math.abs(R.dotProduct(VdoPonto))*Iluminacao.ks);
		// Ka??
		Point I = Iluminacao.Ia.add(Id).add(Ie);
		
		I.trucateColor();
		
		return I;
	}
	
	public void trucateColor(){
		Point I = this;
		
		if(I.x>255)	I.x=255;
		if(I.y>255)	I.y=255;
		if(I.z>255)	I.z=255;
	}
	


}
