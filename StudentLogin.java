import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;


public class StudentLogin extends JFrame implements ActionListener
{
	JLabel title, userLabel, passLabel, sbackGround;
	JTextField userTF;
	JPasswordField passPF;
	JButton loginBtn, exitBtn, signupBtn, backBtn;
	JPanel panel;
	HomeWindow hw;
	ImageIcon img, limg;
	Font pixelPowerline;
	
	
	public StudentLogin(HomeWindow hw)
	{
		super("Student Login Window");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		
		this.hw=hw;
		this.setSize(600, 410);
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
		
		title = new JLabel("Welcome, Student Login-System.");
		title.setBounds(150, 40, 400, 30);
		title.setFont (pixelPowerline);
		title.setForeground (Color.WHITE);
		panel.add(title);
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(200, 100, 60, 30);
		userLabel.setForeground (Color.WHITE);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(270, 100, 100, 30);
		panel.add(userTF);
		
		passLabel = new JLabel("Password : ");
		passLabel.setBounds(200, 150, 70, 30);
		passLabel.setForeground (Color.WHITE);
		panel.add(passLabel);
		
		passPF = new JPasswordField();
		passPF.setBounds(270, 150, 100, 30);
		panel.add(passPF);
		
		loginBtn = new JButton("Login");
		loginBtn.setBounds(200, 200, 80, 30);
		loginBtn.addActionListener(this);
		panel.add(loginBtn);
		
		
		exitBtn = new JButton("Exit");
		exitBtn.setBounds(290, 200, 80, 30);
		exitBtn.addActionListener(this);
		panel.add(exitBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		signupBtn = new JButton ("Sign-Up");
		signupBtn.setBounds (235, 250, 100, 20);
		signupBtn.addActionListener (this);
		panel.add (signupBtn);
		
		sbackGround = new JLabel (img,JLabel.CENTER);
		sbackGround.setBounds (0, 0, 600, 400);
		panel.add (sbackGround);
		
		
		this.add(panel);
		
	}
	
	
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
	    if(text.equals(backBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
		    hw.setVisible(true);
			this.setVisible (false);
		}
		else if(text.equals(loginBtn.getText()))
		{
			checkLogin();
		}
		else if(text.equals(exitBtn.getText()))
		{
			System.exit(0);
		}
		else if (text.equals(signupBtn.getText()))
		{
			SignUp su = new SignUp (this);
			su.setVisible (true);
			this.setVisible (false);
		}
	}
	
	public void checkLogin()
	{
		String query = "SELECT `userId`, `password`, `status` FROM `login`;";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3","root","");
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			boolean flag = false;			
			while(rs.next())
			{
                String userId = rs.getString("userId");
                String password = rs.getString("password");
				int status = rs.getInt("status");
				
				if(userId.equals(userTF.getText()) && password.equals(passPF.getText()))
				{
					flag=true;
					if(status==1)
					{
						StudentHome sh = new StudentHome(userId);
						sh.setVisible(true);
						this.setVisible(false);
					}
					
					else
					{
						JOptionPane.showMessageDialog(this,"Opps!!! What are you doin here???");
						userTF.setText ("");
						passPF.setText ("");
					}
				}
			}
			if(!flag)
			{
				JOptionPane.showMessageDialog(this,"Invalid ID or Password"); 
			}
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
        finally
		{
            try
			{
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex){}
        }
	}
	
}