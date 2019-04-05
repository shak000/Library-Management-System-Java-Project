import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.io.*;

public class AddEmployee extends JFrame implements ActionListener
{
	JLabel welcomeLabel, ebackGround, userLabel, passLabel, eNameLabel, phoneLabel, roleLabel, salaryLabel;
	JTextField userTF, passTF, phoneTF1, phoneTF2, eNameTF, salaryTF;
	JComboBox roleCombo;
	JButton autoPassBtn, addBtn, backBtn, logoutBtn;
	JPanel panel;
	ImageIcon img, limg, lbtnImg;
	Font pixelPowerline;
	
	String userId;
	
	public AddEmployee(String userId)
	{
		super("Add Employee");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		this.userId = userId;
		this.setSize(600,450);
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
		
		lbtnImg = new ImageIcon ("rsz_logout.png");
		img = new ImageIcon ("uni.jpg");
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 40, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(550, 45, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(100, 80, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(200, 80, 120, 30);
		panel.add(userTF);
		
		passLabel = new JLabel("Password : ");
		passLabel.setBounds(100, 130, 120, 30);
		panel.add(passLabel);
		
		passTF = new JTextField();
		passTF.setBounds(200, 130, 120, 30);
		passTF.setEnabled(false);
		panel.add(passTF);
		
		autoPassBtn = new JButton("Generate");
		autoPassBtn.setBounds(350, 130, 100, 30);
		autoPassBtn.addActionListener(this);
		panel.add(autoPassBtn);
		
		eNameLabel = new JLabel("Employee Name : ");
		eNameLabel.setBounds(100, 180, 120, 30);
		panel.add(eNameLabel);
		
		eNameTF = new JTextField();
		eNameTF.setBounds(200, 180, 120, 30);
		panel.add(eNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(100, 230, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF1 = new JTextField("+880");
		phoneTF1.setBounds(200, 230, 35, 30);
		phoneTF1.setEnabled(false);
		panel.add(phoneTF1);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(235, 230, 85, 30);
		panel.add(phoneTF2);
		
		roleLabel = new JLabel("Role : ");
		roleLabel.setBounds(100, 280, 120, 30);
		panel.add(roleLabel);
		
		String []items = {"Manager", "General"};
		roleCombo = new JComboBox(items);
		roleCombo.setBounds(200, 280, 120, 30);
		panel.add(roleCombo);
		
		salaryLabel = new JLabel("Salary : ");
		salaryLabel.setBounds(100, 330, 120, 30);
		panel.add(salaryLabel);
		
		salaryTF = new JTextField();
		salaryTF.setBounds(200, 330, 120, 30);
		panel.add(salaryTF);
		
		addBtn = new JButton("Add");
		addBtn.setBounds(250, 380, 100, 30);
		addBtn.addActionListener(this);
		panel.add(addBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		ebackGround = new JLabel (img,JLabel.CENTER);
		ebackGround.setBounds (0, 0, 600, 450);
		panel.add (ebackGround);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			ManageEmployee me = new ManageEmployee(userId);
			me.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(autoPassBtn.getText()))
		{
			Random r = new Random();
			passTF.setText(r.nextInt(89999999)+10000000+"");
			autoPassBtn.setEnabled(false);
		}
		else if(text.equals(addBtn.getText()))
		{
			insertIntoDB();
			
		}
		else{}
	}
	public void insertIntoDB()
	{
		String newId = userTF.getText();
		String newPass = passTF.getText();
		String eName = eNameTF.getText();
		String phnNo = phoneTF1.getText()+phoneTF2.getText();
		String role = roleCombo.getSelectedItem().toString();
		double salary = Double.parseDouble(salaryTF.getText());
		int status = 0;
		
		
		String query1 = "INSERT INTO Employee VALUES ('"+newId+"','"+eName+"','"+ phnNo+"','"+role+"',"+salary+");";
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
			eNameTF.setText ("");
			phoneTF2.setText ("");
			salaryTF.setText ("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "User Name Already Exist !!!");
        }
    }	
}