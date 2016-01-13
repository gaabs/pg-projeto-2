package Utils;

import java.awt.Insets;

import gui.guiPhong;
import Basicas.Point3D;
import Basicas.Point2D;
import Basicas.Triangulo3D;

public class ProjecaoPontos extends MatrixUtil{
	
	public static Point2D projetar2D(Point3D p, double d, double  Hx,double  Hy){
		
		double x = (d/Hx)*(p.x/p.z);
		double y = (d/Hy)*(p.y/p.z);
		
		Point2D p2 = new Point2D(x,y);
		
		
		return p2;
	}
	
	public static Point2D map2Screen(Point2D p){
		Point2D p2  = null;
		p2 = new Point2D(0,0);
		p2.x = ((p.x+1)/2)*guiPhong.ResX;
		p2.y = ((1-p.y)/2) *guiPhong.ResY;
		
		return p2;
	}
	
	public static boolean isOnWindow(Point2D p, double d, double hx, double hy){
		//fiz uma mudança de acordo com romero sobre a janela de visualização
		return (p.x<=hx && p.x>=-hx && p.y<=hy && p.y>=-hy);
	}
	
	
	
	
}
