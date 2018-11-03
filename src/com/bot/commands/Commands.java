package com.bot.database;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

public class DBC {

		public String userid;
		public int level;
		public int correct;
		private MessageChannel channel;
		public String[][] arr = new String[10][2];
		public int rank;
		
		public DBC(String user) throws Exception {
			userid = user;
			level = getLevel();
			correct = getCorrect();
			
		}
		
		EmbedBuilder eb = new EmbedBuilder();
		
		public DBC(String user, MessageChannel chan) throws Exception {
			userid = user;
			level = getLevel();
			correct = getCorrect();
			channel = chan;
			
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
			 conn.close();
		 }
		 
		 public void addCorrect() throws Exception {
			 Connection conn = getConnection();
			 PreparedStatement state = conn.prepareStatement("UPDATE level SET correct=" + (correct + 1) + " WHERE userid = '" + userid + "';");
			 state.execute();
			 if (correct % 15 == 0) {
				 addLevel();
					eb.setTitle("Jeopardy Rank Up!");
					eb.addField("Level Up!", "You are now level " + (level + 1), false);
					eb.setFooter("Get Thinking!", null);
					eb.setColor(Color.CYAN);
					
					channel.sendMessage(eb.build()).queue(m ->{
						m.addReaction("\uD83C\uDF89").queue();
					});
			 }
			 conn.close();
		 }
		 
		 public void addLevel() throws Exception {
			 Connection conn = getConnection();
			 PreparedStatement state = conn.prepareStatement("UPDATE level SET level=" + (level + 1) + " WHERE userid = '" + userid + "';");
			 state.execute();
			 conn.close();
		 }
		 
		 public void getLb() throws Exception {
			 Connection conn = getConnection();
			 PreparedStatement state = conn.prepareStatement("SELECT correct,userid FROM level ORDER BY -correct");
			 ResultSet res = state.executeQuery();
			 res.beforeFirst();
			 res.next();
			 arr[0][0] = res.getString("userid");
			 arr[0][1] = Integer.toString(res.getInt("correct"));
			 for(int x = 0; x < 5; x++) {
				 res.next();
				 arr[x][0] = res.getString("userid");
				 arr[x][1] = Integer.toString(res.getInt("correct"));
			 }
			 res.first();
			 rank = 1;
			 String currentId = res.getString("userid");
			 while(currentId != userid) {
				res.next();
				if (res.isAfterLast())  {
						rank = 1;
						currentId = userid;
				}else{
				currentId = res.getString("userid");
				rank++;
			 }
		 } 
	}
}
