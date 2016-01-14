package gui;
import entidades.Point;
import entidades.Triangulo;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import util.ProjecaoPontos;
import util.Util;


public class Main {

	//OBJETO

	static long tempo = System.nanoTime();


	public static void main(String[] args) {
		//CAMERA

		Point C;
		Point N;
		Point V;
		Point Vo,No,Vn,U; 
		Point Pl;
		Point Ia;
		Point Il;
		Point Od;
		
		//C->foco da camera
		//N->vetor no eixo Z
		//V->?
		//quando tem um 'o' junto da variável é pq é ortogonalizado
		//quando tem um 'n' junto da variável é pq é normalizado(ou ortonormalizado)
		//Pl -> Coordenadas do ponto de luz
		//Ia -> vetor cor ambiental
		//Il -> cor da fonte de luz
		//Od -> vetor difuso

		double hx;
		double hy;
		double d; // distância do centro da camera ao plano de projeção		
		
		//ILUMINAÇÃO
		double ka; // reflexao ambiental
		double kd; // constante difusa
		double ks; // coeficiente especular
		double n; // constante de rugosidade
		
		ArrayList<Point> vertices = new ArrayList<Point>();
		ArrayList<Triangulo> triangulos = new ArrayList<Triangulo>();
		ArrayList<Triangulo> triangulos2D = new ArrayList<Triangulo>();
		ArrayList<Point> Ntriangulos = new ArrayList<Point>();
		ArrayList<Point> vertices2D = new ArrayList<Point>();
		ArrayList<Point> vertices2DMapeados = new ArrayList<Point>();

		/*O seu sistema começa preparando a câmera,
		 * 
		 *  ler arquivo cfg*/
		try{
			File camera = new File("camera.cfg");

			if(!camera.exists()) {
				camera.createNewFile();
			}

			BufferedReader reader = new BufferedReader(new FileReader(camera));

			//Vetor C
			//extract extrai 3 doubles de uma string e devolve um array com eles
			double[] xyz = Util.extract(reader.readLine());
			C = new Point(xyz[0],xyz[1],xyz[2]);

			//Vetor N
			xyz = Util.extract(reader.readLine());
			N = new Point(xyz[0],xyz[1],xyz[2]);
			//System.out.println("N entrada: " + N);

			//Vetor V
			xyz = Util.extract(reader.readLine());
			V = new Point(xyz[0],xyz[1],xyz[2]);
			//System.out.println("V entrada: " + V);

			//d, hx, hy
			xyz = Util.extract(reader.readLine());
			d = xyz[0];
			hx = xyz[1];
			hy = xyz[2];

			//processo gramSchmidt:
			
			// ortogonalizando V e N

			Vo = Util.ortogonalizar(V, N);
			No = N.divide(Math.sqrt(N.dotProduct(N)));

			//System.out.println("N ortogonalizado: " + No);

			// Normalizando V

			Vn=Vo.normalize();

			//System.out.println("V ortogonalizado e normalizado: " + Vn);

			//gerando U 

			U = No.produtoVetorial(Vn);
			System.out.println("U gerado: " + U);


			//Setando matriz alfa

			Util.setAlfa(U,Vn, No);

			//abrindo objeto
			//System.out.println("abrindo objeto");
			File objeto = new File("objeto.byu");
			if(!objeto.exists()) {
				objeto.createNewFile();
			}

			Scanner s = new Scanner(objeto);
			s.useLocale(Locale.ENGLISH);

			reader.close();
			reader = new BufferedReader(new FileReader(objeto));

			int ver = s.nextInt();
			int tri = s.nextInt();

			//fazer a mudança de coordenadas para o sistema de vista de todos os vértices
			//do objeto

			for(int i=0;i<ver;i++){
				double d1 = s.nextDouble();
				double d2 = s.nextDouble();
				double d3 = s.nextDouble();
				Point p = new Point(d1,d2,d3);
				//System.out.println("lendo ponto "+i+": "+p.toString());
				p=Util.convert(C, p);
				//System.out.println("lendo ponto "+i+" convertido para a Camera: "+p.toString());
				p.indice=i;
				vertices.add(p);

				// calculam-se as projeções dos seus vértices,
				vertices2D.add(ProjecaoPontos.projetar2D(p, d, hx, hy));
				//System.out.println("lendo ponto "+i+" convertido em 2D: "+vertices2D.get(vertices2D.size()-1));
				//Calcula-se o mapeamento dele para o frame

				Point u = ProjecaoPontos.map2Screen(vertices2D.get(vertices2D.size()-1));
				vertices2DMapeados.add(u);
				//System.out.println("lendo ponto "+i+" convertido em 2D e mapeado para o frame: "+vertices2DMapeados.get(vertices2D.size()-1));

			}

			//lendo triangulos
			for(int i=0;i<tri;i++){
				int v1,v2,v3;
				v1 = s.nextInt() -1;
				v2 = s.nextInt() -1;
				v3 = s.nextInt() -1;
				int pontos[] = {v1,v2,v3};

				Triangulo t = new Triangulo(vertices.get(v1),vertices.get(v2),vertices.get(v3),i);

				//gerando normal do triangulo
				Point w1 = t.v2.subtract(t.v1);
				Point w2 = t.v3.subtract(t.v1);

				Point nt = w1.produtoVetorial(w2);

				//nt = nt.normalize();

				triangulos.add(t);
				Ntriangulos.add(nt);

				for(int j=0;j<3;j++){

					//gerando normal parcial dos vertices deste triangulo	
					if(	vertices.get(pontos[j]).normal==null){
						vertices.get(pontos[j]).normal= nt;
					}else{
						vertices.get(pontos[j]).normal = vertices.get(pontos[j]).normal.add(nt);
					}
				}
				Triangulo t2 =  new Triangulo(vertices2DMapeados.get(v1),vertices2DMapeados.get(v2),vertices2DMapeados.get(v3),i);
				t2.ordenarY();
				triangulos.get(i).ordenarY();
				triangulos2D.add(t2);
			}
			s.close();

			//System.exit(1);

				File Iluminacao = new File("Iluminacao.txt");

			if(!Iluminacao.exists()) {
				Iluminacao.createNewFile();
			}

			reader.close();
			reader = new BufferedReader(new FileReader(Iluminacao));

			double[] luz = Util.extract(reader.readLine());
			Pl = new Point(luz[0],luz[1],luz[2]);
			ka = Double.parseDouble(reader.readLine());
			double[] cor = Util.extract(reader.readLine());
			Ia = new Point(cor[0],cor[1],cor[2]);
			kd = Double.parseDouble(reader.readLine());
			double[] dif = Util.extract(reader.readLine());
			Od = new Point(dif[0],dif[1],dif[2]);
			ks = Double.parseDouble(reader.readLine());
			cor = Util.extract(reader.readLine());
			Il = new Point(cor[0],cor[1],cor[2]);
			n = Integer.parseInt(reader.readLine());
			reader.close();

			System.out.printf("Demorou %f segundos para ler e etc\n",(System.nanoTime() - tempo)/1000000000.0);
			
			//fazer a mudança de coordenadas para o sistema de vista da posição
			//da fonte de luz PL,

			Pl = Util.convert(C, Pl);		

			//Cria-se uma Janela para o objeto apresentado por Gouraud e 
			//Outra para Phong.
			guiPhong frame = new guiPhong(triangulos,triangulos2D,d,hx,hy);

			frame.setVisible(true);
			guiGouraud frame2 = new guiGouraud(hx,hy);
			frame2.setVisible(true);

		}catch(Exception e){
			e.printStackTrace();
		}



		//calculam-se as cores dos vértices (utilizando as normais dos vértices,

		//e calculando-se L, V e R e os substituindo na equação de iluminação) e 
		//inicia-se a sua conversão por varredura.

		/*  Para cada pixel (x, yscan),
		 *  calculam-se suas coordenadas baricêntricas com relação aos vértices projetados, 
		 *  e multiplicam-se essas coordenadas pelos correspondentes vértices do triângulo 3D
		 *  original para se obter uma aproximação para o ponto 3D original correspondente
		 *  ao pixel atual.
		 *  
		 *  Após uma consulta ao z-buffer, se for o caso, calcula-se uma aproximação
		 *  para a normal do ponto utilizando-se mesmas coordenadas baricêntricas
		 *  multiplicadas pelas normais dos respectivos vértices originais.
		 *  Calculam-se também os demais vetores (L, V e R) e os substitui na equação do
		 *  modelo de iluminação de Phong produzindo a cor do pixel atual
		 *  (da janela direita). 
		 *  
		 *  
		 *  Para o Gouraud, utilizam-se as coordenadas baricêntricas multiplicadas 
		 *  pelas respectivas cores dos vértices e pinta-se na janela da esquerda
		 *   (cada ponto determinado pela varredura gera dois pixels, um em cada janela).*/ 

	}

}
