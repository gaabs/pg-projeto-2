package gui;
import java.util.Scanner;

import util.Util;
import entidades.Camera;
import entidades.Iluminacao;
import entidades.Objeto;

public class Main {
	static long tempo = System.nanoTime();

	public static void main(String[] args) {
		
		String cameraName,objectName;
		
		cameraName = objectName = "calice2";

		/*O seu sistema começa preparando a câmera,
		 * 
		 *  ler arquivo cfg*/
		try{
			System.out.print("Nome da camera (sem extensão):");
			Scanner scan = new Scanner(System.in);
			//cameraName = scan.next();
			Camera.initCamera("Entradas/Cameras/"+cameraName+".cfg");
			Camera.setCamera();

			//abrindo objeto
			//System.out.println("abrindo objeto");
			System.out.print("Nome do objeto (sem extensão):");
			//objectName = scan.next();

			Objeto.setObjeto("Entradas/Objetos/"+objectName+".byu");
			Camera.convertObject();
			
			Iluminacao.setIluminacao("Entradas/Iluminacao.txt");
			
			System.out.printf("Demorou %f segundos para ler e etc\n",(System.nanoTime() - tempo)/1000000000.0);	

			Camera.setIntervalos();
			//Cria-se uma Janela para o objeto apresentado por Gouraud e 
			//Outra para Phong.
			Gouraud frame2 = new Gouraud();
			frame2.setVisible(true);
			guiPhong frame = new guiPhong();

			frame.setVisible(true);

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
