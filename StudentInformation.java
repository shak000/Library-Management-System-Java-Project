import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;


public class StudentInformation extends JFrame implements ActionListener
{
	JLabel welcomeLabel, userLabel, sNameLabel, phoneLabel, addressLabel, ebackGround;
	JTextField userTF, phoneTF, sNameTF, addressTF;
	JButton backBtn, logoutBtn, updateBtn, confirmBtn;
	JPanel panel;
	ImageIcon img, limg, lbtnImg;
	String userId;
	Font pixelPowerline, font;

	public StudentInformation(String userId)
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
		
		
		
		sNameLabel = new JLabel("Student Name : ");
		sNameLabel.setBounds(150, 140, 120, 30);
		sNameLabel.setFont (font);
		panel.add(sNameLabel);
		
		sNameTF = new JTextField();
		sNameTF.setBounds(250, 140, 120, 30);
		sNameTF.setEditable (false);
		panel.add(sNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(150, 190, 120, 30);
		phoneLabel.setFont (font);
		panel.add(phoneLabel);
		
		
		phoneTF = new JTextField();
		phoneTF.setBounds(250, 190, 120, 30);
		phoneTF.setEditable(false);
		panel.add(phoneTF);
		
		addressLabel = new JLabel("Address : ");
		addressLabel.setBounds(150, 240, 120, 30);
		addressLabel.setFont (font);
		panel.add(addressLabel);
		
		
		addressTF = new JTextField();
		addressTF.setBounds(250, 240, 120, 30);
		addressTF.setEditable(false);
		panel.add(addressTF);
		
		updateBtn = new JButton ("Update");
		updateBtn.setBounds (180, 300, 100, 30);
		updateBtn.addActionListener (this);
		panel.add (updateBtn);
		
		confirmBtn = new JButton ("Confirm");
		confirmBtn.setBounds (300, 300, 100,30);
		confirmBtn.setEnabled (false);
		confirmBtn.addActionListener (this);
		panel.add (confirmBtn);
		
		
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
			StudentHome sh = new StudentHome(userId);
			sh.setVisible(true);
			this.setVisible(false);
		}
		else if (text.equals(updateBtn.getText()))
		{
			sNameTF.setEditable (true);
			phoneTF.setEditable(true);
			addressTF.setEditable(true);
			confirmBtn.setEnabled (true);
			updateBtn.setEnabled (false);
		}
		
		else if (text.equals (confirmBtn.getText()))
		{
			updateInDB ();
			updateBtn.setEnabled (true);
			sNameTF.setEditable (false);
			phoneTF.setEditable(false);
			addressTF.setEditable(false);
			confirmBtn.setEnabled (false);
			
		}
	}
	
	public void updateInDB()
	{
		
		String sName = sNameTF.getText();
		String phnNo = phoneTF.getText();
		String address = addressTF.getText();
		
		String query = "UPDATE customer SET customerNamer ='"+sName+"', phoneNumber = '"+phnNo+"', address = '"+address+"' WHERE userId='"+userId+"'";	
        Connection con=null;//for connection
        Statement st = null;//for query execution
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3","root","");
			st = con.createStatement();//create statement
			st.executeUpdate(query);
			st.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	public void information ()
	{
		
        String query = "SELECT `userId`, `customerNamer`, `phoneNumber`, `address` FROM `customer`WHERE `userId`='"+userId+"';";     
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
	               

					String customerNamer = rs.getString("CustomerNamer");
					String phoneNumber = rs.getString("PhoneNumber");
					String address = rs.getString("Address");
					//double salary = rs.getDouble("Salary");

					userTF.setText(""+userId);
					phoneTF.setText(""+phoneNumber);
					sNameTF.setText(""+customerNamer);
					addressTF.setText(""+address);
					//salaryTF.setText(""+salary);
					
					System.out.println("User: " +userId);
					System.out.println("Name: " +customerNamer);
					System.out.println("PhoneNumber: " +phoneNumber);
					System.out.println("Address: " +address);
					//System.out.println("Role: " +role);
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