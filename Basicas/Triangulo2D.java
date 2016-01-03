package Basicas;


public class Triangulo2D {
	public Point2D v1,v2,v3;
	public int indice;

	public Triangulo2D(Point2D v1,Point2D v2,Point2D v3, int indice){
		this.v1=v1;
		this.v2=v2;
		this.v3=v3;
		this.indice=indice;

	}

	public void ordenarX(){
		Point2D temp=null;
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
		Point2D temp=null;
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
}