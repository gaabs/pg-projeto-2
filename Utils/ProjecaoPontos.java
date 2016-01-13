package Utils;

import java.awt.Insets;

import gui.guiPhong;
import Basicas.Point;
import Basicas.Point;
import Basicas.Triangulo;

public class ProjecaoPontos extends MatrixUtil{
	
	public static Point projetar2D(Point p, double d, double  Hx,double  Hy){
		
		double x = (d/Hx)*(p.x/p.z);
		double y = (d/Hy)*(p.y/p.z);
		
		Point p2 = new Point(x,y);
		
		
		return p2;
	}
	
	public static Point map2Screen(Point p){
		Point p2  = null;
		p2 = new Point(0,0);
		p2.x = ((p.x+1)/2)*guiPhong.ResX;
		p2.y = ((1-p.y)/2) *guiPhong.ResY;
		
		return p2;
	}
	
	public static boolean isOnWindow(Point p, double d, double hx, double hy){
		//fiz uma mudança de acordo com romero sobre a janela de visualização
		return (p.x<=hx && p.x>=-hx && p.y<=hy && p.y>=-hy);
	}
	
	
	
	
}
