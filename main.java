import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class main {
	//camera
	static Point C,N,V;//vetores
	double hx,hy,d;
	//iluminação
	double ka,kd,ks,Pl,Ia,Od,Il,n;
	//objeto
	ArrayList<Point> vertices = new ArrayList<Point>();
	ArrayList<Triangulo> triangulos = new ArrayList<Triangulo>();
	
	public static void main(String[] args) {
		/*O seu sistema começa preparando a câmera,
		 * 
		 *  ler arquivo cfg
		 *  */
		try{
		File arquivo = new File("camera.cfg");
		if(!arquivo.exists()) {
			arquivo.createNewFile();
		}
		BufferedReader reader = new BufferedReader(new FileReader(arquivo));
		String CxCyCz = reader.readLine();
		int d = CxCyCz.indexOf(" ");
		double Cx = Integer.parseInt(CxCyCz.substring(0, d));
		String CyCz = CxCyCz.substring(d+1);
		d = CyCz.indexOf(" ");
		double Cy = Integer.parseInt(CyCz.substring(0, d));
		double Cz = Integer.parseInt(CyCz.substring(d+1));
		C = new Point(Cx,Cy,Cz);
		System.out.println(C.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		/*
		 *  ortogonalizando V
		 *  
		 *   
		 *   
		 *   
		 *   
		 *   e gerando U (?) , 
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   e depois os normalizando, 
		 *   
		 *   
		 *   
		 *   
		 *   
		 *   
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
