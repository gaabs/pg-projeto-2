package util;
import entidades.Camera;
import entidades.Point;
import entidades.Triangulo;


public class Util {

	public static double[][] alfa;
	final static double EPS = 1/1000000.0;

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

	public static Point convert(Point C, Point p2){
		Point p = p2.copy();
		double[][] m = alfa;

		double[][] m2 = new double [3][1];
		p=p.subtract(C);
		//System.out.println("Ponto depois de subtraido por "+C+" fica: "+p.toString());
		m2[0][0]=p.x;
		m2[1][0]=p.y;
		m2[2][0]=p.z;

		m = multiplicarMatrizes(m, m2);

		p.x=m[0][0];
		p.y=m[1][0];
		p.z=m[2][0];

		return p;
	}

	public static Point[][] scanLine(Triangulo ti){
		Triangulo t = ti.ordenarY();
		Point[][] ret = null;
		int tam = (int)Math.ceil(t.v1.y - t.v3.y +1);
		ret = new Point[tam][2];

		ret=getIntervalos(ret,t.v1,t.v2,t.v3);	
		
		return ret;
	}

	public static Point[][] getIntervalos(Point[][] ret, Point L, Point M, Point S){
		double a = (M.y - L.y)/(M.x - L.x);
		double b = M.y - a*M.x;
		
		double a2 = (S.y - L.y)/(S.x - L.x);
		double b2 = S.y - a2*S.x;
		
		boolean vert1,vert2;
		vert1 = (M.x - L.x) == 0;
		vert2 = (S.x - L.x) == 0;
		double xi,xj;
		
		if (L.y - S.y == 0){
			xi = Math.min(M.x, Math.min(L.x, S.x));
			xj = Math.max(M.x, Math.max(L.x, S.x));
			ret[0][0] = new Point(xi,L.y);
			ret[0][1] = new Point(xj,L.y);
			
			return ret;
		}
		
		int k=0;
		for(double i=M.y;i<=L.y;i++, k++){
			double xTemp = (i-b)/a;
			double xTemp2 = (i-b2)/a2;
			
			if (xTemp>xTemp2){
				xi = Math.ceil(xTemp2);
				xj = Math.floor(xTemp);
			} else if (xTemp < xTemp2){
				xi = Math.ceil(xTemp);
				xj = Math.floor(xTemp2);
			} else{
				xi = Math.round(xTemp);
				xj = Math.round(xTemp2);
			}
			
			if (vert1) xi = Math.round(L.x);
			if (vert2) xj = Math.round(L.x);
			
			ret[k][0] = new Point(xi, i);				
			ret[k][1] = new Point(xj, i);
		}

		a = (M.y - S.y)/(M.x - S.x);
		b = M.y - a*M.x;

		for(double i=S.y;i<=M.y;i++,k++){
			double xTemp = (i-b)/a;
			double xTemp2 = (i-b2)/a2;
			
			if (xTemp>xTemp2){
				xi = Math.ceil(xTemp2);
				xj = Math.floor(xTemp);
			} else if (xTemp < xTemp2){
				xi = Math.ceil(xTemp);
				xj = Math.floor(xTemp2);
			} else{
				xi = Math.round(xTemp);
				xj = Math.round(xTemp2);
			}
			
			if (vert1) xi = Math.round(L.x);
			if (vert2) xj = Math.round(L.x);
			
			ret[k][0] = new Point(xi, i);				
			ret[k][1] = new Point(xj, i);
		}

		return ret;
	}
	//metodo inutil? n ja tem um em MatrixUtil.m
	public static double[][] multiplicarMatrizes(double[][] matriz1, double[][] matriz2){
		double valor = 0;
		//tirei linhas 2... era inutil
		int linhas1,colunas1,colunas2;
		linhas1 = matriz1.length;
		colunas1  = matriz2.length;
		colunas2 = matriz2[0].length;
		
		double[][] matriz = new double[linhas1][colunas2];
		
		for(int i = 0; i < linhas1; i++){
			for(int j = 0; j < colunas2; j++){
				for(int k = 0; k < colunas1; k++){
					valor += matriz1[i][k] * matriz2[k][j];
				}
				matriz[i][j] = valor;
				valor = 0;
			}
		}

		return matriz;
	}
	 
	public static Point findBaryNormal(Triangulo tri2D, Triangulo tri3D, Point p) {
	    Point temp1, vetor;
	    double alfa, beta, gama;
	    double bary[] = tri2D.getBaryCoefs(p);
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
	
	public static void rotateX(Point p, double degrees){
		degrees = Math.toRadians(degrees);
		
		double x,y,z;
		
		y = p.y * Math.cos(degrees) + p.z * -Math.sin(degrees);
		z = p.y * Math.sin(degrees) + p.z * Math.cos(degrees);
		p.y = y;
		p.z = z;
	}
	
	public static void rotateY(Point p, double degrees){
		degrees = Math.toRadians(degrees);
		double x,y,z;
		
		x = p.x * Math.cos(degrees) + p.z * -Math.sin(degrees);
		z = p.x * Math.sin(degrees) + p.z * Math.cos(degrees);
		p.x = x;
		p.z = z;
	}
	
	public static void rotateZ(Point p, double degrees){
		degrees = Math.toRadians(degrees);
		double x,y;
		
		x = p.x * Math.cos(degrees) + p.y * -Math.sin(degrees);
		y = p.x * Math.sin(degrees) + p.y * Math.cos(degrees);
		p.x = x;
		p.y = y;
	}
	
}
