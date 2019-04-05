import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class IssueBook extends JFrame implements ActionListener
{
	JLabel welcomeLabel, bookIdLabel, bookNameLabel, authorLabel, yearLabel, quantityLabel, ebackGround, availableBook,borrowId, borrowBookId, sUserId, borrowDate, returnDate;
	JTextField bookIdTF, authorTF, bookNameTF, yearTF, quantityTF, borrowIdTF, borrowBookIdTF, userIdTF, borrowDateTF, returnDateTF;
	JButton refreshBtn, loadBtn, issueBtn, delBtn, backBtn, logoutBtn;
	JPanel panel;
	ImageIcon limg, lbtnImg, img;
	Font pixelPowerline;
	String userId;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	
	String [] columns = {"Borrow ID","Book ID","Student ID","Borrow Date","Return Date "};
	String [] rows = new String [5];
	
	public IssueBook(String userId)
	{
		super("Issue Book");
		
		limg = new ImageIcon ("library.png");
		this.setIconImage (limg.getImage());
		this.userId = userId;
		this.setSize(700,620);
		this.setLocation(300,50);
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
		welcomeLabel.setBounds(510, 40, 400, 30);
		welcomeLabel.setFont (pixelPowerline);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton(lbtnImg);
		logoutBtn.setBounds(650, 45, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		
		
		bookIdLabel = new JLabel("Book ID : ");
		bookIdLabel.setBounds(50, 100, 100, 30);
		panel.add(bookIdLabel);
		
		bookIdTF = new JTextField();
		bookIdTF.setBounds(150, 100, 120, 30);
		panel.add(bookIdTF);
		
		loadBtn = new JButton("Search");
		loadBtn.setBounds(280, 100, 80, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		bookNameLabel = new JLabel("Book Name : ");
		bookNameLabel.setBounds(50, 150, 120, 30);
		panel.add(bookNameLabel);
		
		bookNameTF = new JTextField();
		bookNameTF.setBounds(150, 150, 120, 30);
		bookNameTF.setEnabled (false);
		panel.add(bookNameTF);
		
		authorLabel = new JLabel("Author Name : ");
		authorLabel.setBounds(50, 200, 120, 30);
		panel.add(authorLabel);
		
		authorTF = new JTextField();
		authorTF.setBounds(150, 200, 120, 30);
		authorTF.setEnabled (false);
		panel.add(authorTF);
		
		
		yearLabel = new JLabel("Publication Year : ");
		yearLabel.setBounds(50, 250, 120, 30);
		panel.add(yearLabel);
		
		yearTF = new JTextField();
		yearTF.setBounds(150, 250, 120, 30);
		yearTF.setEnabled (false);
		panel.add(yearTF);
		
		quantityLabel = new JLabel("Quantity : ");
		quantityLabel.setBounds(50, 300, 120, 30);
		panel.add(quantityLabel);
		
		quantityTF = new JTextField();
		quantityTF.setBounds(150, 300, 120, 30);
		quantityTF.setEnabled (false);
		panel.add(quantityTF);
		
		issueBtn = new JButton ("Issue");
		issueBtn.setBounds (510, 350, 100, 30);
		issueBtn.setEnabled(false);
		issueBtn.addActionListener(this);
		panel.add(issueBtn);
		
		
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		
		borrowId = new JLabel("Borrow ID : ");
		borrowId.setBounds(400, 100, 100, 30);
		panel.add(borrowId);
		
		borrowIdTF = new JTextField();
		borrowIdTF.setBounds(500, 100, 120, 30);
		panel.add(borrowIdTF);
		
		
		
		borrowBookId = new JLabel("Book ID : ");
		borrowBookId.setBounds(400, 150, 100, 30);
		panel.add(borrowBookId);
		
		borrowBookIdTF = new JTextField();
		borrowBookIdTF.setBounds(500, 150, 120, 30);
		panel.add(borrowBookIdTF);
		
		sUserId = new JLabel("User ID : ");
		sUserId.setBounds(400, 200, 100, 30);
		panel.add(sUserId);
		
		userIdTF = new JTextField();
		userIdTF.setBounds(500, 200, 120, 30);
		panel.add(userIdTF);
		
		
		borrowDate = new JLabel("Borrow Date : ");
		borrowDate.setBounds(400, 250, 100, 30);
		panel.add(borrowDate);
		
		borrowDateTF = new JTextField();
		borrowDateTF.setBounds(500, 250, 120, 30);
		panel.add(borrowDateTF);
		
		returnDate = new JLabel ("Return Date : ");
		returnDate.setBounds (400, 300, 100, 30);
		panel.add (returnDate);
		
		returnDateTF = new JTextField ();
		returnDateTF.setBounds (500, 300, 120, 30);
		panel.add (returnDateTF);
		
		
		
		availableBook = new JLabel ("Issued Book Details");
		availableBook.setBounds (250, 380, 200, 50);
		availableBook.setFont (pixelPowerline);
		panel.add (availableBook);
		
		table = new JTable ();
		
		model = new DefaultTableModel ();
		model.setColumnIdentifiers (columns);
		table.setModel (model);
		table.setRowHeight (20);
		
		scroll = new JScrollPane (table);
		scroll.setBounds (25,420,650,150);
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
			EmployeeHome eh = new EmployeeHome (userId);
			eh.setVisible(true);
			this.setVisible(false);
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
		else if(text.equals(issueBtn.getText()))
		{
			
			insertIntoDB ();
			updateInDB();
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
				yearTF.setText(""+publicationYear);
				quantityTF.setText(""+availableQuantity);
				bookIdTF.setEnabled(false);
				issueBtn.setEnabled(true);
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
			availableQuantity = Integer.parseInt(quantityTF.getText())-1;
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
			//JOptionPane.showMessageDialog(this, "Success !!!");
			
			issueBtn.setEnabled(false);
			delBtn.setEnabled(false);
			bookIdTF.setEnabled(true);
			borrowIdTF.setText ("");
			borrowBookIdTF.setText ("");
			userIdTF.setText ("");
			borrowDateTF.setText ("");
			returnDateTF.setText ("");
				
			bookIdTF.setEnabled(true);
			bookIdTF.setText ("");
			bookNameTF.setText("");
			authorTF.setText("");
			yearTF.setText("");
			quantityTF.setText("");
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	
	public void insertIntoDB ()
	{
		String newBorrowId = borrowIdTF.getText();
		String newBookId = bookIdTF.getText();
		String newUserId = userIdTF.getText();
		String newBorrowDate = borrowDateTF.getText ();
		String newReturnDate = returnDateTF.getText ();
		
		
		
		
		
		String query = "INSERT INTO borrowinfo VALUES ('"+newBorrowId+"','"+newBookId+"','"+ newUserId+"','"+newBorrowDate+"','"+newReturnDate+"');";
		System.out.println (query);
		
		if (borrowIdTF.getText().trim().isEmpty() && bookIdTF.getText().trim().isEmpty() && userIdTF.getText().trim().isEmpty() && borrowDateTF.getText().trim().isEmpty() && returnDateTF.getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog (this, "All Information Required");
		}
		else 
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3", "root", "");
				Statement stm = con.createStatement();
				stm.execute(query);
				stm.close();
				con.close();
				JOptionPane.showMessageDialog(this, "Issued !!!");
				
				
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this, "Oops !!!");
			}
			
		
		}
	}
	
	public void information ()
	{
		
        String query = "SELECT `borrowId`, `bookId`, `userId`, `borrowDate`,`returnDate` FROM `borrowinfo`";     
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
					columnData [0] = rs.getString ("borrowID");
					columnData [1] = rs.getString ("bookId");
					columnData [2] = rs.getString ("userId");
					columnData [3] = rs.getString ("borrowDate");
					columnData [4] = rs.getString ("returnDate");
					
					model.addRow (columnData);
					
	              
				}
			}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
	}

	
	
}