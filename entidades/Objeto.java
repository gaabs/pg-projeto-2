package entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import util.ProjecaoPontos;
import util.Util;

public class Objeto {
	public static ArrayList<Point> vertices = new ArrayList<Point>();
	public static ArrayList<Triangulo> triangulos = new ArrayList<Triangulo>();
	public static ArrayList<Triangulo> triangulos2D = new ArrayList<Triangulo>();
	public static ArrayList<Point> vertices2D = new ArrayList<Point>();
	public static ArrayList<Point> vertices2DMapeados = new ArrayList<Point>();
	
	public static void setObjeto(String filepath) throws IOException {
		File objeto = new File(filepath);

		Scanner scan = new Scanner(objeto);
		scan.useLocale(Locale.ENGLISH);

		BufferedReader reader = new BufferedReader(new FileReader(objeto));

		int ver = scan.nextInt();
		int tri = scan.nextInt();

		//fazer a mudança de coordenadas para o sistema de vista de todos os vértices
		//do objeto

		for(int i=0;i<ver;i++){
			double d1 = scan.nextDouble();
			double d2 = scan.nextDouble();
			double d3 = scan.nextDouble();
			Point p = new Point(d1,d2,d3);
			//System.out.println("lendo ponto "+i+": "+p.toString());
			p=Util.convert(Camera.C, p);
			//System.out.println("lendo ponto "+i+" convertido para a Camera: "+p.toString());
			p.indice=i;
			vertices.add(p);

			// calculam-se as projeções dos seus vértices,
			vertices2D.add(ProjecaoPontos.projetar2D(p, Camera.d, Camera.hx, Camera.hy));
			//System.out.println("lendo ponto "+i+" convertido em 2D: "+vertices2D.get(vertices2D.size()-1));
			//Calcula-se o mapeamento dele para o frame

			Point u = ProjecaoPontos.map2Screen(vertices2D.get(vertices2D.size()-1));
			vertices2DMapeados.add(u);
			//System.out.println("lendo ponto "+i+" convertido em 2D e mapeado para o frame: "+vertices2DMapeados.get(vertices2D.size()-1));

		}

		//lendo triangulos
		for(int i=0;i<tri;i++){
			int v1,v2,v3;
			v1 = scan.nextInt() -1;
			v2 = scan.nextInt() -1;
			v3 = scan.nextInt() -1;
			int pontos[] = {v1,v2,v3};

			Triangulo t = new Triangulo(vertices.get(v1),vertices.get(v2),vertices.get(v3),i);

			//gerando normal do triangulo
			Point w1 = t.v2.subtract(t.v1);
			Point w2 = t.v3.subtract(t.v1);

			Point nt = w1.produtoVetorial(w2);

			nt = nt.normalize(); // n sei se precisa

			triangulos.add(t);
			//Ntriangulos.add(nt);

			for(int j=0;j<3;j++){

				//gerando normal parcial dos vertices deste triangulo	
				if(	vertices.get(pontos[j]).normal==null){
					vertices.get(pontos[j]).normal= nt;
				}else{
					vertices.get(pontos[j]).normal = vertices.get(pontos[j]).normal.add(nt);
				}
			}
			Triangulo t2 =  new Triangulo(vertices2DMapeados.get(v1),vertices2DMapeados.get(v2),vertices2DMapeados.get(v3),i);
			triangulos2D.add(t2);
		}
		scan.close();


		for(int i=0;i<ver;i++){
			if(vertices.get(i).normal!=null){
				vertices.get(i).normal=vertices.get(i).normal.normalize(); // n sei se precisa
			}//System.out.printf("Normal normalizada do vértice %d: %s\n",i,vertices.get(i).normal);
		}

		reader.close();
		
	}
	
	
}
