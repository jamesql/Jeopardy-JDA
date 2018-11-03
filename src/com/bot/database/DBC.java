package com.bot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBC {

		public String userid;
		public int level;
		public int correct;
		
		public DBC(String user) throws Exception {
			userid = user;
			level = getLevel();
			correct = getCorrect();
			
		}
	
	 public Connection getConnection() throws Exception{
		  try{
		   String driver = "com.mysql.jdbc.Driver";
		   String url = "jdbc:mysql://localhost:3306/jep";
		   String username = "root";
		   String password = "JagroshSucks1337";
		   Class.forName(driver);
		   
		   Connection conn = DriverManager.getConnection(url,username,password);
		   return conn;
		  } catch(Exception e){System.out.println(e);}
		  return null;
		 }
		 
		 public String getUser() throws Exception {
			 Connection conn = getConnection();
			 PreparedStatement getName = conn.prepareStatement("SELECT userid FROM level WHERE userid='" + userid + "'"); 
			 ResultSet res = getName.executeQuery();
			 if(res.first()) return res.getString("userid"); 
				 else return null;
		 }
		 
		 public int getLevel() throws Exception {
			 Connection conn = getConnection();
			 PreparedStatement getName = conn.prepareStatement("SELECT level FROM level WHERE userid='" + userid + "'"); 
			 ResultSet res = getName.executeQuery();
			 if(res.next()){
				 return res.getInt("level");
		 }else return 0;
		}
		 
		 public int getCorrect() throws Exception {
			 Connection conn = getConnection();
			 PreparedStatement getName = conn.prepareStatement("SELECT correct FROM level WHERE userid='" + userid + "'"); 
			 ResultSet res = getName.executeQuery();
			 if(res.next()){
				 return res.getInt("correct");
		 }else return 0;
		}
		 
		 public void inputUser() throws Exception {
			 Connection conn = getConnection();
			 PreparedStatement state = conn.prepareStatement("INSERT INTO level (level,correct,userid) VALUES (0,0," + userid + ")");
			 state.execute();
		 }
		 
		 public void addCorrect() throws Exception {
			 Connection conn = getConnection();
			 PreparedStatement state = conn.prepareStatement("UPDATE level SET correct=" + (correct + 1) + " WHERE userid = '" + userid + "';");
			 state.execute();
		 }
	
}
