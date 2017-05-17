package com.sw.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.springframework.ui.Model;

import com.sw.controller.ConnectionDB;

public class NotificationDB implements NotificationInterface {
	private Connection connect;
	Statement st;

	public NotificationDB() {
		try {
			ConnectionDB connectiondb = new ConnectionDB();
			connect = connectiondb.Connect();
			st = connect.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public HashSet<String> SearchForUsers(NotifyedUser NU) {
		HashSet<String> users = new HashSet<String>();
		try {
			String g = "SELECT Email FROM score WHERE CourseName =" + "'" + NU.getCourseName() + "'";
			st = connect.createStatement();
			ResultSet resultset = st.executeQuery(g);
			while (resultset.next()) {
				String u = resultset.getString("Email");
				if (!u.equals(NU.getTeacher())) {
					users.add(u);
				}
				System.out.println(resultset.getString("Email"));
			}
		} catch (SQLException e) {
			return null;
		}

		return users;

	}

	public List<NotifyedUser> GetNotification(String email) {
		try {
			List<NotifyedUser> list = new ArrayList<NotifyedUser>();
			String g = "select * from  notification where Student =" + "'" + email + "'";
			st = connect.createStatement();
			ResultSet resultset = st.executeQuery(g);
			while (resultset.next()) {
				list.add(new NotifyedUser(resultset.getString("GameName"), resultset.getString("CourseName"),
						resultset.getString("Teacher"), email));
				// System.out.println("asd" + resultset.getString("Question"));
			}
			return list;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public List<NotifyedUser> ListNotification(String Email) {

		List<NotifyedUser> notification = new ArrayList<NotifyedUser>();
		notification = GetNotification(Email);
		return notification;
	}

	@Override
	public void Add(NotifyedUser NU) {
		HashSet<String> users = new HashSet<String>();
		Iterator<String> iterator = users.iterator();
		users = SearchForUsers(NU);
		try {
			for (String b : users) {
				PreparedStatement ps = connect.prepareStatement(
						"INSERT INTO notification (Teacher, Student, GameName, CourseName,Type) VALUES  (?,?,?,?,?)");
				ps.setString(1, NU.getTeacher());
				ps.setString(2, b);
				ps.setString(3, NU.getGameName());
				ps.setString(4, NU.getCourseName());
				ps.setString(5, "Add");
 				int j = ps.executeUpdate();
			}

		} catch (SQLException e) {
			System.out.println("Add Noti Exe:))");
			e.printStackTrace();
		}

	}

	@Override
	public void Remove(NotifyedUser NU) {

		try {
			String g="DELETE FROM notification WHERE GameName=('"+NU.getGameName()+"')AND CourseName=('"+NU.getCourseName()
			+"')AND Teacher=('"+NU.getTeacher()+"')AND Student=('"+NU.getStudent()+"')";
			st.executeUpdate(g);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}