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

		/*O seu sistema come�a preparando a c�mera,
		 * 
		 *  ler arquivo cfg*/
		try{
			System.out.print("Nome da camera (sem extens�o):");
			Scanner scan = new Scanner(System.in);
			//cameraName = scan.next();
			Camera.initCamera("Entradas/Cameras/"+cameraName+".cfg");
			Camera.setCamera();

			//abrindo objeto
			//System.out.println("abrindo objeto");
			System.out.print("Nome do objeto (sem extens�o):");
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
