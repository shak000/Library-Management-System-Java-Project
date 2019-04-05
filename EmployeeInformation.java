import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;


public class EmployeeInformation extends JFrame implements ActionListener
{
	JLabel welcomeLabel, userLabel, eNameLabel, phoneLabel, roleLabel, salaryLabel, ebackGround;
	JTextField userTF, phoneTF, eNameTF, salaryTF, roleTF;
	JButton backBtn, logoutBtn;
	JPanel panel;
	ImageIcon img, limg, lbtnImg;
	String userId;
	Font pixelPowerline, font;

	public EmployeeInformation(String userId)
	{
		super("My Information");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		this.userId = userId;
		this.setSize(600,415);
		this.setLocation(300,150);
		this.setResizable (false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		try
		{
			pixelPowerline = Font.createFont(Font.TRUETYPE_FONT, new File("pixelPowerline.ttf")).deriveFont(15f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont (Font.createFont(Font.TRUETYPE_FONT, new File ("pixelPowerline.ttf")));
		}
		catch(IOException | FontFormatException e){}
		
		panel = new JPanel();
		panel.setLayout(null);
		
		img = new ImageIcon ("uni.jpg");
		lbtnImg = new ImageIcon ("rsz_logout.png");
		font = new Font ("Arial", Font.BOLD, 12);
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 40, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(550, 45, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(150, 90, 120, 30);
		userLabel.setFont (font);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(250, 90, 120, 30);
		userTF.setEditable (false);
		panel.add(userTF);
		
		
		
		eNameLabel = new JLabel("Employee Name : ");
		eNameLabel.setBounds(150, 140, 120, 30);
		eNameLabel.setFont (font);
		panel.add(eNameLabel);
		
		eNameTF = new JTextField();
		eNameTF.setBounds(250, 140, 120, 30);
		eNameTF.setEditable (false);
		panel.add(eNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(150, 190, 120, 30);
		phoneLabel.setFont (font);
		panel.add(phoneLabel);
		
		
		phoneTF = new JTextField();
		phoneTF.setBounds(250, 190, 120, 30);
		phoneTF.setEditable(false);
		panel.add(phoneTF);
		
		roleLabel = new JLabel("Role : ");
		roleLabel.setBounds(150, 240, 120, 30);
		roleLabel.setFont (font);
		panel.add(roleLabel);
		
		
		roleTF = new JTextField();
		roleTF.setBounds(250, 240, 120, 30);
		roleTF.setEditable(false);
		panel.add(roleTF);
		
		salaryLabel = new JLabel("Salary : ");
		salaryLabel.setBounds(150, 290, 120, 30);
		salaryLabel.setFont (font);
		panel.add(salaryLabel);
		
		salaryTF = new JTextField();
		salaryTF.setBounds(250, 290, 120, 30);
		salaryTF.setEditable(false);
		panel.add(salaryTF);
		
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		

		
		ebackGround = new JLabel (img,JLabel.CENTER);
		ebackGround.setBounds (0, 0, 600, 400);
		panel.add (ebackGround);
		
		this.add(panel);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();

		if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(backBtn.getText()))
		{
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
	}
	public void information ()
	{
		
        String query = "SELECT `userId`, `employeeName`, `phoneNumber`, `role`,`salary` FROM `employee`WHERE `userId`='"+userId+"';";     
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
						
				while(rs.next())
				{
	                String userId = rs.getString("userID");
	               

					String employeeName = rs.getString("employeeName");
					String phoneNumber = rs.getString("PhoneNumber");
					String role=rs.getString("Role");
					double salary = rs.getDouble("Salary");

					userTF.setText(""+userId);
					phoneTF.setText(""+phoneNumber);
					eNameTF.setText(""+employeeName);
					roleTF.setText(""+role);
					salaryTF.setText(""+salary);
					
					System.out.println("User: " +userId);
					System.out.println("Name: " +employeeName);
					System.out.println("PhoneNumber: " +phoneNumber);
					System.out.println("Salary: " +salary);
					System.out.println("Role: " +role);
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