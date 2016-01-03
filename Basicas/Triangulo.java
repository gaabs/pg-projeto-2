package Basicas;


public class Triangulo {
	public Point v1,v2,v3;
	
	public Triangulo(Point v1,Point v2,Point v3){
		this.v1=v1;
		this.v2=v2;
		this.v3=v3;
		
	}

	public Point getV1() {
		return v1;
	}

	public void setV1(Point v1) {
		this.v1 = v1;
	}

	public Point getV2() {
		return v2;
	}

	public void setV2(Point v2) {
		this.v2 = v2;
	}

	public Point getV3() {
		return v3;
	}

	public void setV3(Point v3) {
		this.v3 = v3;
	}
	
}
