package entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import util.Util;

public class Iluminacao {
	public static Point Pl; // - Coordenadas do ponto de luz
	public static Point Pl0; // - Coordenadas do ponto de luz
	public static double ka; // - reflexao ambiental
	public static Point Ia; // - vetor cor ambiental
	public static double kd; // - constante difusa
	public static Point Od; // - vetor difuso
	public static double ks; // - coeficiente especular
	public static Point Il; // - cor da fonte de luz
	public static double n; // - constante de rugosidade
	
	public static void initIluminacao(String filepath) throws IOException {
		File iluminacaoEntrada = new File(filepath);
		BufferedReader reader = new BufferedReader(new FileReader(iluminacaoEntrada));

		double[] luz = Util.extract(reader.readLine());
		Iluminacao.Pl0 = new Point(luz[0],luz[1],luz[2]);
		Iluminacao.ka = Double.parseDouble(reader.readLine());
		double[] cor = Util.extract(reader.readLine());
		Iluminacao.Ia = new Point(cor[0],cor[1],cor[2]);
		Iluminacao.kd = Double.parseDouble(reader.readLine());
		double[] dif = Util.extract(reader.readLine());
		Iluminacao.Od = new Point(dif[0],dif[1],dif[2]);
		Iluminacao.ks = Double.parseDouble(reader.readLine());
		cor = Util.extract(reader.readLine());
		Iluminacao.Il = new Point(cor[0],cor[1],cor[2]);
		Iluminacao.n = Integer.parseInt(reader.readLine());
		reader.close();	
	}
	
	public static void setIluminacao(){
		//fazer a mudança de coordenadas para o sistema de vista da posição
		//da fonte de luz PL,

		Iluminacao.Pl = Util.convert(Camera.C, Iluminacao.Pl0);
	}
}

