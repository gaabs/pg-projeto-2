package entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import util.ProjecaoPontos;
import util.Util;

public class Camera {
	public static Point C, N, V, Vo, No, Vn, U;
	public static double d, hx, hy;

	public static ArrayList<Point> verticesConvertidos;
	public static ArrayList<Triangulo> triangulosConvertidos;
	public static ArrayList<Triangulo> triangulos2D;
	public static ArrayList<Point> vertices2D;
	public static ArrayList<Point> vertices2DMapeados;
	public static ArrayList<Point[][]> intervalos;

	public static void initCamera(String filepath) throws IOException{
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

		reader.close();
	}

	public static void setCamera() {
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
		
		//Setando matriz alfa
		Util.setAlfa(Camera.U,Camera.Vn, Camera.No);
	}


	public static void convertObject(){
		verticesConvertidos = new ArrayList<Point>();
		triangulosConvertidos = new ArrayList<Triangulo>();
		triangulos2D = new ArrayList<Triangulo>();
		vertices2D = new ArrayList<Point>();
		vertices2DMapeados = new ArrayList<Point>();

		ArrayList<Point> vertices = Objeto.vertices;
		int verticesSize = vertices.size();
		for(int i=0;i<verticesSize;i++){
			Point p = vertices.get(i);
			p = Util.convert(Camera.C, p);
			verticesConvertidos.add(p);

			// calculam-se as projeções dos seus vértices,
			vertices2D.add(ProjecaoPontos.projetar2D(p, Camera.d, Camera.hx, Camera.hy));
			//System.out.println("lendo ponto "+i+" convertido em 2D: "+vertices2D.get(vertices2D.size()-1));
			//Calcula-se o mapeamento dele para o frame

			Point u = ProjecaoPontos.map2Screen(vertices2D.get(vertices2D.size()-1));
			vertices2DMapeados.add(u);
			//System.out.println("lendo ponto "+i+" convertido em 2D e mapeado para o frame: "+vertices2DMapeados.get(vertices2D.size()-1));
		}

		ArrayList<Triangulo> triangulos = Objeto.triangulos;
		Triangulo triangulo;
		int triangulosSize = triangulos.size();

		for(int i=0;i<triangulosSize;i++){
			triangulo = triangulos.get(i);
			int v1,v2,v3;
			v1 = triangulo.v1.indice;
			v2 = triangulo.v2.indice;
			v3 = triangulo.v3.indice;
			int pontos[] = {v1,v2,v3};

			Triangulo t = new Triangulo(verticesConvertidos.get(v1),verticesConvertidos.get(v2),verticesConvertidos.get(v3),i);

			//gerando normal do triangulo
			Point w1 = t.v2.subtract(t.v1);
			Point w2 = t.v3.subtract(t.v1);

			Point nt = w1.produtoVetorial(w2);

			nt = nt.normalize(); // n sei se precisa

			triangulosConvertidos.add(t);
			//Ntriangulos.add(nt);

			for(int j=0;j<3;j++){

				//gerando normal parcial dos vertices deste triangulo	
				if(	verticesConvertidos.get(pontos[j]).normal==null){
					verticesConvertidos.get(pontos[j]).normal= nt;
				}else{
					verticesConvertidos.get(pontos[j]).normal = verticesConvertidos.get(pontos[j]).normal.add(nt);
				}
			}
			Triangulo t2 =  new Triangulo(vertices2DMapeados.get(v1),vertices2DMapeados.get(v2),vertices2DMapeados.get(v3),i);
			triangulos2D.add(t2);
		}

		for(int i=0;i<verticesSize;i++){
			if(verticesConvertidos.get(i).normal!=null){
				verticesConvertidos.get(i).normal=verticesConvertidos.get(i).normal.normalize(); // n sei se precisa
			}//System.out.printf("Normal normalizada do vértice %d: %s\n",i,vertices.get(i).normal);
		}
	}

	public static void setIntervalos(){
		intervalos = new ArrayList<Point[][]>();
		int size = triangulos2D.size();
		for(int i = 0; i < size; i++){
			intervalos.add(Util.scanLine(triangulos2D.get(triangulosConvertidos.get(i).indice)));
		}
	}



}
