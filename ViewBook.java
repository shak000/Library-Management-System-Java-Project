import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class ViewBook extends JFrame implements ActionListener
{
	JLabel welcomeLabel, bookIdLabel, bookNameLabel, authorLabel, yearLabel, quantityLabel, ebackGround, availableBook;
	JTextField bookIdTF, authorTF, bookNameTF, yearTF, quantityTF;
	JButton refreshBtn, loadBtn, updateBtn, delBtn, backBtn, logoutBtn;
	JPanel panel;
	ImageIcon limg, lbtnImg, img;
	Font pixelPowerline;
	String userId;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	
	String [] columns = {"BookId","Book Title","Author Name","Publication Year","Quantity"};
	String [] rows = new String [5];
	
	public ViewBook(String userId)
	{
		super("View Book");
		
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
		refreshBtn.setBounds(400, 350, 120, 30);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		
		bookIdLabel = new JLabel("Book ID : ");
		bookIdLabel.setBounds(100, 100, 100, 30);
		panel.add(bookIdLabel);
		
		bookIdTF = new JTextField();
		bookIdTF.setBounds(200, 100, 140, 30);
		panel.add(bookIdTF);
		
		loadBtn = new JButton("Search");
		loadBtn.setBounds(400, 100, 120, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		bookNameLabel = new JLabel("Book Name : ");
		bookNameLabel.setBounds(100, 150, 120, 30);
		panel.add(bookNameLabel);
		
		bookNameTF = new JTextField();
		bookNameTF.setBounds(200, 150, 140, 30);
		panel.add(bookNameTF);
		
		authorLabel = new JLabel("Author Name : ");
		authorLabel.setBounds(100, 200, 120, 30);
		panel.add(authorLabel);
		
		authorTF = new JTextField();
		authorTF.setBounds(200, 200, 140, 30);
		panel.add(authorTF);
		
		
		yearLabel = new JLabel("Publication Year : ");
		yearLabel.setBounds(100, 250, 120, 30);
		panel.add(yearLabel);
		
		yearTF = new JTextField();
		yearTF.setBounds(200, 250, 140, 30);
		panel.add(yearTF);
		
		quantityLabel = new JLabel("Quantity : ");
		quantityLabel.setBounds(100, 300, 120, 30);
		panel.add(quantityLabel);
		
		quantityTF = new JTextField();
		quantityTF.setBounds(200, 300, 140, 30);
		panel.add(quantityTF);
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(100, 350, 120, 30);
		updateBtn.setEnabled(false);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		delBtn = new JButton("Delete");
		delBtn.setBounds(250, 350, 120, 30);
		delBtn.setEnabled(false);
		delBtn.addActionListener(this);
		panel.add(delBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		availableBook = new JLabel ("Available Book");
		availableBook.setBounds (250, 380, 200, 50);
		availableBook.setFont (pixelPowerline);
		panel.add (availableBook);
		
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
			ManageBook mb = new ManageBook (userId);
			mb.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(refreshBtn.getText()))
		{
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorTF.setText("");
			yearTF.setText("");
			quantityTF.setText("");
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
		}
		else if(text.equals(updateBtn.getText()))
		{
			updateInDB();
			information ();
		}
		else if(text.equals(delBtn.getText()))
		{
			deleteFromDB();
			information ();
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String loadId = bookIdTF.getText();
		String query = "SELECT `bookId`, `bookTitle`, `authorName`, `publicationYear`, `availableQuantity` FROM `book` WHERE `bookId`='"+loadId+"';";     
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
			String bookName = null;
			String authorName = null;
			//String role = null;
			//double salary = 0.0;
			int publicationYear = 0;
			int availableQuantity = 0;
			while(rs.next())
			{
                bookName = rs.getString("bookTitle");
				authorName = rs.getString("authorName");
				publicationYear = rs.getInt("publicationYear");
				availableQuantity = rs.getInt("availableQuantity");
				flag=true;
				
				bookNameTF.setText(bookName);
				authorTF.setText(""+authorName);
				//phoneTF2.setText(phnNo.substring(4,14));
				yearTF.setText(""+publicationYear);
				quantityTF.setText(""+availableQuantity);
				bookIdTF.setEnabled(false);
				updateBtn.setEnabled(true);
				delBtn.setEnabled(true);
			}
			if(!flag)
			{
				bookNameTF.setText("");
				authorTF.setText("");
				yearTF.setText("");
				quantityTF.setText("");
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
	
	
	public void updateInDB()
	{
		String newId = bookIdTF.getText();
		String bookName = bookNameTF.getText();
		String authorName = authorTF.getText();
		int publicationYear = 0;
		int availableQuantity = 0;
		try
		{
			publicationYear = Integer.parseInt(yearTF.getText());
			availableQuantity = Integer.parseInt(quantityTF.getText());
		}
		catch(Exception e){}
		
		String query = "UPDATE book SET bookTitle='"+bookName+"', authorName = '"+authorName+"', publicationYear = '"+publicationYear+"', availableQuantity = "+availableQuantity+" WHERE bookId='"+newId+"'";	
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
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorTF.setText("");
			yearTF.setText("");
			quantityTF.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	public void deleteFromDB()
	{
		String newId = bookIdTF.getText();
		String query1 = "DELETE from book WHERE bookId='"+newId+"';";
		//String query2 = "DELETE from login WHERE userId='"+newId+"';";
		System.out.println(query1);
		//System.out.println(query2);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			//stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorTF.setText("");
			yearTF.setText("");
			quantityTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
	
	public void information ()
	{
		
        String query = "SELECT `bookId`, `bookTitle`, `authorName`, `publicationYear`,`availableQuantity` FROM `book`";     
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
					columnData [0] = rs.getString ("bookID");
					columnData [1] = rs.getString ("bookTitle");
					columnData [2] = rs.getString ("authorName");
					columnData [3] = rs.getString ("publicationYear");
					columnData [4] = rs.getString ("availableQuantity");
					
					model.addRow (columnData);
					
	              
				}
			}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
	}	
	
	
}