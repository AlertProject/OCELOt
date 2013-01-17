package eu.atosresearch.conceptmanagement.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConector {
	private Statement st;
	private String server;
	private String db;
	private String user;
	private String password;
	private Connection con;
	

	public JDBConector(String server,String db,String user, String password){
		this.server=server;
		this.db=db;
		this.user=user;
		this.password=password;
	}
	
	public void connect() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://"+server+"/"+db+"?user="+user+"&password="+password);
		st=con.createStatement();		
		
	}
	
	public void disconnect() throws SQLException{
		st.close();
		con.close();
	}
	
	public ResultSet executeSQL(String sql) throws SQLException{
		//System.out.println(sql);
		return st.executeQuery(sql);
		
	}
	
	public void executeUpdate(String sql) throws SQLException{
		//System.out.println(sql);		
		st.executeUpdate(sql);
	}
}
