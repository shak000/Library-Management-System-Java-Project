import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class EmployeeHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel, ebackGround;
	JButton manageEmployeeBtn, changePasswordBtn, viewDetailsBtn, logoutBtn, studentDetailsBtn, bookManageBtn, borrowRequestBtn, returnBookBtn;
	JPanel panel;
	String userId;
	//EmployeeLogin el;
	Font pixelPowerline;
	ImageIcon img, mbtnImg, ibtnImg, cbtnImg, lbtnImg, limg, simg, bimg, borimg, rimg;
	
	public EmployeeHome(String userId)
	{
		super("Employee Home");
		
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
		mbtnImg = new ImageIcon ("rsz_manage.png");
		ibtnImg = new ImageIcon ("rsz_information.png");
		cbtnImg = new ImageIcon ("rsz_change.png");
		lbtnImg = new ImageIcon ("rsz_logout.png");
		simg = new ImageIcon ("rsz_student-information-system.png");
		bimg = new ImageIcon ("rsz_book.png");
		borimg = new ImageIcon ("rsz_borrow.png");
		rimg = new ImageIcon ("rsz_return.png");
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 50, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		welcomeLabel.setForeground (Color.WHITE);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(550, 55, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		manageEmployeeBtn = new JButton("Manage Employee",mbtnImg);
		manageEmployeeBtn.setBounds(30, 90, 170, 35);
		manageEmployeeBtn.addActionListener(this);
		panel.add(manageEmployeeBtn);
		
		bookManageBtn = new JButton ("Manage Book",bimg);
		bookManageBtn.setBounds (30, 130, 170, 35);
		bookManageBtn.addActionListener (this);
		panel.add (bookManageBtn);
		
		studentDetailsBtn = new JButton ("Student Details",simg);
		studentDetailsBtn.setBounds (30, 170, 170, 35);
		studentDetailsBtn.addActionListener (this);
		panel.add (studentDetailsBtn);
		
		
		viewDetailsBtn = new JButton("My Information",ibtnImg);
		viewDetailsBtn.setBounds(30, 210, 170, 35);
		viewDetailsBtn.addActionListener(this);
		panel.add(viewDetailsBtn);
		
		borrowRequestBtn = new JButton ("    Issue Book",borimg);
		borrowRequestBtn.setBounds (30, 250, 170, 35);
		borrowRequestBtn.addActionListener (this);
		panel.add (borrowRequestBtn);
		
		returnBookBtn = new JButton ("   Return Book",rimg);
		returnBookBtn.setBounds (30, 290, 170, 35);
		returnBookBtn.addActionListener(this);
		panel.add (returnBookBtn);
		
		changePasswordBtn = new JButton("Change Password",cbtnImg);
		changePasswordBtn.setBounds(30, 330, 170, 35);
		changePasswordBtn.addActionListener(this);
		panel.add(changePasswordBtn);
		
		
		ebackGround = new JLabel (img,JLabel.CENTER);
		ebackGround.setBounds (0, 0, 600, 400);
		panel.add (ebackGround);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand ();
		
		if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		else if (text.equals(manageEmployeeBtn.getText()))
		{
		
			ManageEmployee me = new ManageEmployee (userId);
			me.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals(bookManageBtn.getText()))
		{
			ManageBook mb = new ManageBook (userId);
			mb.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals(viewDetailsBtn.getText()))
		{
			EmployeeInformation ei = new EmployeeInformation (userId);
			ei.setVisible (true);
			ei.information ();
			this.setVisible (false);
			
		}
		else if (text.equals(changePasswordBtn.getText()))
		{
			ChangePassword cp = new ChangePassword (userId);
			cp.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals(studentDetailsBtn.getText()))
		{
			StudentDetails sd = new StudentDetails (userId);
			sd.setVisible (true);
			sd.information ();
			this.setVisible (false);
 		}
		else if (text.equals(borrowRequestBtn.getText()))
		{
			IssueBook ib = new IssueBook (userId);
			ib.setVisible (true);
			ib.information ();
			this.setVisible(false);
		}
		
		else if (text.equals (returnBookBtn.getText()))
		{
			ReturnBook rb = new ReturnBook (userId);
			rb.setVisible (true);
			this.setVisible (false);
		}
		
	}
}