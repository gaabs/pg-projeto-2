package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import util.Debug;
import entidades.Camera;
import entidades.Point;
import entidades.Triangulo;

public class Gouraud extends JFrame{

	public double[][] z_buffer;
	BufferedImage objeto;
	public ArrayList<Triangulo> t;
	public ArrayList<Triangulo> t2;
	public static int ResX = 640;
	public static int ResY = 480;
	int qtdPontos =0;
	Debug debug;

	public Gouraud() throws IOException{
		super("Gouraud");

		//		debug = new Debug("debugGouraud.txt");

		objeto = new BufferedImage(ResX+1, ResY+1, BufferedImage.TYPE_INT_ARGB); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, ResX, ResY);

		z_buffer = new double[ResX+1][ResY+1];
		for (double[] row: z_buffer)
			Arrays.fill(row, Double.MAX_VALUE);

		this.t = Camera.triangulosConvertidos;
		this.t2 = Camera.triangulos2D;

		scanLine3D();
		fix();
		//		debug.close();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(objeto, 0, 0, null);
	}

	private void scanLine3D(){
		//Collections.sort(t);
		for(int i=0;i<t2.size();i++){
			pinte(Camera.intervalos.get(i),t.get(i).indice, i);
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
				for(int j= (int) intervalos[i][0].x;j<=intervalos[i][1].x;j++){
					Point pixel = new Point(j, intervalos[i][0].y);
					int x = (int) (pixel.x);
					int y = (int) (pixel.y);
					if(x>=0 && x<=ResX && y>=0 && y<=ResY ){
						double[] bary = t2.get(indice).getBaryCoefs(pixel);
						if (bary[0] < 0 || bary[1] < 0 || bary[2] < 0) continue;

						Point v1 = t.get(indice).v1;
						v1 = v1.multiplyWithColor(bary[0]);
						Point v2 = t.get(indice).v2;
						v2 = v2.multiplyWithColor(bary[1]);
						Point v3 = t.get(indice).v3;
						v3 = v3.multiplyWithColor(bary[2]);

						Point p = v1.add(v2).add(v3); 

						if(x <= ResX && y <= ResY && z_buffer[x][y]>p.z && p.z>=0){
							z_buffer[x][y] = p.z;
							Point I = p.color;
							int r,g,b;
							I.truncateXYZ();
							qtdPontos++;
							r = (int) Math.round(I.x); 
							g = (int) Math.round(I.y); 
							b = (int) Math.round(I.z); 
							int rgb = new Color(r,g,b).getRGB();
							//int rgb = Color.GREEN.getRGB();
							objeto.setRGB(x, y, rgb);
						}
					}
				}
			}
		}
	}
	
	private void fix() {
		for (int x=1;x<ResX;x++){
			for (int y=1;y<ResY;y++){
				if (z_buffer[x][y] == Double.MAX_VALUE && z_buffer[x][y+1] != Double.MAX_VALUE && z_buffer[x][y-1] != Double.MAX_VALUE){
					int r,g,b,r2,g2,b2;
					Color color;
					int rgb;
					
					rgb= objeto.getRGB(x, y+1);
					color = new Color(rgb);
					r2 = color.getRed()/2;
					g2 = color.getGreen()/2;
					b2 = color.getBlue()/2;
					
					rgb = objeto.getRGB(x, y-1);
					color = new Color(rgb);
					r2 += color.getRed()/2;
					g2 += color.getGreen()/2;
					b2 += color.getBlue()/2;
					
					rgb = objeto.getRGB(x, y);
					r = color.getRed();
					g = color.getGreen();
					b = color.getBlue();
					
//					if (z_buffer[x][y] == Double.MAX_VALUE || Math.abs(r-r2) > 60 || Math.abs(g-g2) > 60 || Math.abs(b-b2) > 60){
						color = new Color(r2,g2,b2);
						rgb = color.getRGB();
//					}
					
					objeto.setRGB(x, y, rgb);
				}
			}
		}
	}

}
