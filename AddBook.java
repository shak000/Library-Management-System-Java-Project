import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class AddBook extends JFrame implements ActionListener
{
	JLabel welcomeLabel, ebackGround, bookId, bookName, authorName, publicationYear, availableQuantity;
	JTextField bookIdTF, bookNameTF, authorNameTF, publicationYearTF, availableQuantityTF;
	JButton addBtn, backBtn, logoutBtn;
	JPanel panel;
	ImageIcon img, limg, lbtnImg;
	Font pixelPowerline, font;
	
	String userId;
	
	public AddBook(String userId)
	{
		super("Add Book");
		
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
		
		img = new ImageIcon ("uni.jpg");
		lbtnImg = new ImageIcon ("rsz_logout.png");
		
		font = new Font ("Arial",Font.BOLD,12);
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 40, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(550, 45, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		bookId = new JLabel("Book ID : ");
		bookId.setBounds(150, 90, 120, 30);
		bookId.setFont (font);
		panel.add(bookId);
		
		bookIdTF = new JTextField();
		bookIdTF.setBounds(250, 90, 120, 30);
		panel.add(bookIdTF);
		
		bookName = new JLabel("Book Name : ");
		bookName.setBounds(150, 140, 120, 30);
		bookName.setFont(font);
		panel.add(bookName);
		
		bookNameTF = new JTextField();
		bookNameTF.setBounds(250, 140, 120, 30);
		panel.add(bookNameTF);
		
		
		authorName = new JLabel("Author Name : ");
		authorName.setBounds(150, 190, 120, 30);
		authorName.setFont (font);
		panel.add(authorName);
		
		authorNameTF = new JTextField();
		authorNameTF.setBounds(250, 190, 120, 30);
		panel.add(authorNameTF);
		
		publicationYear = new JLabel("Publication Year : ");
		publicationYear.setBounds(150, 240, 120, 30);
		publicationYear.setFont (font);
		panel.add(publicationYear);
		
		publicationYearTF = new JTextField();
		publicationYearTF.setBounds(250, 240, 120, 30);
		panel.add(publicationYearTF);
		
		
		
		availableQuantity = new JLabel("Quantity : ");
		availableQuantity.setBounds(150, 290, 120, 30);
		availableQuantity.setFont (font);
		panel.add(availableQuantity);
		
		availableQuantityTF = new JTextField();
		availableQuantityTF.setBounds(250, 290, 120, 30);
		panel.add(availableQuantityTF);
		
		addBtn = new JButton("Add");
		addBtn.setBounds(250, 340, 100, 30);
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
			ManageBook mb = new ManageBook (userId);
			mb.setVisible (true);
			this.setVisible (false);
		}
		else if(text.equals(addBtn.getText()))
		{
			insertIntoDB();
			
		}
		else{}
		
	}
	
	public void insertIntoDB ()
	{
		String newBookId = bookIdTF.getText();
		String newBookName = bookNameTF.getText();
		String newAuthorName = authorNameTF.getText();
		int newPublicationYear = Integer.parseInt(publicationYearTF.getText());
		int newAvailableQuantity = Integer.parseInt(availableQuantityTF.getText());
		
		
		
		String query = "INSERT INTO Book VALUES ('"+newBookId+"','"+newBookName+"','"+ newAuthorName+"','"+newPublicationYear+"',"+newAvailableQuantity+");";
		System.out.println (query);
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			bookIdTF.setText ("");
			bookNameTF.setText ("");
			authorNameTF.setText ("");
			publicationYearTF.setText ("");
			availableQuantityTF.setText ("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}