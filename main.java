import gui.guiGouraud;
import gui.guiPhong;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import Utils.ProjecaoPontos;
import Utils.Util;
import Basicas.Point3D;
import Basicas.Point2D;
import Basicas.Triangulo;


public class main {
	//CAMERA
	
	//C->foco da camera
	//N->vetor no eixo Z
	//V->?
	//quando tem um 'o' junto da vari�vel � pq � ortogonalizado
	//quando tem um 'n' junto da vari�vel � pq � normalizado(ou ortonormalizado)
	//Pl -> Coordenadas do ponto de luz
	//Ia -> vetor cor ambiental
	//Il -> cor da fonte de luz
	//Od -> vetor difuso
	
	static Point3D C,N,V,Vo,No,Vn,U,Pl,Ia,Il,Od;//vetores

	//hx ->
	//hy ->
	//d -> dist�ncia do centro da camera ao plano de proje��o
	
	static double hx,hy,d;
	
	//ILUMINA��O
	
	//ka -> reflexao ambiental
	//kd -> constante difusa
	//ks -> coeficiente especular
	//n  -> constante de rugosidade
	static double ka,kd,ks,n;
	
	//OBJETO
	
	static ArrayList<Point3D> vertices = new ArrayList<Point3D>();
	static ArrayList<Triangulo> triangulos = new ArrayList<Triangulo>();
	static ArrayList<Point3D> Ntriangulos = new ArrayList<Point3D>();
	static ArrayList<Point3D> Nvertices = new ArrayList<Point3D>();
	static ArrayList<Point2D> vertices2D = new ArrayList<Point2D>();
	static ArrayList<Point2D> vertices2DMapeados = new ArrayList<Point2D>();

	
	
