package Utils;

import gui.guiPhong;
import Basicas.Point3D;
import Basicas.Point2D;

public class ProjecaoPontos extends MatrixUtil{
	
	public static Point2D projetar2D(Point3D p, double d, double  Hx,double  Hy){
		
		double x = (d/Hx)*(p.x/p.z);
		double y = (d/Hy)*(p.y/p.z);
		
		Point2D p2 = new Point2D(x,y);
		
		
		return p2;
	}
	
	public static Point2D map2Screen(Point2D p, double d, double hx, double hy){
		Point2D p2  = null;
		if(p.x<=d/hx&&p.x>=-d/hx&&p.y<=d/hy&&p.y>=-d/hy){
			p2 = new Point2D(0,0);
			p2.x = ((p.x+1)/2)*guiPhong.ResX;
			p2.y = ((1-p.y)/2) *guiPhong.ResY;
		}
		
		return p2;
	}
	
}
