package util;

import entidades.Point;
import gui.Phong;

public class ProjecaoPontos extends MatrixUtil{
	
	public static Point projetar2D(Point p, double d, double Hx,double  Hy){
		
		double x = (d/Hx)*(p.x/p.z);
		double y = (d/Hy)*(p.y/p.z);
		
		Point p2 = new Point(x,y);
		p2.indice = p.indice;
		
		return p2;
	}
	
	public static Point map2Screen(Point p, int resX, int resY){
		Point p2  = null;
		p2 = new Point(0,0);
		p2.x = ((p.x+1)/2)*resX;
		p2.y = ((1-p.y)/2) *resY;
		p2.indice = p.indice;
		
		return p2;
	}
	
	//metodo inutil VV
	public static boolean isOnWindow(Point p, double d, double hx, double hy){
		//fiz uma mudan�a de acordo com romero sobre a janela de visualiza��o
		return (p.x<=hx && p.x>=-hx && p.y<=hy && p.y>=-hy);
	}
	
	
	
	
}
