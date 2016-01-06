package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	public static int ResX = 432; 
	public static int ResY = 421;
	public static double[][] z_buffer;
	static BufferedImage objeto;
	public static ArrayList<Triangulo> t;
	public static ArrayList<Triangulo2D> t2;
	public static double d, hx, hy;

	public guiPhong(ArrayList<Triangulo> t, ArrayList<Triangulo2D> t2,double d,double hx,double hy){

		super("Phong");
		objeto = new BufferedImage(ResX, ResY, BufferedImage.TYPE_INT_ARGB); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, ResX, ResY);

		z_buffer = new double[ResX][ResY];
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
		scanLine3D();

	}

	private static void scanLine3D(){
		Collections.sort(t);
		//sem penetrações de triangulo
		for(int i=0;i<t2.size();i++){
			pinte(Util.scanLine(t2.get(t.get(i).indice)),t.get(i).indice);
		}	
		JOptionPane.showMessageDialog(null, new ImageIcon(objeto)); 
	}


	private static void pinte(Point2D[][] ret, int indice){
		for(int i=0;i<ret.length-1;i++){
			for(double j=ret[i][0].x;j<ret[i][1].x;j++){
				Point2D temp = new Point2D(j, ret[i][0].y);
				if(temp.x>=100 && temp.x<=ResX && temp.y>=100 && temp.y<=ResY ){ 
					//System.out.println("x: "+temp.x+" y: "+ temp.y);
					int rgb = Color.GREEN.getRGB();
					objeto.setRGB((int)temp.x, (int)temp.y, rgb);  
				}
			}
		}
	}

	private static void Phong(){
		//scanLine3D(t,t2,d,hx,hy);
		//resto de phong;
	}

}
