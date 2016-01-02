
public class Util {

	public static void gramSchmidt(Point v1, Point v2, Point v3){
		Point temp;
		v2 = v2.subtract(v1.multiply(v2.dotProduct(v1)/v1.dotProduct(v1)));
		temp = v2.multiply(v3.dotProduct(v2)/v2.dotProduct(v2));
		temp = temp.add(v1.multiply(v3.dotProduct(v1)/v1.dotProduct(v1)));
		v3 = v3.subtract(temp); 
		
		System.out.println("V2': " + v2);
		System.out.println("V3': " + v3);
		
		v1 = v1.divide(Math.sqrt(v1.dotProduct(v1)));
		v2 = v2.divide(Math.sqrt(v2.dotProduct(v2)));
		v3 = v3.divide(Math.sqrt(v3.dotProduct(v3)));
		
		System.out.println("e1: " + v1);
		System.out.println("e2: " + v2);
		System.out.println("e3: " + v3);
	}
	
	public static void main(String[] args) {
		Point v1 = new Point(1,0,1);
		Point v2 = new Point(0,1,1);
		Point v3 = new Point(1,1,1);
		
		gramSchmidt(v1,v2,v3);
		
	}

}
