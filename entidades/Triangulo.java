package entidades;


public class Triangulo implements Comparable<Triangulo>{
	public Point v1,v2,v3;
	public int indice;
	public double area;
	public boolean isAreaCalculated;
	
	public Triangulo(Point v1,Point v2,Point v3, int indice){
		this.v1=v1;
		this.v2=v2;
		this.v3=v3;
		this.indice=indice;
		this.isAreaCalculated = false;
	}
	
	public Triangulo(Point v1,Point v2,Point v3){
		this.v1=v1;
		this.v2=v2;
		this.v3=v3;
		this.isAreaCalculated = false;
	}
	
	public double getArea(){
		if (!isAreaCalculated){
		    area = v1.x*v2.y + v1.y*v3.x + v2.x*v3.y - v2.y*v3.x - v1.y*v2.x - v1.x*v3.y;
		    if (area > 0)
		    	area /= 2;
		    else area /= -2;
		    
		    isAreaCalculated = true;
		}
		return area;
	}
	
	public double[] getBaryCoefs(Point p) {
		double total, alfa, beta, gama;
	    total = getArea();
	    alfa = new Triangulo(p, v3, v2).getArea() / total;
	    beta = new Triangulo(p, v3, v1).getArea() / total;
	    gama = (double)1 - beta - alfa;
	    double[] ret = {alfa, beta, gama};
	    return ret;
	}

	public void ordenarX(){
		Point temp=null;
		if(v1.x<v2.x){
			temp=v1;
			v1=v2;
			v2=temp;
		}
		if(v1.x<v3.x){
			temp=v1;
			v1=v3;
			v3=temp;
		}
		if(v2.x<v3.x){
			temp=v2;
			v2=v3;
			v3=temp;
		}
	}

	public void ordenarY(){
		Point temp=null;
		if(v1.y<v2.y){
			temp=v1;
			v1=v2;
			v2=temp;
		}
		if(v1.y<v3.y){
			temp=v1;
			v1=v3;
			v3=temp;
		}
		if(v2.y<v3.y){
			temp=v2;
			v2=v3;
			v3=temp;
		}
	}
	
	public void ordenarZ(){
		Point temp=null;
		if(v1.z<v2.z){
			temp=v1;
			v1=v2;
			v2=temp;
		}
		if(v1.z<v3.z){
			temp=v1;
			v1=v3;
			v3=temp;
		}
		if(v2.z<v3.z){
			temp=v2;
			v2=v3;
			v3=temp;
		}
	}

	public int compareTo(Triangulo t) {
		ordenarZ();
		t.ordenarZ();
		if(v1.z>t.v1.z) return 1;
		if(v1.z<t.v1.z) return -1;
		
		return 0;
	}
}
