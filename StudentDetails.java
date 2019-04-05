import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class StudentDetails extends JFrame implements ActionListener
{
	JLabel welcomeLabel, userIdLabel, studentNameLabel, phoneLabel, addressLabel, ebackGround, studentsInfo;
	JTextField userIdTF, studentNameTF, phoneTF, addressTF;
	JButton refreshBtn, loadBtn, backBtn, logoutBtn;
	JPanel panel;
	ImageIcon limg, lbtnImg, img;
	Font pixelPowerline;
	String userId;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	
	String [] columns = {"Student Id","Student Name","Phone","Address"};
	String [] rows = new String [4];
	
	public StudentDetails(String userId)
	{
		super("Student Details");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		this.userId = userId;
		this.setSize(680,620);
		this.setLocation(300,100);
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
		
		img = new ImageIcon ("uni.jpg");
		lbtnImg = new ImageIcon ("rsz_logout.png");
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 40, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(550, 45, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(210, 310, 140, 30);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		
		userIdLabel = new JLabel("Student ID : ");
		userIdLabel.setBounds(100, 100, 100, 30);
		panel.add(userIdLabel);
		
		userIdTF = new JTextField();
		userIdTF.setBounds(200, 100, 160, 30);
		panel.add(userIdTF);
		
		loadBtn = new JButton("Search");
		loadBtn.setBounds(400, 100, 120, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		studentNameLabel = new JLabel("Student Name : ");
		studentNameLabel.setBounds(100, 150, 120, 30);
		panel.add(studentNameLabel);
		
		studentNameTF = new JTextField();
		studentNameTF.setBounds(200, 150, 160, 30);
		panel.add(studentNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(100, 200, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF = new JTextField();
		phoneTF.setBounds(200, 200, 160, 30);
		panel.add(phoneTF);
		
		
		addressLabel = new JLabel("Address : ");
		addressLabel.setBounds(100, 250, 120, 30);
		panel.add(addressLabel);
		
		addressTF = new JTextField();
		addressTF.setBounds(200, 250, 160, 30);
		panel.add(addressTF);
		
		
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		studentsInfo = new JLabel ("All Students Info");
		studentsInfo.setBounds (250, 380, 200, 50);
		studentsInfo.setFont (pixelPowerline);
		panel.add (studentsInfo);
		
		table = new JTable ();
		
		model = new DefaultTableModel ();
		model.setColumnIdentifiers (columns);
		table.setModel (model);
		table.setRowHeight (20);
		
		scroll = new JScrollPane (table);
		scroll.setBounds (10,420,650,150);
		panel.add (scroll);
		
		ebackGround = new JLabel (img,JLabel.CENTER);
		ebackGround.setBounds (0, 0, 700, 620);
		panel.add (ebackGround);
		
		this.add(panel);
	}
	
	public void actionPerformed (ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			EmployeeHome mh = new EmployeeHome (userId);
			mh.setVisible(true);
			this.setVisible(false);
		}
		if(text.equals(refreshBtn.getText()))
		{
			userIdTF.setEnabled(true);
			userIdTF.setText("");
			studentNameTF.setText("");
			phoneTF.setText("");
			addressTF.setText("");
		}
		else if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(loadBtn.getText()))
		{
			loadFromDB();	
			userIdTF.setEnabled (false);
			studentNameTF.setEnabled (false);
			phoneTF.setEnabled (false);
			addressTF.setEnabled (false);
		}
		
	}
	
	public void loadFromDB()
	{
		String loadId = userIdTF.getText();
		String query = "SELECT `userId`, `customerNamer`, `phoneNumber`, `address` FROM `customer` WHERE `userId`='"+loadId+"';";     
        Connection con = null;//for connection
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
			String userId = null;
			String customerNamer = null;
			//String role = null;
			//double salary = 0.0;
			String phoneNumber = null;
			String address = null;
			while(rs.next())
			{
                userId = rs.getString("userId");
				customerNamer = rs.getString("customerNamer");
				phoneNumber = rs.getString("phoneNumber");
				address = rs.getString("address");
				flag=true;
				
				userIdTF.setText(""+userId);
				studentNameTF.setText(""+customerNamer);
				phoneTF.setText(""+phoneNumber);
				addressTF.setText(""+address);
				
			}
			if(!flag)
			{
				userIdTF.setText("");
				studentNameTF.setText("");
				phoneTF.setText("");
				addressTF.setText("");
				JOptionPane.showMessageDialog(this,"Invalid ID"); 
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
	
	

	
	
	
	public void information ()
	{
		
        String query = "SELECT `userId`, `customerNamer`, `phoneNumber`, `address` FROM `customer`";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		PreparedStatement pst = null;
		System.out.println(query);
		
		try
			{
				Class.forName("com.mysql.jdbc.Driver");//load driver
				System.out.println("driver loaded");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3","root","");
				System.out.println("connection done");//connection with database established
				st = con.createStatement();//create statement
				System.out.println("statement created");
				//rs = st.executeQuery(query);//getting result
				System.out.println("results received");
				
				
				pst = con.prepareStatement (query);	
				rs = pst.executeQuery ();
				
				String []columnData = new String [5];
				while(rs.next())
				{
					columnData [0] = rs.getString ("userId");
					columnData [1] = rs.getString ("customerNamer");
					columnData [2] = rs.getString ("phoneNumber");
					columnData [3] = rs.getString ("address");
					
					
					model.addRow (columnData);
					
	              
				}
			}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
	}	
	
	
}