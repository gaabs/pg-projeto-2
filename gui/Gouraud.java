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
import entidades.Iluminacao;
import entidades.Objeto;
import entidades.Point;
import entidades.Triangulo;



public class Gouraud extends JFrame{

	private JPanel panel;
	public double[][] z_buffer;
	BufferedImage objeto;
	public ArrayList<Triangulo> t;
	public ArrayList<Triangulo> t2;
	public static int ResX = 640; // Gio: isso não devia ser estático; Maia: Porquê?
	public static int ResY = 480;
	int qtdPontos =0;
	Debug debug;
	
	public Gouraud() throws IOException{
		super("Gouraud");
		
		debug = new Debug("debugGouraud.txt");

		objeto = new BufferedImage(ResX+1, ResY+1, BufferedImage.TYPE_INT_ARGB); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, ResX, ResY);

		z_buffer = new double[ResX+1][ResY+1];
		for (double[] row: z_buffer)
			Arrays.fill(row, Double.MAX_VALUE);

		this.t = Objeto.triangulos;
		this.t2 = Objeto.triangulos2D;

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
						
						v1 = v1.multiplyWithColor(bary[0]);
						Point v2 = t.get(indice).v2;
						v2 = v2.multiplyWithColor(bary[1]);
						Point v3 = t.get(indice).v3;
						v3 = v3.multiplyWithColor(bary[2]);
//						System.out.println("v1: " + v1.color);
//						System.out.println("v2: " + v2.color);
//						System.out.println("v3: " + v3.color);
						Point p = v1.add(v2).add(v3); 
						debug.write("p: "+p+"\n");
						debug.write("alfa: "+bary[0]+" beta: "+bary[1]+" gama: "+bary[2]+"\n");
						int x1 = (int) Math.round(pixel.x);
						int y1 = (int) Math.round(pixel.y);
						//System.out.println("x: "+pixel.x+" y: "+ pixel.y);
						if(x1 <= ResX && y1 <= ResY && z_buffer[x1][y1]>p.z && p.z>=0){
							z_buffer[x1][y1] = p.z;
							Point I = p.color;
							int r,g,b;
							I.truncateXYZ();
							qtdPontos++;
							r = (int) Math.round(I.x); 
							g = (int) Math.round(I.y); 
							b = (int) Math.round(I.z); 
							int rgb = new Color(r,g,b).getRGB();
							//int rgb = Color.GREEN.getRGB();
							objeto.setRGB(x1, y1, rgb);
						}
					}
				}
			}
		}
	}

}
