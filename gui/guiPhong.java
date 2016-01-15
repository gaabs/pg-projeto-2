package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.ProjecaoPontos;
import util.Util;
import entidades.*;

public class guiPhong extends JFrame{

	private JPanel panel;
	public double[][] z_buffer;
	BufferedImage objeto;
	public ArrayList<Triangulo> t;
	public ArrayList<Triangulo> t2;
	public double d, hx, hy;
	public static int ResX = 794; // Gio: isso n�o devia ser est�tico; Maia: Porqu�?
	public static int ResY = 571;

	public guiPhong(ArrayList<Triangulo> t, ArrayList<Triangulo> t2,double d,double hx,double hy){
		super("Phong");
		
		objeto = new BufferedImage(ResX+1, ResY+1, BufferedImage.TYPE_INT_ARGB); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, ResX, ResY);

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
		
		panel = new JPanel();
		this.add(panel);
		
		panel.add(new JLabel(new ImageIcon(objeto)));
		pack();//n�o conhe�o esse metodo
		//JOptionPane.showMessageDialog(null, new ImageIcon(objeto));
	}

	private void scanLine3D(){
		//Collections.sort(t);
		for(int i=0;i<t2.size();i++){
			pinte(Util.scanLine(t2.get(t.get(i).indice)),t.get(i).indice, i);
		}
		long tempo = Main.tempo;
		tempo = System.nanoTime() - tempo; 
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
							z_buffer[x1][y1] = p.z;
							Iluminacao.Ia=Iluminacao.Ia.normalize();
							Iluminacao.Il=Iluminacao.Il.normalize();
							Iluminacao.Od=Iluminacao.Od.normalize();
							p=p.normalize();
							p.normal=p.normal.normalize();
							Point L = p.subtract(Iluminacao.Pl).normalize();
							Point Id = Iluminacao.Il.multiply(L.dotProduct(p.normal)*Iluminacao.kd*Iluminacao.Od.norma());							
							Point R = p.normal.multiply(2).multiply(p.normal.dotProduct(L)).subtract(L).normalize();
							Point lambda = Iluminacao.Ia.multiply(Iluminacao.ka).add(Iluminacao.Od.multiply(Iluminacao.kd*L.dotProduct(p.normal)+Iluminacao.ks*R.dotProduct(Camera.V.normalize()))).normalize();
							if(lambda.x>255||lambda.y>255||lambda.z>255||lambda.x<0||lambda.y<0||lambda.z<0){
								System.err.println("deu merda");
							}
							int rgb = Color.GREEN.getRGB();
							objeto.setRGB(x1, y1, rgb);	
						}
					}
				}
			}
		}
	}

	private static void Phong(){
		//scanLine3D(t,t2,d,hx,hy);
		//resto de phong;
	}

}