	public static void main(String[] args) {
		/*O seu sistema come�a preparando a c�mera,
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
			C = new Point3D(xyz[0],xyz[1],xyz[2]);

			//Vetor N
			xyz = Util.extract(reader.readLine());
			N = new Point3D(xyz[0],xyz[1],xyz[2]);

			//Vetor V
			xyz = Util.extract(reader.readLine());
			V = new Point3D(xyz[0],xyz[1],xyz[2]);

			//d, hx, hy
			xyz = Util.extract(reader.readLine());
			d = xyz[0];
			hx = xyz[1];
			hy = xyz[2];

			// ortogonalizando V e N

			Vo = Util.ortogonalizar(V, N);
			No = N.divide(Math.sqrt(N.dotProduct(N)));

			// Normalizando V

			Vn=Vo.multiply((1/Math.sqrt(Vo.dotProduct(Vo))));

			//gerando U 

			U = No.produtoVetorial(Vn);

			//Setando matriz alfa
			
			Util.setAlfa(Vn, No, U);
			
			//abrindo objeto
			File objeto = new File("objeto.byu");

			if(!objeto.exists()) {
				objeto.createNewFile();
			}

			reader = new BufferedReader(new FileReader(objeto));

			String s = reader.readLine();
			int d = s.indexOf(" ");
			int ver = Integer.parseInt(s.substring(0,d));
			int tri = Integer.parseInt(s.substring(d+1));

			//fazer a mudan�a de coordenadas para o sistema de vista de todos os v�rtices
			//do objeto

			for(int i=0;i<ver;i++){
				double[] pontos = Util.extract(reader.readLine());
				Point3D p = new Point3D(pontos[0],pontos[1],pontos[2]);
				p=Util.convert(C, p);
				vertices.add(p);
			}
			
			Point3D[] NverticesArray = new Point3D[ver];
			
			for(int i=0;i<tri;i++){
				double[] pontos = Util.extract(reader.readLine());
				Triangulo t = new Triangulo(vertices.get((int) (pontos[0]-1)),vertices.get((int) (pontos[1]-1)),vertices.get((int) (pontos[2]-1)));


				//gerando normal do triangulo
				Point3D w1 = t.v2.subtract(t.v1);
				Point3D w2 = t.v3.subtract(t.v1);

				Point3D nt = w1.produtoVetorial(w2);

				triangulos.add(t);
				Ntriangulos.add(nt);

				for(int j=0;j<3;j++){
					
					//gerando normal parcial dos vertices deste triangulo	
					if(	NverticesArray[(int) (pontos[j]-1)]==null){
						NverticesArray[(int) (pontos[j]-1)]= nt;
						//Para cada tri�ngulo, calculam-se as proje��es dos seus v�rtices,
						
						vertices2D.add(ProjecaoPontos.projetar2D(vertices.get((int)pontos[i]-1), d, hx, hy));
						
						//Calcula-se o mapeamento dele para o frame
						
						Point2D u = ProjecaoPontos.map2Screen(vertices2D.get(vertices2D.size()-1), d, hx, hy);
						if(u!=null){
							//mapeado
							vertices2DMapeados.add(u);
						}
						
					}else{
						NverticesArray[(int) (pontos[j]-1)] = NverticesArray[(int) (pontos[j]-1)].add(nt);
					}
				}
				
			}

			//deixando as normais dos vertices em uma var global
			
			for(int i=0;i<ver;i++){
				Nvertices.add(NverticesArray[i]);
			}
			
			
			File Iluminacao = new File("Iluminacao.txt");

			if(!Iluminacao.exists()) {
				Iluminacao.createNewFile();
			}

			reader = new BufferedReader(new FileReader(Iluminacao));

			double[] luz = Util.extract(reader.readLine());
			Pl = new Point3D(luz[0],luz[1],luz[2]);
			ka = Double.parseDouble(reader.readLine());
			double[] cor = Util.extract(reader.readLine());
			Ia = new Point3D(cor[0],cor[1],cor[2]);
			kd = Double.parseDouble(reader.readLine());
			double[] dif = Util.extract(reader.readLine());
			Od = new Point3D(dif[0],dif[1],dif[2]);
			ks = Double.parseDouble(reader.readLine());
			cor = Util.extract(reader.readLine());
			Il = new Point3D(cor[0],cor[1],cor[2]);
			n = Integer.parseInt(reader.readLine());

		}catch(Exception e){
			e.printStackTrace();
		}

		//fazer a mudan�a de coordenadas para o sistema de vista da posi��o
		//da fonte de luz PL,

		Pl = Util.convert(C, Pl);		

		   
		//Cria-se uma Janela para o objeto apresentado por Gouraud e 
		//Outra para Phong.
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				guiPhong frame = new guiPhong(hx,hy);
				frame.setVisible(true);
				guiGouraud frame2 = new guiGouraud(hx,hy);
				frame2.setVisible(true);
			}
		});
		
		
		
		//calculam-se as cores dos v�rtices (utilizando as normais dos v�rtices,
		
		//e calculando-se L, V e R e os substituindo na equa��o de ilumina��o) e 
		//inicia-se a sua convers�o por varredura.
		
		/*  Para cada pixel (x, yscan),
		 *  calculam-se suas coordenadas baric�ntricas com rela��o aos v�rtices projetados, 
		 *  e multiplicam-se essas coordenadas pelos correspondentes v�rtices do tri�ngulo 3D
		 *  original para se obter uma aproxima��o para o ponto 3D original correspondente
		 *  ao pixel atual.
		 *  
		 *  Ap�s uma consulta ao z-buffer, se for o caso, calcula-se uma aproxima��o
		 *  para a normal do ponto utilizando-se mesmas coordenadas baric�ntricas
		 *  multiplicadas pelas normais dos respectivos v�rtices originais.
		 *  Calculam-se tamb�m os demais vetores (L, V e R) e os substitui na equa��o do
		 *  modelo de ilumina��o de Phong produzindo a cor do pixel atual
		 *  (da janela direita). 
		 *  
		 *  
		 *  Para o Gouraud, utilizam-se as coordenadas baric�ntricas multiplicadas 
		 *  pelas respectivas cores dos v�rtices e pinta-se na janela da esquerda
		 *   (cada ponto determinado pela varredura gera dois pixels, um em cada janela).*/ 

	}

}
