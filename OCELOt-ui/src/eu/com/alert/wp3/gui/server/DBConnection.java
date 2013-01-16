package eu.com.alert.wp3.gui.server;

import java.sql.*;

public class DBConnection 
{	
	Connection conn;
//	static String bd = "ocelot";
//	static String login = "root";
//	static String password = "";
//	static String url = "jdbc:mysql://localhost:3306/"+bd;
	
	static String bd = "";
	static String login = "";
	static String password = "";
	static String host = "";
	
	
	
	
	public DBConnection() 
	{
		ConfigDB config = new ConfigDB();
		System.err.println("*********** READING CONFIGURING FILES ***********");
		String[] getConectiondata = config.readFile();
		
		System.err.println("Tengo los datos"+getConectiondata);
		
		bd = getConectiondata[0];
		login = getConectiondata[1];
		password = getConectiondata[2];
		host = getConectiondata[3];
		String url = "jdbc:mysql://"+host+"/"+bd;
	
		try 
		{

			/**** Cargamos el driver ****/
			System.err.println("Database conection"+url);
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,login,password);
		
			Statement stmt = conn.createStatement();
            ResultSet rs;
         
            if (conn!=null)
            {
                System.err.println(" *********** CONNECTION WITH DB ---> OK *********** ");
            }
            else
            {
            	System.err.println(" *********** CONNECTION WITH DB ---> FAULT *********** ");
            }          
        } 
		catch (SQLException e) 
		{
			 System.err.println("Exception CATCH conection.... ");
	         System.err.println(e.getMessage());
		} 
		catch (ClassNotFoundException e) 
		{
			 System.err.println("CATCH found  driver....  ");
	            System.err.println(e.getMessage());
		}
	}
	public Connection getConnection()
	{
	      return conn;
	}

   public void desconectar()
   {
      conn = null;
   }
}
