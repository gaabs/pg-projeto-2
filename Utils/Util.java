package Utils;
import Basicas.Point;
import Basicas.Point;
import Basicas.Triangulo;
import Basicas.Triangulo;


public class Util {

	public static double[][] alfa;

	public static Point[] gramSchmidt(Point v1, Point v2, Point v3){
		Point[] pontos = new Point[8];

		Point temp;
		v2 = v2.subtract(v1.multiply(v2.dotProduct(v1)/v1.dotProduct(v1)));
		temp = v2.multiply(v3.dotProduct(v2)/v2.dotProduct(v2));
		temp = temp.add(v1.multiply(v3.dotProduct(v1)/v1.dotProduct(v1)));
		v3 = v3.subtract(temp); 

		System.out.println("V2': " + v2);
		System.out.println("V3': " + v3);

		pontos[0]=v1;
		pontos[1]=v2;
		pontos[2]=v3;

		v1 = v1.divide(Math.sqrt(v1.dotProduct(v1)));
		v2 = v2.divide(Math.sqrt(v2.dotProduct(v2)));
		v3 = v3.divide(Math.sqrt(v3.dotProduct(v3)));

		pontos[3]=new Point(-1,-1,-1);

		pontos[4]=v1;
		pontos[5]=v2;
		pontos[6]=v3;

		pontos[7]=new Point(-1,-1,-1);

		System.out.println("e1: " + v1);
		System.out.println("e2: " + v2);
		System.out.println("e3: " + v3);

		return pontos;
	}

	public static Point ortogonalizar(Point V, Point N){
		Point V2 = V.subtract(N.multiply(V.dotProduct(N)/N.dotProduct(N)));

		
		return V2;
	}

	public static double[] extract(String s){
		double[] resp = new double[3];
		int e = s.indexOf(" ");
		resp[0] = Double.parseDouble(s.substring(0, e));
		String s2 = s.substring(e+1);
		e = s2.indexOf(" ");
		resp[1] = Double.parseDouble(s2.substring(0, e));
		resp[2] = Double.parseDouble(s2.substring(e+1));

		return resp;
	}

	public static int[] extractInt(String s){
		int[] resp = new int[3];
		int e = s.indexOf(" ");
		resp[0] = Integer.parseInt(s.substring(0, e));
		String s2 = s.substring(e+1);
		e = s2.indexOf(" ");
		resp[1] = Integer.parseInt(s2.substring(0, e));
		resp[2] = Integer.parseInt(s2.substring(e+1));

		return resp;
	}

	public static void setAlfa(Point U, Point V,Point N){
		alfa=new double[3][3];

		alfa[0][0]=U.x;
		alfa[0][1]=U.y;
		alfa[0][2]=U.z;
		alfa[1][0]=V.x;
		alfa[1][1]=V.y;
		alfa[1][2]=V.z;
		alfa[2][0]=N.x;
		alfa[2][1]=N.y;
		alfa[2][2]=N.z;
	}

	public static Point convert(Point C, Point p){

		double[][] m = alfa;

		double[][] m2 = new double [3][1];
		p=p.subtract(C);
		System.out.println("Ponto depois de subtraido por "+C+" fica: "+p.toString());
		m2[0][0]=p.x;
		m2[1][0]=p.y;
		m2[2][0]=p.z;

		m = MatrixUtil.multiplicar(m, m2, 3, 3, 1);

		p.x=m[0][0];
		p.y=m[1][0];
		p.z=m[2][0];

		return p;
	}

	public static Point[][] scanLine(Triangulo t){
		t.ordenarY();
		Point[][] ret = null;
		int minY = (int) Math.round(t.v3.y);
		int maxY = (int) Math.round(t.v1.y);
		int tam = (int)Math.ceil(t.v1.y - t.v3.y +1);
		ret = new Point[tam][2];

		ret=aux(ret,t.v1,t.v2,t.v3);			

		return ret;
	}

	public static Point[][] aux(Point[][] ret, Point L, Point M, Point S){
		double a = (M.y - L.y)/(M.x - L.x);
		double b = M.y - a*M.x;
		
		
		double a2 = (S.y - L.y)/(S.x - L.x);
		double b2 = S.y - a2*S.x;


		int k=0;
		for(double i=M.y;i<=L.y;i++, k++){
			double xTemp = (i-b)/a;
			double xTemp2 = (i-b2)/a2;
			if(xTemp>xTemp2){
				ret[k][0] = new Point(Math.floor(xTemp2), i);				
				ret[k][1] = new Point(Math.ceil(xTemp), i);
			}else{
				ret[k][0] = new Point(Math.floor(xTemp), i);				
				ret[k][1] = new Point(Math.ceil(xTemp2), i);
			}
		}


		a = (M.y - S.y)/(M.x - S.x);
		b = M.y - a*M.x;



		for(double i=S.y;i<=M.y;i++,k++){
			double xTemp = (i-b)/a;
			double xTemp2 = (i-b2)/a2;
			if(xTemp>xTemp2){
				ret[k][0] = new Point(Math.floor(xTemp2), i);				
				ret[k][1] = new Point(Math.ceil(xTemp), i);
			}else{
				ret[k][0] = new Point(Math.floor(xTemp), i);				
				ret[k][1] = new Point(Math.ceil(xTemp2), i);
			}
		}


		return ret;
	}

	public static double area(Point a, Point b, Point c) {
	    double scalar;
	    scalar = a.x*b.y + a.y*c.x + b.x*c.y - b.y*c.x - a.y*b.x - a.x*c.y;
	    if (scalar > 0)
	        scalar /= 2;
	    else scalar /= -2;
	    return scalar;
	}
	 
	public static double[] findBary( Point a, Point b, Point c, Point p) {
		double total, alfa, beta, gama;
	    total = area(a, b, c);
	    alfa = area(p, c, b) / total;
	    beta = area(p, c, a) / total;
	    gama = (double)1 - beta - alfa;
	    double[] ret = {alfa, beta, gama};
	    return ret;
	}
	 
	public static Point findBaryNormal(Triangulo tri2D, Triangulo tri3D, Point p) {
	    Point temp1, vetor;
	    double alfa, beta, gama;
	    double bary[] = findBary(tri2D.v1, tri2D.v2, tri2D.v3, p);
	    alfa = bary[0];
	    beta = bary[1];
	    gama = bary[2];
	    vetor = tri3D.v1.normal.multiply(alfa);
	    temp1 = tri3D.v2.normal.multiply(beta);
	    vetor.add(temp1);
	    temp1 = tri3D.v3.normal.multiply(gama);
	    vetor.add(temp1);
	    vetor=vetor.normalize();
	    
	    return vetor;
	}
	
}
