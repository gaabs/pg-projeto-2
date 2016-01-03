package Basicas;


public class Triangulo {
	public Point3D v1,v2,v3;
	
	public Triangulo(Point3D v1,Point3D v2,Point3D v3){
		this.v1=v1;
		this.v2=v2;
		this.v3=v3;
		
	}

	public Point3D getV1() {
		return v1;
	}

	public void setV1(Point3D v1) {
		this.v1 = v1;
	}

	public Point3D getV2() {
		return v2;
	}

	public void setV2(Point3D v2) {
		this.v2 = v2;
	}

	public Point3D getV3() {
		return v3;
	}

	public void setV3(Point3D v3) {
		this.v3 = v3;
	}
	
}
