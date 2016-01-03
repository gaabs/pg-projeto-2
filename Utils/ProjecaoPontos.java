package Utils;

import Basicas.Point;
import Basicas.Point2D;

public class ProjecaoPontos extends MatrixUtil{
	
	public static Point2D projetar2D(Point p, double d, double  Hx,double  Hy){
		
		double x = (d/Hx)*(p.x/p.z);
		double y = (d/Hy)*(p.y/p.z);
		
		Point2D p2 = new Point2D(x,y);
		
		
		return p2;
	}
	

/*
	Para mapear para a tela do computador 
	(onde o centro do plano esta no topo esquerdo da tela)
	Xs = (Xd+1)/2 * (ResX - 1)
	Ys = (1-Yd)/2 * (ResY - 1)"
	 * */

	public static Point2D map2Screen(Point2D p){
		Point2D p2  =p;
		
		
		return p2;
	}
	
}
