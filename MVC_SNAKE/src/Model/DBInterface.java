package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SuppressWarnings("unused")
public class DBInterface {
//constructor no connection
	//connect to DB
	//find ID if exists
	//add ID if doest exists
	//update user Data if exists
	//check authority
	//check Login
	private String IP;
	private String URL;
	private int portnumber;
	private String mySqlUserName;
	private String mySqlPassword;
	private String Scheme;
	private Connection conn;
	
	
	public DBInterface(String IP)
	{
	this.IP=IP;
	portnumber = 3306;
	mySqlUserName = "root";
	mySqlPassword="Cbh190919";
	Scheme="test";
	URL = "jdbc:mysql://"+IP+":"+portnumber+"/"+Scheme;
	checkForSetup();
	}
	
	public void checkForSetup()
	{
		connectToDataBase();
		PreparedStatement pstmt;
		 String sql;
		 
		   try {
			   
			   sql="select schema_name from information_schema.schemata where schema_name = 'test';";
			   pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			   ResultSet rs = pstmt.executeQuery();
			   rs.last();
			   if(rs.getRow()==0)
			   {
				   sql ="CREATE SCHEMA test;";
				   pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				    rs = pstmt.executeQuery();
			   }
			   
			   sql = "SELECT * FROM information_schema.tables WHERE table_schema = 'test' AND table_name = 'userlist' LIMIT 1;";
			pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			    rs = pstmt.executeQuery();
			   rs.last();
			   if(rs.getRow()==0)
			   {
				   sql=	"CREATE TABLE userlist(UserID VARCHAR(200) NOT NULL, PW TINYTEXT NOT NULL, level INT(10) NOT NULL,AuthorityLevel int(11), PRIMARY KEY (UserID));";
					pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
						pstmt.execute();
			   }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void connectToDataBase()
	{
		try {
			conn= DriverManager.getConnection(URL,mySqlUserName , mySqlPassword);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("wrong IP");
		}
	}
	
	public boolean checkIfIDExists(String userID)
	{
		String sql ="SELECT UserID FROM userlist" ;
		try {
			PreparedStatement ptsm = conn.prepareStatement(sql);
			ResultSet st=ptsm.executeQuery();
			while(st.next()) {
				String line =st.getString("UserID");
		if(userID.equals(line))
		{
			return true;
		}
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		System.out.println("checkID");
		}
		return false;
	}

	public void register(String userID,String PW,int authLevel,int highscore)
	{
		
	
		String  sql = "INSERT INTO userlist(UserID,PW,HighScore,AuthorityLevel) "
		            + "VALUES(?,?,?,?)";
		 
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql,
			                              Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, userID);
			pstmt.setString(2, PW);
			pstmt.setInt(3, highscore);
			pstmt.setInt(4,authLevel);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean checkLoginDetails(String userID,String PW)
	{
		try {
			
		
		if(!checkIfIDExists(userID))
		{
			return false;
		}
		String sql ="SELECT UserID, PW FROM userlist";
	
			PreparedStatement ptsm = conn.prepareStatement(sql);
			ResultSet st=ptsm.executeQuery();
			while(st.next())
			{
				if(PW.equals(st.getString("PW")))
				{
					return true;
				}
				
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}
	
	public void updateDB(String userID,String targetedArea,String newValue)
	{
		
	
		if(targetedArea.equals("UserID"))
		{
			String sql = "SELECT * FROM userlist WHERE UserId = " +userID;
			try {
				PreparedStatement ptsm = conn.prepareStatement(sql);
				ResultSet st=ptsm.executeQuery();
				st.next();
				 sql = "INSERT INTO uselist(UserID,PW,HighScore,AuthorityLevel) "
			            + "VALUES(?,?,?,?)";
			 
			PreparedStatement pstmt = conn.prepareStatement(sql,
			                              Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newValue);
			pstmt.setString(2, st.getString("PW"));
			pstmt.setString(3, st.getString("HighScore"));
			pstmt.setString(4, st.getString("AuthorityLevel"));
			pstmt.executeUpdate();
			sql = "DELETE FROM userlist WHERE UserID=" +userID;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//get old row
			//create new row
			//remove old row
		}
		else
		{
			String sql = "UPDATE userlist SET "+ targetedArea +"= "+newValue+" WHERE UserID= '"+userID+"'";
			PreparedStatement psmt;
			try {
				psmt = conn.prepareStatement(sql);
				psmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//update old row
			//done
		}
	}

	public int getHighScore(String userID)
{
	String sql ="SELECT HighScore FROM userlist WHERE UserID="+ userID;
	
		PreparedStatement ptsm;
		try {
			ptsm = conn.prepareStatement(sql);
			ResultSet st=ptsm.executeQuery();
			st.next();
				int line =st.getInt("HighScore");
			
			return line;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
}

	public int getauthLevel(String userID)
{

	String sql ="SELECT AuthorityLevel FROM userlist WHERE UserID="+ userID;
	
		PreparedStatement ptsm;
		try {
			ptsm = conn.prepareStatement(sql);
			ResultSet st=ptsm.executeQuery();
			st.next();
				int line =st.getInt("AuthorityLevel");
			
			return line;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
}

	public String[][] getScoresforBoard()
{
	String sql="SELECT * FROM userlist ORDER BY HighScore DESC";
	try {
		PreparedStatement ptsm=conn.prepareStatement(sql);
		ResultSet st=ptsm.executeQuery();
		int j=getRowCount();
		
		int i=0;
		String[][] tmp=new String[j][3];
		while(st.next())
		{
			tmp[i][0]=st.getString("UserID");
			tmp[i][1]=st.getString("HighScore");
			tmp[i][2]=st.getString("AuthorityLevel");
		i++;
		}
		return tmp;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}

	public int getRowCount()
{
	String sql="SELECT * FROM userlist";
	PreparedStatement ptsm;
	try {
		ptsm = conn.prepareStatement(sql);
		ResultSet t=ptsm.executeQuery();
		t.last();
		return t.getRow();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return 0;
}
}
