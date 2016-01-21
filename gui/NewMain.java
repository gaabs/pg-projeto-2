package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entidades.Camera;
import entidades.Iluminacao;
import entidades.Objeto;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class NewMain extends JFrame{

	static long tempo = System.nanoTime();
	int ResX = 640;
	int ResY = 480;
	JPanel panel;
	JLabel labelObjeto;
	JTextField textFieldObjeto;
	JLabel labelCamera;
	JTextField textFieldCamera;
	JLabel labelIluminacao;
	JTextField textFieldIluminacao;
	JButton button;
	Gouraud gouraud;
	Phong phong;
	JButton zoomIn, zoomOut, rotateX, rotateX2, rotateY, rotateY2, rotateZ, rotateZ2;
	double d = 0.5;
	double degrees = 30;

	public NewMain(){
		super("Setup");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 100);

		panel = new JPanel();
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{37, 89, 89, 89, 89, 89, 89, 89, 89, 0, 0};
		gbl_panel.rowHeights = new int[]{30, 23, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		labelObjeto = new JLabel("Objeto:");
		labelObjeto.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelObjeto = new GridBagConstraints();
		gbc_labelObjeto.fill = GridBagConstraints.BOTH;
		gbc_labelObjeto.insets = new Insets(0, 0, 5, 5);
		gbc_labelObjeto.gridx = 1;
		gbc_labelObjeto.gridy = 0;
		panel.add(labelObjeto, gbc_labelObjeto);

		textFieldObjeto = new JTextField("calice2");
		GridBagConstraints gbc_textFieldObjeto = new GridBagConstraints();
		gbc_textFieldObjeto.fill = GridBagConstraints.BOTH;
		gbc_textFieldObjeto.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldObjeto.gridx = 2;
		gbc_textFieldObjeto.gridy = 0;
		panel.add(textFieldObjeto, gbc_textFieldObjeto);

		labelCamera = new JLabel("Camera:");
		labelCamera.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelCamera = new GridBagConstraints();
		gbc_labelCamera.fill = GridBagConstraints.BOTH;
		gbc_labelCamera.insets = new Insets(0, 0, 5, 5);
		gbc_labelCamera.gridx = 3;
		gbc_labelCamera.gridy = 0;
		panel.add(labelCamera, gbc_labelCamera);

		textFieldCamera = new JTextField("calice2");
		GridBagConstraints gbc_textFieldCamera = new GridBagConstraints();
		gbc_textFieldCamera.fill = GridBagConstraints.BOTH;
		gbc_textFieldCamera.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCamera.gridx = 4;
		gbc_textFieldCamera.gridy = 0;
		panel.add(textFieldCamera, gbc_textFieldCamera);

		labelIluminacao = new JLabel("Iluminacao:");
		labelIluminacao.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_labelIluminacao = new GridBagConstraints();
		gbc_labelIluminacao.fill = GridBagConstraints.BOTH;
		gbc_labelIluminacao.insets = new Insets(0, 0, 5, 5);
		gbc_labelIluminacao.gridx = 5;
		gbc_labelIluminacao.gridy = 0;
		panel.add(labelIluminacao, gbc_labelIluminacao);

		textFieldIluminacao = new JTextField("iluminacao");
		GridBagConstraints gbc_textFieldIluminacao = new GridBagConstraints();
		gbc_textFieldIluminacao.fill = GridBagConstraints.BOTH;
		gbc_textFieldIluminacao.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldIluminacao.gridx = 6;
		gbc_textFieldIluminacao.gridy = 0;
		panel.add(textFieldIluminacao, gbc_textFieldIluminacao);

		button = new JButton("Gerar imagem");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridwidth = 2;
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 7;
		gbc_button.gridy = 0;
		panel.add(button, gbc_button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					readFiles();
					setup();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		zoomIn = new JButton("");
		GridBagConstraints gbc_zoomIn = new GridBagConstraints();
		gbc_zoomIn.fill = GridBagConstraints.BOTH;
		gbc_zoomIn.insets = new Insets(0, 0, 0, 5);
		gbc_zoomIn.gridx = 1;
		gbc_zoomIn.gridy = 1;
		panel.add(zoomIn, gbc_zoomIn);
		zoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Camera.d+=0.1;
				setup();
			}
		});

		zoomOut = new JButton();
		GridBagConstraints gbc_zoomOut = new GridBagConstraints();
		gbc_zoomOut.fill = GridBagConstraints.BOTH;
		gbc_zoomOut.insets = new Insets(0, 0, 0, 5);
		gbc_zoomOut.gridx = 2;
		gbc_zoomOut.gridy = 1;
		panel.add(zoomOut, gbc_zoomOut);
		zoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Camera.d-=0.1;
				setup();
			}
		});

		rotateX = new JButton();
		GridBagConstraints gbc_rotateX = new GridBagConstraints();
		gbc_rotateX.fill = GridBagConstraints.BOTH;
		gbc_rotateX.insets = new Insets(0, 0, 0, 5);
		gbc_rotateX.gridx = 3;
		gbc_rotateX.gridy = 1;
		panel.add(rotateX, gbc_rotateX);
		rotateX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Camera.rotateX(degrees);
				setup();
			}
		});

		rotateX2 = new JButton();
		GridBagConstraints gbc_rotateX2 = new GridBagConstraints();
		gbc_rotateX2.fill = GridBagConstraints.BOTH;
		gbc_rotateX2.insets = new Insets(0, 0, 0, 5);
		gbc_rotateX2.gridx = 4;
		gbc_rotateX2.gridy = 1;
		panel.add(rotateX2, gbc_rotateX2);
		rotateX2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Camera.rotateX(-degrees);
				setup();
			}
		});

		rotateY = new JButton();
		GridBagConstraints gbc_rotateY = new GridBagConstraints();
		gbc_rotateY.fill = GridBagConstraints.BOTH;
		gbc_rotateY.insets = new Insets(0, 0, 0, 5);
		gbc_rotateY.gridx = 5;
		gbc_rotateY.gridy = 1;
		panel.add(rotateY, gbc_rotateY);
		rotateY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Camera.rotateY(degrees);
				setup();
			}
		});

		rotateY2 = new JButton();
		GridBagConstraints gbc_rotateY2 = new GridBagConstraints();
		gbc_rotateY2.fill = GridBagConstraints.BOTH;
		gbc_rotateY2.insets = new Insets(0, 0, 0, 5);
		gbc_rotateY2.gridx = 6;
		gbc_rotateY2.gridy = 1;
		panel.add(rotateY2, gbc_rotateY2);
		rotateY2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Camera.rotateY(-degrees);
				setup();
			}
		});

		rotateZ = new JButton();
		GridBagConstraints gbc_rotateZ = new GridBagConstraints();
		gbc_rotateZ.fill = GridBagConstraints.BOTH;
		gbc_rotateZ.insets = new Insets(0, 0, 0, 5);
		gbc_rotateZ.gridx = 7;
		gbc_rotateZ.gridy = 1;
		panel.add(rotateZ, gbc_rotateZ);
		rotateZ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Camera.rotateZ(degrees);
				setup();
			}
		});

		rotateZ2 = new JButton();
		GridBagConstraints gbc_rotateZ2 = new GridBagConstraints();
		gbc_rotateZ2.insets = new Insets(0, 0, 0, 5);
		gbc_rotateZ2.fill = GridBagConstraints.BOTH;
		gbc_rotateZ2.gridx = 8;
		gbc_rotateZ2.gridy = 1;
		panel.add(rotateZ2, gbc_rotateZ2);
		rotateZ2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Camera.rotateZ(-degrees);
				setup();
			}
		});

		//Cria-se uma Janela para o objeto apresentado por Gouraud e 
		//Outra para Phong.
		gouraud = new Gouraud(0, 100, ResX, ResY);
		gouraud.setVisible(true);
		phong = new Phong(ResX + 100, 100, ResX, ResY);
		phong.setVisible(true);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	public static void main(String[] args) {
		NewMain frame = new NewMain();
		frame.setVisible(true);
	}

	public void setup(){
		tempo = System.nanoTime();
		Camera.setCamera();
		Camera.convertObject(ResX, ResY);

		Iluminacao.setIluminacao();

		Camera.setIntervalos();
		System.out.printf("Demorou %f segundos calcular as variaveis necessárias\n",(System.nanoTime() - tempo)/1000000000.0);

		tempo = System.nanoTime();
		gouraud.scanLine3D();
		gouraud.repaint();
		System.out.printf("Demorou %f segundos para calcular as cores e pintar em Gouraud\n",(System.nanoTime() - tempo)/1000000000.0);

		tempo = System.nanoTime();
		phong.scanLine3D();
		phong.repaint();
		System.out.printf("Demorou %f segundos para calcular as cores e pintar em Phong\n",(System.nanoTime() - tempo)/1000000000.0);
		System.out.println();
	}

	public void readFiles() throws IOException{
		tempo = System.nanoTime(); 
		String cameraName = textFieldCamera.getText();
		Camera.initCamera("Entradas/Cameras/"+cameraName+".cfg");

		String objectName = textFieldObjeto.getText();
		Objeto.setObjeto("Entradas/Objetos/"+objectName+".byu");

		String iluminacaoName = textFieldIluminacao.getText();
		Iluminacao.initIluminacao("Entradas/" + iluminacaoName + ".txt");
		System.out.printf("Demorou %f segundos para ler e inicializar\n",(System.nanoTime() - tempo)/1000000000.0);
	}

}
