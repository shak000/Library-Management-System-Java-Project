import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class StudentHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel, ebackGround;
	JButton viewBookBtn, changePasswordBtn, viewDetailsBtn, logoutBtn, deleteAccountBtn, borrowBookBtn;
	JPanel panel;
	String userId;
	//EmployeeLogin el;
	Font pixelPowerline;
	ImageIcon img, mbtnImg, ibtnImg, cbtnImg, lbtnImg, limg, simg, bimg;
	
	public 	StudentHome(String userId)
	{
		super("Student Home");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		this.userId = userId;
		//this.el = el;
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
		mbtnImg = new ImageIcon ("rsz_viewbook.png");
		ibtnImg = new ImageIcon ("rsz_information.png");
		cbtnImg = new ImageIcon ("rsz_change.png");
		lbtnImg = new ImageIcon ("rsz_logout.png");
		simg = new ImageIcon ("rsz_deletesign.png");
		bimg = new ImageIcon ("rsz_book.png");
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 50, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		welcomeLabel.setForeground (Color.WHITE);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(550, 55, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		viewBookBtn = new JButton("View Book",mbtnImg);
		viewBookBtn.setBounds(30, 90, 170, 35);
		viewBookBtn.addActionListener(this);
		panel.add(viewBookBtn);
		
		borrowBookBtn = new JButton ("Borrow Info",bimg);
		borrowBookBtn.setBounds (30, 130, 170, 35);
		borrowBookBtn.addActionListener (this);
		panel.add (borrowBookBtn);
		
		viewDetailsBtn = new JButton ("My Information",ibtnImg);
		viewDetailsBtn.setBounds (30, 170, 170, 35);
		viewDetailsBtn.addActionListener (this);
		panel.add (viewDetailsBtn);
		
		
		changePasswordBtn = new JButton("Change Password",cbtnImg);
		changePasswordBtn.setBounds(30, 210, 170, 35);
		changePasswordBtn.addActionListener(this);
		panel.add(changePasswordBtn);
		
		deleteAccountBtn = new JButton("Delete Account",simg);
		deleteAccountBtn.setBounds(30, 250, 170, 35);
		deleteAccountBtn.addActionListener(this);
		panel.add(deleteAccountBtn);
		
		ebackGround = new JLabel (img,JLabel.CENTER);
		ebackGround.setBounds (0, 0, 600, 400);
		panel.add (ebackGround);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand ();
		
		if (text.equals(deleteAccountBtn.getText()))
		{
			
			int yesOrNo = JOptionPane.showConfirmDialog(null, "Are You Sure ??", "Confirm", JOptionPane.YES_NO_OPTION);
			if (yesOrNo == 0)
			{
				deleteFromDB();
				HomeWindow hw = new HomeWindow();
			    hw.setVisible(true);
			    this.setVisible(false);
			}
			else 
			{
				this.setVisible (true);
			}
		
			
		}
		else if (text.equals (logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		
		else if (text.equals(borrowBookBtn.getText()))
		{
			BorrowInfo bi = new BorrowInfo (userId);
			bi.setVisible (true);
			this.setVisible(false);
		}
			
		
		else if (text.equals(viewDetailsBtn.getText()))
		{
			StudentInformation si = new StudentInformation (userId);
			si.setVisible (true);
			si.information ();
			this.setVisible (false);
			
		}
		else if (text.equals(changePasswordBtn.getText()))
		{
			SchangePass scp = new SchangePass (userId);
			scp.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals (viewBookBtn.getText()))
		{
			SviewBook svb = new SviewBook (userId);
			svb.setVisible(true);
			svb.information ();
			this.setVisible (false);
		}
		
	}
	
	
	public void deleteFromDB()
	{
	
		String query1 = "DELETE from Customer WHERE userId='"+userId+"';";
		String query2 = "DELETE from login WHERE userId='"+userId+"';";
		System.out.println(query1);
		System.out.println(query2);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}