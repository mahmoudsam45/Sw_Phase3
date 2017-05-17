package com.sw.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sw.controller.ConnectionDB;
import com.sw.controller.User;

public class CommentDB {
	private Connection connect;
	Statement st;
	User user;

	public CommentDB() {

		ConnectionDB connectiondb = new ConnectionDB();
		connect = connectiondb.Connect();
	}

	public void AddComment(Comment comment) {
		PreparedStatement ps;
		if (!comment.getFromEmail().equals(comment.getToEmail())) {
System.out.println("123");
			try {
				ps = connect.prepareStatement(
						"INSERT INTO  comments (Seen, CourseName, GameName,FromEmail, ToEmail, Comment) VALUES(?,?,?,?,?,?)");
				ps.setInt(1, 0);
				ps.setString(2, comment.getCname());
				ps.setString(3, comment.getGname());
				ps.setString(4, comment.getFromEmail());
				ps.setString(5, comment.getToEmail());
				ps.setString(6, comment.getComment());
				int j = ps.executeUpdate();
				System.out.println("asd" + comment.getGname() + " " + comment.getFromEmail() + " "
						+ comment.getToEmail() + " " + comment.getComment());
			} catch (SQLException e) {
				System.out.println("Add Comment Exeption:))>");
				return;
			}
		}
		System.out.println("456");
	}

	public String GameAdmin(String Gname, String Cname) {

		String g;
		ResultSet resultset;
		try {
			g = "select user from courses where GameName =" + "'" + Gname + "'" + " AND CourseName= " + "'" + Cname
					+ "'";
			st = connect.createStatement();
			resultset = st.executeQuery(g);

			while (resultset.next()) {
				// if (Gname.equals(resultset.getString("GameName"))
				// && (Cname.equals(resultset.getString("CourseName")))) {

				return resultset.getString("user");

			}
		}

		catch (Exception e) {
			return null;
		}
		return null;
	}

	public List<Comment> SelectUnseen(String Email) {
		List<Comment> list = new ArrayList<Comment>();
		String g;
		try {
			g = "select  * from comments where ToEmail =" + "'" + Email + "'" + " AND Seen=0";
			st = connect.createStatement();
			ResultSet resultset = st.executeQuery(g);
			while (resultset.next()) {
				list.add(new Comment(resultset.getString("GameName"), resultset.getString("CourseName"),
						resultset.getString("Comment"), resultset.getString("ToEmail"),
						resultset.getString("FromEmail"), 0));
			}
			return list;
		} catch (Exception e) {
			return null;
		}

	}

	public void MakeSeen(Comment comment) {

		try {
			String g = "UPDATE comments SET Seen = 1 WHERE GameName=('" + comment.getGname() + "')AND CourseName=('"
					+ comment.getCname() + "')AND ToEmail=('" + comment.getToEmail() + "')AND FromEmail=('"
					+ comment.getFromEmail() + "')";
			st.executeUpdate(g);
			System.out.println(g);

		} catch (SQLException e) {
			System.out.println("make seen exe");
			e.printStackTrace();
		}
	}
}
