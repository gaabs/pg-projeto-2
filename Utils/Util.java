package Utils;
import Basicas.Point2D;
import Basicas.Point3D;
import Basicas.Triangulo;
import Basicas.Triangulo2D;


public class Util {

	public static double[][] alfa;

	public static Point3D[] gramSchmidt(Point3D v1, Point3D v2, Point3D v3){
		Point3D[] pontos = new Point3D[8];

		Point3D temp;
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

		pontos[3]=new Point3D(-1,-1,-1);

		pontos[4]=v1;
		pontos[5]=v2;
		pontos[6]=v3;

		pontos[7]=new Point3D(-1,-1,-1);

		System.out.println("e1: " + v1);
		System.out.println("e2: " + v2);
		System.out.println("e3: " + v3);

		return pontos;
	}

	public static Point3D ortogonalizar(Point3D V, Point3D N){
		Point3D V2 = V.subtract(N.multiply(V.dotProduct(N)/N.dotProduct(N)));

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

	public static void setAlfa(Point3D V, Point3D N, Point3D U){
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

	public static Point3D convert(Point3D C, Point3D p){

		double[][] m = alfa;

		double[][] m2 = new double [3][1];
		p=p.subtract(C);
		m2[0][0]=p.x;
		m2[1][0]=p.y;
		m2[2][0]=p.z;

		m = MatrixUtil.multiplicar(m, m2, 3, 3, 1);

		p.x=m[0][0];
		p.y=m[1][0];
		p.z=m[2][0];

		return p;
	}

	public static Point2D[][] scanLine(Triangulo2D t){
		Point2D[][] ret = null;
		double yMax = Math.max(Math.max(t.v1.y, t.v2.y), t.v3.y);
		double yMin = Math.min(Math.min(t.v1.y, t.v2.y), t.v3.y);
		int tam = (int)Math.ceil(yMax - yMin +1);
		ret = new Point2D[tam][2];
		double yMax2 = Math.max(t.v3.y, t.v2.y);
		
		if(t.v1.y==yMax && t.v2.y==yMax2){
			ret=aux(ret,t.v1,t.v2,t.v3);			
		}else if(t.v1.y==yMax && t.v3.y==yMax2){
			ret=aux(ret,t.v1,t.v3,t.v2);
		}else if(t.v2.y==yMax && t.v1.y==yMax2){
			ret=aux(ret,t.v2,t.v1,t.v3);
		}else if(t.v2.y==yMax && t.v3.y==yMax2){
			ret=aux(ret,t.v2,t.v3,t.v1);
		}else if(t.v3.y==yMax && t.v1.y==yMax2){
			ret=aux(ret,t.v3,t.v1,t.v2);
		}else{
			ret=aux(ret,t.v3,t.v2,t.v1);
		}

		return ret;
	}

	public static Point2D[][] aux(Point2D[][] ret, Point2D L, Point2D M, Point2D S){
		double a = (M.x - L.x)/(M.y - L.y);
		double b = M.y - a*M.x;

		double a2 = (S.x - L.x)/(S.y - L.y);
		double b2 = S.x - a*S.x;


		int k=0;
		for(double i=L.y;i>=M.y;i--, k++){
			double xTemp = (i-b)/a;
			double xTemp2 = (i-b2)/a2;
			ret[k][0] = new Point2D(xTemp, i);				
			ret[k][1] = new Point2D(xTemp2, i);
		}


		a = (M.x - S.x)/(M.y - S.y);
		b = M.y - a*M.x;



		for(double i=M.y;i>=S.y;i--,k++){
			double xTemp = (i-b)/a;
			double xTemp2 = (i-b2)/a2;
			ret[k][0] = new Point2D(xTemp, i);				
			ret[k][1] = new Point2D(xTemp2, i);
		}


		return ret;
	}

	public static void main(String[] args) {
		Point3D v1 = new Point3D(1,0,1);
		Point3D v2 = new Point3D(0,1,1);
		Point3D v3 = new Point3D(1,1,1);

		gramSchmidt(v1,v2,v3);

	}

}
