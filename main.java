import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class main {
	//camera
	static Point C,N,V,Vo,No,Vn,U;//vetores
	static double hx,hy,d;
	//iluminação
	static double ka,kd,ks,Pl,Ia,Od,Il,n;
	//objeto
	static ArrayList<Point> vertices = new ArrayList<Point>();
	static ArrayList<Triangulo> triangulos = new ArrayList<Triangulo>();
	
	public static void main(String[] args) {
		/*O seu sistema começa preparando a câmera,
		 * 
		 *  ler arquivo cfg*/
		try{
		File arquivo = new File("camera.cfg");
		
		if(!arquivo.exists()) {
			arquivo.createNewFile();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(arquivo));
		
		//Vetor C
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
