package gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.Debug;
import util.Util;
import entidades.Camera;
import entidades.Iluminacao;
import entidades.Point;
import entidades.Triangulo;

public class guiPhong extends JFrame{

	private JPanel panel;
	public double[][] z_buffer;
	BufferedImage objeto;
	public ArrayList<Triangulo> t;
	public ArrayList<Triangulo> t2;
	public double d, hx, hy; // não estamos usando essas variáveis nessa classe
	public static int ResX = 640; // Gio: isso não devia ser estático; Maia: Porquê? //Gio: pq não faz sentido outra classe chamar a GUI. A gui mandaria seus atributos
	public static int ResY = 480;
	int qtdPontos =0;
	Debug debug;
	
	public guiPhong(ArrayList<Triangulo> t, ArrayList<Triangulo> t2,double d,double hx,double hy) throws IOException{
		super("Phong");
		
		debug = new Debug("debugPhong.txt");

		objeto = new BufferedImage(ResX+1, ResY+1, BufferedImage.TYPE_INT_ARGB); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 0, ResX, ResY);

		z_buffer = new double[ResX+1][ResY+1];
		for (double[] row: z_buffer)
			Arrays.fill(row, Double.MAX_VALUE);

		//		contentPane = new JPanel();
		//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//		setContentPane(contentPane);
		//		contentPane.setLayout(null);

		this.t=t;
		this.t2=t2;
		this.d=d;
		this.hx=hx;
		this.hy=hy;

		scanLine3D();
		debug.close();
		
		panel = new JPanel();
		this.add(panel);

		panel.add(new JLabel(new ImageIcon(objeto)));
		pack();//não conheço esse metodo
		//JOptionPane.showMessageDialog(null, new ImageIcon(objeto));
	}

	private void scanLine3D(){
		//Collections.sort(t);
		for(int i=0;i<t2.size();i++){
			pinte(Util.scanLine(t2.get(t.get(i).indice)),t.get(i).indice, i);
		}
		long tempo = Main.tempo;
		tempo = System.nanoTime() - tempo; 
		System.out.println(qtdPontos);
		System.out.printf("Fim: %f segundos\n",tempo/1000000000.0);

	}

	private void pinte(Point[][] intervalos, int indice, int k){
		for(int i=0;i<intervalos.length;i++){
			if(intervalos[i][0]!=null){
				//System.out.println("x_min: "+ret[i][0].x+" x_max:"+ret[i][1].x+" y: "+ret[i][0].y);
				for(double j=intervalos[i][0].x;j<=intervalos[i][1].x;j++){
					Point pixel = new Point(j, intervalos[i][0].y);
					if(pixel.x>=0 && pixel.x<=ResX && pixel.y>=0 && pixel.y<=ResY ){
						double[] bary = t2.get(indice).getBaryCoefs(pixel);
						Point v1 = t.get(indice).v1;
						Point v2 = t.get(indice).v2;
						Point v3 = t.get(indice).v3;
						Point p = v1.multiply(bary[0]).add(v2.multiply(bary[1])).add(v3.multiply(bary[2])); 
						int x1 = (int) Math.round(pixel.x);
						int y1 = (int) Math.round(pixel.y);
						//System.out.println("x: "+pixel.x+" y: "+ pixel.y);
						if(x1 <= ResX && y1 <= ResY && z_buffer[x1][y1]>p.z && p.z>=0){

							//							cor final:
							//							I = Ia + Ie + Id
							//							Id = <L, N> * Kd * Od * Il




							z_buffer[x1][y1] = p.z;
							//Iluminacao.Il=Iluminacao.Il.normalize();
							Iluminacao.Od=Iluminacao.Od.normalize();
							//p=p.normalize();
							p.normal=p.normal.normalize();
							Point L = p.subtract(Iluminacao.Pl).normalize();
							Point VdoPonto = p.subtract(Camera.C).normalize();
							//Point VdoPonto = p.multiply(-1).normalize();
							Point Id = Iluminacao.Il.multiply(Math.abs(L.dotProduct(p.normal))*Iluminacao.kd).kronecker(Iluminacao.Od);						
							Point R = p.normal.multiply(2).multiply(p.normal.dotProduct(L)).subtract(L).normalize();
							Point lambda = Iluminacao.Ia.multiply(Iluminacao.ka).add(Iluminacao.Od.multiply(Iluminacao.kd*L.dotProduct(p.normal)+Iluminacao.ks*R.dotProduct(VdoPonto.normalize()))).normalize();
							Point Ie = Iluminacao.Il.multiply(Math.abs(R.dotProduct(VdoPonto))*Iluminacao.ks);

							// Ka??
							Point I = Iluminacao.Ia.add(Id).add(Ie);
							//							if(lambda.x>255||lambda.y>255||lambda.z>255||lambda.x<0||lambda.y<0||lambda.z<0){
							//								System.err.println("deu merda");
							//							}

							//						System.out.printf("Ia: %s Ie:%s Id:%s\n",Iluminacao.Ia, Ie, Id);
							if(I.x>255){
								I.x=255;
							}
							if(I.y>255){
								I.y=255;
							}
							if(I.z>255){
								I.z=255;
							}

							//							System.out.printf("Il(%f,%f,%f) kd:%f Od.norma: %f rgb(%f,%f,%f) ", Iluminacao.Il.x, Iluminacao.Il.y, Iluminacao.Il.z, Iluminacao.kd, Iluminacao.Od.norma(), Id.x,Id.y,Id.z);
							//							System.out.printf("Produto escalar <L,P.normal>: %f\n", L.dotProduct(p.normal));

							int r,g,b;
							r = (int) Math.round(I.x); 
							g = (int) Math.round(I.y); 
							b = (int) Math.round(I.z); 
							qtdPontos++;
							int rgb = new Color(r,g,b).getRGB();
							//int rgb = Color.GREEN.getRGB();
							objeto.setRGB(x1, y1, rgb);
							try{
								debug.write("x1: "+x1+"y1: "+y1+"\n");
								debug.write("p: "+p+"\n");
								debug.write("Pnormal: "+p.normal+"\n");
								debug.write("L: "+L+"\n");
								debug.write("V do Ponto: " + VdoPonto+"\n");
								debug.write("R: "+R+"\n");
								debug.write("Id: "+Id+"\n");
								debug.write("Ie: "+Ie+"\n");
								debug.write("I: "+I+"\n");
								debug.write(String.format("alfa: %f beta: %f gama: %f\n", bary[0],bary[1],bary[2]));
								
								
							}catch(Exception e){
								System.out.println(e.getStackTrace());
							}
						}
					}
				}
			}
		}
	}

}
