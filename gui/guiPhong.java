package gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import Basicas.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.Util;

public class guiPhong extends JFrame{

	private static JPanel contentPane;
	public static int ResX = 432; 
	public static int ResY = 421;
	
	
	public guiPhong(ArrayList<Triangulo> t, ArrayList<Triangulo2D> t2){
		
		super("Phong");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 421);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Phong(t,t2);
	}
	
	private static void scanLine3D(ArrayList<Triangulo> t, ArrayList<Triangulo2D> t2){
		Collections.sort(t);
		
		//sem penetrações de triangulo
		for(int i=0;i<t2.size();i++){
			if(t2.get(t.get(i).indice).indice==t.get(i).indice){
				pinte(Util.scanLine(t2.get(t.get(i).indice)));
			}
		}	
	}
	
	
	private static void pinte(Point2D[][] ret){
		//pinta XP
	}
	
	private static void Phong(ArrayList<Triangulo> t, ArrayList<Triangulo2D> t2){
		scanLine3D(t,t2);
		//resto de phong;
	}

}
