package com.flyaway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection connection;
	public DBConnection (String dbURL, String user, String password) throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.jdbc.Driver"); //Registering the Driver
		this.connection = DriverManager.getConnection(dbURL, user, password); //leveraging Driver Manager to get the connection
		
	}
	public Connection getConnection()
	{
		return this.connection; //this provides the required connection for your Java Programs
		
	}
	public void closeConnection() throws SQLException
	{
		if(this.connection!=null) //to check if the connection exist
			this.connection.close(); //close the connection
	}
}
