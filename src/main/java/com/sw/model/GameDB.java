package com.sw.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import com.sw.controller.ConnectionDB;
import com.sw.controller.User;

public class GameDB implements Notifiable{
	
	private Account userObserver;
	private boolean changed;
	private final Object MUTEX= new Object();
	private Connection connect;
	Statement st;
	User user;
	public GameDB() {

		ConnectionDB connectiondb = new ConnectionDB();
		connect = connectiondb.Connect();
	}
	public int oldscore(String email, String gname) {
		try {
			String g = "select Score from  score where Game =" + "'" + gname + "'" + "and Email=" + "'" + email + "'";
			st = connect.createStatement();
			ResultSet resultset = st.executeQuery(g);
			while (resultset.next()) {
				return resultset.getInt("Score");
			}
		} catch (SQLException e) {
 			return -1;
		}
		return -1;
	}

	public void saveScore(int score, String email, String Gname,String CName) {
		try {
			int x = oldscore(email, Gname);
			PreparedStatement ps;
			  if (x == -1) {
				  ps = connect.prepareStatement("INSERT INTO score(Email, Game, Score,CourseName) VALUES (?,?,?,?)");
				ps.setString(1, email);
				ps.setString(2, Gname);
				ps.setInt(3, score);
				ps.setString(4, CName);


				int j = ps.executeUpdate();

			}else if (x < score) {
				  ps = connect.prepareStatement("UPDATE Score SET Score = ? where Game =? and Email=?");
					ps.setInt(1, score);
					ps.setString(2, Gname);
					ps.setString(3, email);
					int j = ps.executeUpdate();
  			}
				
		} catch (SQLException e) {
 			e.printStackTrace();
		}

	}

	public List<MCQ> RetriveGame(String gamename, String coursename) {
		ResultSet resultset = null;
		System.out.println(gamename + " " + coursename);
		List<MCQ> list = new ArrayList<MCQ>();
		String g;
 		try {
			g = "select * from " + coursename + " where Game_Name =" + "'" + gamename + "'";
			System.out.println(g);
			st = connect.createStatement();
			resultset = st.executeQuery(g);
			while (resultset.next()) {
				list.add(new MCQ(resultset.getString("Question"), resultset.getString("Answer"),
						resultset.getString("A"), resultset.getString("B"), resultset.getString("C"),
						resultset.getString("D")));
				System.out.println("asd" + resultset.getString("Question"));
			}
			return list;
		} catch (Exception e) {
			return list;
		}
	}

	public boolean AddGame(Game game) {
		try {

			 System.out.println("WWW" + game.course_name + "-" + game.MCQ_Questions.get(0).Question);
			for (int t = 0; t < game.MCQ_Questions.size(); t++) {
				PreparedStatement ps = connect.prepareStatement("INSERT INTO " + game.course_name
						+ "(Game_Name ,  Question ,  Answer ,  A ,  B ,  C ,  D ) VALUES (?,?,?,?,?,?,?)");
				ps.setString(1, game.Game_Name);
				ps.setString(2, game.MCQ_Questions.get(t).Question);
				ps.setString(3, game.MCQ_Questions.get(t).answer);
				ps.setString(4, game.MCQ_Questions.get(t).a);
				ps.setString(5, game.MCQ_Questions.get(t).b);
				ps.setString(6, game.MCQ_Questions.get(t).c);
				ps.setString(7, game.MCQ_Questions.get(t).d);
				// int i = st.executeUpdate(g);
				System.out
						.println("ADD" + game.MCQ_Questions.get(t).Question + ">>+" + game.MCQ_Questions.get(t).answer);
				int j = ps.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("exception :-)");
			return false;
		}
		return true;

	}
	
	/**
	 * this method returns the teacher who created the game
	 */
	public Account getGameCreator(String gameName, String courseName){
		
		try {
			String query1 = "select user from courses where CourseName = " + "'" + courseName + "'" + " and GameName = " + "'" + gameName + "'";
			System.out.println(query1);
			st = connect.createStatement();
			String userEmail = null;
			ResultSet result1 = st.executeQuery(query1);
			if(result1.next()){
				userEmail = result1.getString("user");
				System.out.println("user email is " + userEmail);
			}									
						
			String query2 = "select * from users where Email = " + "'" + userEmail + "'";
			st = connect.createStatement();
			ResultSet result2 = st.executeQuery(query2);
			if(result2.next()){
				String name = result2.getString("Name");
				String email = result2.getString("Email");
				String password = result2.getString("password");
				String type = result2.getString("Type");
				
				Account account = new Account();
				account.userName = name;
				account.Email = email;
				account.Password = password;
				account.UserType = type;
				return account;				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;		
	}
	public void copyGame(String gameName, String courseName, String userName){
		
		List<MCQ>Questions = RetriveGame(gameName, courseName);
		
		Account teacher = getGameCreator(gameName, courseName);
		userObserver = teacher;
		
		changed = true;
		notifyTeacher(gameName, courseName, userName);				
	}


	public void notifyTeacher(String gameName, String courseName, String userName){
		
		synchronized (MUTEX) {
			if(!changed){
				System.out.println("There is no new notifications ..");
				return;
			}			
			
			userObserver.updateNotification(gameName, courseName, userName);
			
			changed = false;
		}		
	}
	
	
	public void deleteGame(String gameName, String courseName){
		System.out.println("ff "+gameName+" "+courseName);
		try {	
 		String query = "delete from courses where GameName = ? ";
 		//			st = connect.createStatement();
//			st.executeQuery(query);
			// String sql = "DELETE FROM my_table WHERE col_string=?";
			    PreparedStatement pstmt = connect.prepareStatement(query);
			    pstmt.setString(1, gameName );
			   // pstmt.setString(2,courseName);
			   int  deleteCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {	
			System.out.println("Failed to cancel this game !!");
			e.printStackTrace();
		}
		
	}
	
	public void unDeleteGame(String gameName, String courseName){
		System.out.println(gameName+" "+courseName);

		try {
			PreparedStatement ps = connect.prepareStatement("insert into courses (CourseName, GameName,user) values (?,?,?)");
			ps.setString(1, gameName);
			ps.setString(2, courseName);
			ps.setString(3, User.getAccount().getEmail());
			ps.executeUpdate();
			
		} catch (SQLException e) {			
			System.out.println("Failed to unCancel this game !!");
			e.printStackTrace();
		}
		
	}

}
