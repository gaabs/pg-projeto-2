package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class guiPhong extends JFrame{

	private static JPanel contentPane;
	
	public guiPhong(double hx, double hy){
		
		super("Phong");
		int x =  (int) (2*hx);
		int y=(int) (2*hy);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, x, y);
		setBounds(100, 100, 432, 421);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Phong();
	}
	
	private static void Phong(){
		
	}

}
