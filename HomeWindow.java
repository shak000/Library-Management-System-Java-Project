import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class HomeWindow extends JFrame implements  ActionListener
{
	JLabel backGround, ebackGround, sbackGround;
	JButton btnE, btnS, btnExit;
	ImageIcon img, eimg, simg, exitimg, limg, btne, btns;
	JPanel panel;
	
	public HomeWindow()
	{
		super("Library System Home Window");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		this.setSize(650, 488);
		this.setLocation(300,150);
		this.setResizable (false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel ();
		panel.setLayout (null);
		
		img = new ImageIcon ("home.jpg");
		eimg = new ImageIcon ("rsz_1employee.png");
		simg = new ImageIcon ("student.png");
		exitimg = new ImageIcon ("exit.png");
		btne = new ImageIcon ("btnE.png");
		btns = new ImageIcon ("btnS.png");
		
		btnE = new JButton("E-Login",btne);
		btnE.setBounds (160, 280, 120, 30);
		btnE.addActionListener(this);
		panel.add(btnE);
		
		ebackGround = new JLabel (eimg);
		ebackGround.setBounds (140,140,144,151);
		panel.add (ebackGround);
		
		sbackGround = new JLabel (simg);
		sbackGround.setBounds (370,125,144,151);
		panel.add (sbackGround);

		btnS = new JButton("S-Login",btns);
		btnS.setBounds (390,280,120,30);
		btnS.addActionListener(this);
		panel.add(btnS);
		
		
		
		btnExit = new JButton ("Exit", exitimg);
		btnExit.setBounds (280,380,100,30);
		btnExit.addActionListener (this);
		panel.add (btnExit);
		
		backGround = new JLabel (img,JLabel.CENTER);
		backGround.setBounds (0,0,650,488);
		panel.add(backGround);
		
		this.add(panel);
	}
	
		
	public void actionPerformed(ActionEvent ae)
	{
		String str = ae.getActionCommand();
		if(str.equals(btnE.getText()))
		{
			EmployeeLogin el = new EmployeeLogin (this);
			el.setVisible(true);
			this.setVisible(false);
		}
		else if(str.equals(btnS.getText()))
		{
			StudentLogin sl = new StudentLogin (this);
			sl.setVisible (true);
			this.setVisible (false);
		}
		else if (str.equals(btnExit.getText()))
		{
			System.exit (0);
		}
	}
	


}





