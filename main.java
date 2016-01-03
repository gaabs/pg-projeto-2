import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class main {
	//camera
	static Point C,N,V,Vo,No,Vn,U;//vetores
	static double hx,hy,d;
	//ilumina��o
	static double ka,kd,ks,Pl,Ia,Od,Il,n;
	//objeto
	static ArrayList<Point> vertices = new ArrayList<Point>();
	static ArrayList<Triangulo> triangulos = new ArrayList<Triangulo>();
	
	public static void main(String[] args) {
		/*O seu sistema come�a preparando a c�mera,
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
		 *   fazer a mudan�a de coordenadas para o sistema de vista de todos os v�rtices
		 *   do objeto e da posi��o da fonte de luz PL,
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
		 *   gerar as normais dos tri�ngulos 
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
		 *   e gerar as normais dos v�rtices (como recomendado em sala de aula).
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
		 *  Para cada tri�ngulo, calculam-se as proje��es dos seus v�rtices, 
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  calculam-se as cores dos v�rtices (utilizando as normais dos v�rtices, 
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  e calculando-se L, V e R e os substituindo na equa��o de ilumina��o) e 
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
		 *  inicia-se a sua convers�o por varredura.
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  Para cada pixel (x, yscan),
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
