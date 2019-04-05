import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class ManageBook extends JFrame implements ActionListener
{
	JLabel welcomeLabel, ebackGround;
	JButton addBookBtn, viewBookBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	ImageIcon img, limg, lbtnImg, addImg, viewImg;
	Font pixelPowerline;
	
	public ManageBook(String userId)
	{
		super("Manage Book");
		
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
		addImg = new ImageIcon ("rsz_addbook.png");
		viewImg = new ImageIcon ("rsz_viewbook.png");
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 50, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		welcomeLabel.setForeground (Color.WHITE);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(550, 55, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		addBookBtn = new JButton("Add Book", addImg);
		addBookBtn.setBounds(200, 150, 170, 55);
		addBookBtn.addActionListener(this);
		panel.add(addBookBtn);
		
		viewBookBtn = new JButton("View Book", viewImg);
		viewBookBtn.setBounds(200, 230, 170, 55);
		viewBookBtn.addActionListener(this);
		panel.add(viewBookBtn);
		
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
		else if (text.equals(addBookBtn.getText()))
		{
			AddBook adb = new AddBook (userId);
			adb.setVisible (true);
			this.setVisible (false);
		}	
		else if (text.equals(viewBookBtn.getText()))
		{
			ViewBook vb = new ViewBook (userId);
			vb.setVisible (true);
			vb.information();
			this.setVisible (false);
		}
	}
}	