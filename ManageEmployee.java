import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class ManageEmployee extends JFrame implements ActionListener
{
	JLabel welcomeLabel, ebackGround;
	JButton addEmployeeBtn, viewEmployeeBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	ImageIcon img, limg, lbtnImg, addImg, viewImg;
	Font pixelPowerline;
	
	public ManageEmployee(String userId)
	{
		super("Manage Employee");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		this.userId = userId;
		this.setSize(600,410);
		this.setLocation(300,150);
		this.setResizable (false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try
		{
			pixelPowerline = Font.createFont(Font.TRUETYPE_FONT, new File("pixelPowerline.ttf")).deriveFont(15f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont (Font.createFont(Font.TRUETYPE_FONT, new File ("pixelPowerline.ttf")));
		}
		catch(IOException | FontFormatException e){}
		
		panel = new JPanel();
		panel.setLayout(null);
		
		img = new ImageIcon ("elogin.jpg");
		lbtnImg = new ImageIcon ("rsz_logout.png");
		addImg = new ImageIcon ("rsz_addemp.png");
		viewImg = new ImageIcon ("rsz_viewemp.png");
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 50, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		welcomeLabel.setForeground (Color.WHITE);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(550, 55, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		addEmployeeBtn = new JButton("Add Employee", addImg);
		addEmployeeBtn.setBounds(200, 150, 170, 55);
		addEmployeeBtn.addActionListener(this);
		panel.add(addEmployeeBtn);
		
		viewEmployeeBtn = new JButton("View Employee", viewImg);
		viewEmployeeBtn.setBounds(200, 230, 170, 55);
		viewEmployeeBtn.addActionListener(this);
		panel.add(viewEmployeeBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		ebackGround = new JLabel (img,JLabel.CENTER);
		ebackGround.setBounds (0, 0, 600, 400);
		panel.add (ebackGround);
			
		this.add(panel);
	}
	
	public void actionPerformed (ActionEvent ae)
	{
		String text = ae.getActionCommand ();
		
		if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		
		else if (text.equals(backBtn.getText()))
		{
			EmployeeHome eh = new EmployeeHome (userId);
			eh.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals(addEmployeeBtn.getText()))
		{
			AddEmployee ade = new AddEmployee (userId);
			ade.setVisible (true);
			this.setVisible (false);
		}	
		else if (text.equals(viewEmployeeBtn.getText()))
		{
			ViewEmployee ve = new ViewEmployee (userId);
			ve.setVisible (true);
			ve.information();
			this.setVisible (false);
		}
	}
}	