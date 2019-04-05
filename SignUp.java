import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class SignUp extends JFrame implements ActionListener
{
	JLabel  ebackGround, userLabel, passLabel, sNameLabel, phoneLabel, addressLabel, confirmPassword;
	JTextField userTF, phoneTF1, phoneTF2, sNameTF, addressTF;
	JPasswordField passTF, confirmPassTF;
	JButton addBtn, exitBtn, backBtn;
	JPanel panel;
	ImageIcon img, limg;
	Font font;
	StudentLogin sl;
	
	public SignUp(StudentLogin sl)
	{
		super("Sign-Up");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		this.setSize(600,450);
		this.setLocation(300,150);
		this.setResizable (false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.sl = sl;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		
		img = new ImageIcon ("uni.jpg");
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(100, 80, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(230, 80, 120, 30);
		panel.add(userTF);
		
		sNameLabel = new JLabel("Student Name : ");
		sNameLabel.setBounds(100, 130, 120, 30);
		panel.add(sNameLabel);
		
		sNameTF = new JTextField();
		sNameTF.setBounds(230, 130, 120, 30);
		panel.add(sNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(100, 180, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF1 = new JTextField("+880");
		phoneTF1.setBounds(230, 180, 35, 30);
		phoneTF1.setEnabled(false);
		panel.add(phoneTF1);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(265, 180, 85, 30);
		panel.add(phoneTF2);
		
		passLabel = new JLabel("Choose Password : ");
		passLabel.setBounds(100, 230, 120, 30);
		panel.add(passLabel);
		
		
		passTF = new JPasswordField();
		passTF.setBounds(230, 230, 120, 30);
		panel.add(passTF);
		
		confirmPassword = new JLabel ("Confirm Password");
		confirmPassword.setBounds (100, 280, 120, 30);
		panel.add(confirmPassword);
		
		confirmPassTF = new JPasswordField ();
		confirmPassTF.setBounds (230, 280, 120, 30);
		panel.add (confirmPassTF);
		
		addressLabel = new JLabel("Address : ");
		addressLabel.setBounds(100, 330, 120, 30);
		panel.add(addressLabel);
		
		addressTF = new JTextField();
		addressTF.setBounds(230, 330, 120, 30);
		panel.add(addressTF);
		
		addBtn = new JButton("Sign-up");
		addBtn.setBounds(180, 380, 100, 30);
		addBtn.addActionListener(this);
		panel.add(addBtn);
		
		exitBtn = new JButton ("Exit");
		exitBtn.setBounds (300, 380, 100, 30);
		exitBtn.addActionListener (this);
		panel.add (exitBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		ebackGround = new JLabel (img,JLabel.CENTER);
		ebackGround.setBounds (0, 0, 600, 450);
		panel.add (ebackGround);
		
		
		this.add (panel);
	}	
	
	public void actionPerformed (ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if (text.equals (backBtn.getText()))
		{
			//StudentLogin sl = new StudentLogin ();
			sl.setVisible (true);
			this.setVisible (false);
		}
		
		else if (text.equals(exitBtn.getText()))
		{
			System.exit (0);
		}
		
		else if (text.equals(addBtn.getText()))
		{
			insertIntoDB();
		}
	}
	
	public void insertIntoDB()
	{
		String newId = userTF.getText();
		String newPass = passTF.getText();
		String newConfirmPass = confirmPassTF.getText ();
		String sName = sNameTF.getText();
		String phnNo = phoneTF1.getText()+phoneTF2.getText();
		String address = addressTF.getText();
		int status = 1;
		
		
		if (userTF.getText().trim().isEmpty() && passTF.getText().trim().isEmpty() && sNameTF.getText().trim().isEmpty() && phoneTF2.getText().trim().isEmpty() && addressTF.getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog (this, "All Information Required");
		}
		else if (newPass.equals(confirmPassTF.getText()))
		{
		String query1 = "INSERT INTO Customer VALUES ('"+newId+"','"+sName+"','"+ phnNo+"','"+address+"');";
		String query2 = "INSERT INTO Login VALUES ('"+newId+"','"+newPass+"',"+status+");";
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
			
			userTF.setText ("");
			passTF.setText ("");
			sNameTF.setText ("");
			phoneTF2.setText ("");
			addressTF.setText ("");
			confirmPassTF.setText ("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Already Exist User  !!!");
        }
		}
		
		else 
		{
			JOptionPane.showMessageDialog(this, "incorrect Password !!!");
		}
		
    }	
}