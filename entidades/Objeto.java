package entidades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Objeto {
	public static ArrayList<Point> vertices;
	public static ArrayList<Triangulo> triangulos;
	
	public static void setObjeto(String filepath) throws IOException {
		vertices = new ArrayList<Point>();
		triangulos = new ArrayList<Triangulo>();
		
		File objeto = new File(filepath);

		Scanner scan = new Scanner(objeto);
		scan.useLocale(Locale.ENGLISH);

		int ver = scan.nextInt();
		int tri = scan.nextInt();

		//fazer a mudança de coordenadas para o sistema de vista de todos os vértices
		//do objeto

		for(int i=0;i<ver;i++){
			double d1 = scan.nextDouble();
			double d2 = scan.nextDouble();
			double d3 = scan.nextDouble();
			Point p = new Point(d1,d2,d3);
			p.indice=i;
			
			vertices.add(p);
		}

		//lendo triangulos
		for(int i=0;i<tri;i++){
			int v1,v2,v3;
			v1 = scan.nextInt() -1;
			v2 = scan.nextInt() -1;
			v3 = scan.nextInt() -1;
			Triangulo t = new Triangulo(vertices.get(v1),vertices.get(v2),vertices.get(v3),i);
			triangulos.add(t);
		}
		scan.close();
	}
}
