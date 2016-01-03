package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class guiGouraud extends JFrame{

	private static JPanel contentPane;
	
	public guiGouraud(double hx, double hy){
		
		super("Gouraud");
		int x =  (int) (2*hx);
		int y=(int) (2*hy);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(800, 100, x, y);
		setBounds(800, 100, 432, 421);

		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Gouraud();
	}
	
	private static void Gouraud(){
		
	}

}
