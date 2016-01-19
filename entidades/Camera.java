package entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import util.Util;

public class Camera {
	public static Point C, N, V, Vo, No, Vn, U;
	public static double d, hx, hy;
	
	public static void setCamera(String filepath) throws IOException{
		//cameraName = scan.next();
		File camera = new File(filepath);

		BufferedReader reader = new BufferedReader(new FileReader(camera));

		//Vetor C
		//extract extrai 3 doubles de uma string e devolve um array com eles
		double[] xyz = Util.extract(reader.readLine());
		Camera.C = new Point(xyz[0],xyz[1],xyz[2]);

		//Vetor N
		xyz = Util.extract(reader.readLine());
		Camera.N = new Point(xyz[0],xyz[1],xyz[2]);
		//System.out.println("N entrada: " + N);

		//Vetor V
		xyz = Util.extract(reader.readLine());
		Camera.V = new Point(xyz[0],xyz[1],xyz[2]);
		//System.out.println("V entrada: " + V);

		//d, hx, hy
		xyz = Util.extract(reader.readLine());
		d = xyz[0];
		hx = xyz[1];
		hy = xyz[2];

		//processo gramSchmidt:

		// ortogonalizando V e N

		Camera.Vo = Util.ortogonalizar(Camera.V, Camera.N);
		Camera.No = Camera.N.divide(Math.sqrt(Camera.N.dotProduct(Camera.N)));

		//System.out.println("N ortogonalizado: " + No);

		// Normalizando V

		Camera.Vn=Camera.Vo.normalize();

		//System.out.println("V ortogonalizado e normalizado: " + Vn);

		//gerando U 

		Camera.U = Camera.No.produtoVetorial(Camera.Vn);
		
		reader.close();
	}
	
}
