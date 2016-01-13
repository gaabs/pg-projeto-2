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

import Basicas.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.ProjecaoPontos;
import Utils.Util;

public class guiPhong extends JFrame{

	private static JPanel contentPane;
	public static int ResX = 794; 
	public static int ResY = 571;
	public static double[][] z_buffer;
	static BufferedImage objeto;
	public static ArrayList<Triangulo> t;
	public static ArrayList<Triangulo> t2;
	public static double d, hx, hy;
	public static Insets insets; 

	public guiPhong(ArrayList<Triangulo> t, ArrayList<Triangulo> t2,double d,double hx,double hy){

		super("Phong");
		
		objeto = new BufferedImage(ResX, ResY, BufferedImage.TYPE_INT_ARGB); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, ResX, ResY);

		z_buffer = new double[ResX+1][ResY+1];
		for (double[] row: z_buffer)
			Arrays.fill(row, Double.MAX_VALUE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		guiPhong.t=t;
		guiPhong.t2=t2;
		guiPhong.d=d;
		guiPhong.hx=hx;
		guiPhong.hy=hy;
		insets = getInsets();
		scanLine3D();

	}

	private static void scanLine3D(){
		//Collections.sort(t);
		for(int i=0;i<t2.size();i++){
			if(i==24){
				System.out.println();
			}
			pinte(Util.scanLine(t2.get(t.get(i).indice)),t.get(i).indice, i);
		}	
		JOptionPane.showMessageDialog(null, new ImageIcon(objeto)); 
	}


	private static void pinte(Point[][] ret, int indice, int k){
		for(int i=0;i<ret.length;i++){
			if(ret[i][0]!=null){
				System.out.println("x_min: "+ret[i][0].x+" x_max:"+ret[i][1].x+" y: "+ret[i][0].y);
				for(double j=ret[i][0].x;j<=ret[i][1].x;j++){
					Point temp = new Point(j, ret[i][0].y);
					if(temp.x>=0 && temp.x<=ResX && temp.y>=0 && temp.y<=ResY ){
						double[] bary = Util.findBary(t2.get(indice).v1, t2.get(indice).v2, t2.get(indice).v3, temp);
						Point v1 = t.get(indice).v1;
						Point v2 = t.get(indice).v2;
						Point v3 = t.get(indice).v3;
						Point p = v1.multiply(bary[0]).add(v2.multiply(bary[1])).add(v3.multiply(bary[2])); 
						int x1 = (int) Math.round(temp.x);
						int y1 = (int) Math.round(temp.y);
						//System.out.println("x: "+temp.x+" y: "+ temp.y);
						if(z_buffer[x1][y1]>p.z&&p.z>=0){
							z_buffer[x1][y1] = p.z;
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
