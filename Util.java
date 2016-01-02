
public class Util {

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
	
	public static Point ortogonalizar(double x, double y, double z){
		Point resp = new Point(x,y,z);
		
		/*V' = V - (<V, N>/<N, N>) * N*/
		
		
		return resp;
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
	
	
	
	public static void main(String[] args) {
		Point v1 = new Point(1,0,1);
		Point v2 = new Point(0,1,1);
		Point v3 = new Point(1,1,1);
		
		gramSchmidt(v1,v2,v3);
		
	}

}
