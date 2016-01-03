import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class main {
	//camera
	static Point C,N,V,Vo,No,Vn,U,Pl,Ia,Il,Od;//vetores
	static double hx,hy,d;
	//iluminação
	static double ka,kd,ks,n;
	//objeto
	static ArrayList<Point> vertices = new ArrayList<Point>();
	static ArrayList<Triangulo> triangulos = new ArrayList<Triangulo>();
	
	public static void main(String[] args) {
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

		//Vetor V
		xyz = Util.extract(reader.readLine());
		V = new Point(xyz[0],xyz[1],xyz[2]);
		
		//d, hx, hy
		xyz = Util.extract(reader.readLine());
		d = xyz[0];
		hx = xyz[1];
		hy = xyz[2];
		
		//abrindo objeto
		File objeto = new File("objeto.byu");
		
		if(!objeto.exists()) {
			objeto.createNewFile();
		}
		
		reader = new BufferedReader(new FileReader(objeto));
		
		String s = reader.readLine();
		int d = s.indexOf(" ");
		int n = Integer.parseInt(s.substring(0,d));
		int m = Integer.parseInt(s.substring(d+1));
		for(int i=0;i<n;i++){
			double[] pontos = Util.extract(reader.readLine());
			Point p = new Point(pontos[0],pontos[1],pontos[2]);
			vertices.add(p);
		}
		for(int i=0;i<m;i++){
			double[] pontos = Util.extract(reader.readLine());
			Triangulo t = new Triangulo(vertices.get((int) (pontos[0]-1)),vertices.get((int) (pontos[1]-1)),vertices.get((int) (pontos[2]-1)));
			triangulos.add(t);
		}
		
		File Iluminacao = new File("Iluminacao.txt");
		
		if(!Iluminacao.exists()) {
			Iluminacao.createNewFile();
		}
		
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
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// ortogonalizando V e N
		  
		Vo = Util.ortogonalizar(V, N);
		No = N.divide(Math.sqrt(N.dotProduct(N)));

		// Normalizando V
		Vn=Vo.multiply((1/Math.sqrt(Vo.dotProduct(Vo))));
				
		//gerando U 
		U = No.produtoVetorial(Vn);
		
		/*   
		 *   fazer a mudança de coordenadas para o sistema de vista de todos os vértices
		 *   do objeto e da posição da fonte de luz PL,
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   gerar as normais dos triângulos 
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   e gerar as normais dos vértices (como recomendado em sala de aula).
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *  Cria-se uma Janela para o objeto apresentado por Gouraud e 
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
		 *  Outra para Phong.
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  Para cada triângulo, calculam-se as projeções dos seus vértices, 
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  calculam-se as cores dos vértices (utilizando as normais dos vértices, 
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  e calculando-se L, V e R e os substituindo na equação de iluminação) e 
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  inicia-se a sua conversão por varredura.
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  Para cada pixel (x, yscan),
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
