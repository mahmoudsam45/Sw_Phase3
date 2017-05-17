package com.sw.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

import com.sw.controller.ConnectionDB;

// Reciever
public class History {	
	private Connection connect;
	private Statement st;
	GameDB gamedb = new GameDB();
	
	public History(){
		ConnectionDB connectiondb = new ConnectionDB();
		connect = connectiondb.Connect();
	}
	
	public void editGame(String gameName, String courseName){
		
		addChangeToDB(gameName, courseName, "edited");
	}
	
	public void addNewCollaborator(String gameName, String courseName){
		
		
	}
	
	public void cancelGame(String gameName, String courseName){
		System.out.println("in cancel execute history ..");
		//gamedb.deleteGame(gameName, courseName);
		addChangeToDB(gameName, courseName, "canceled");
	}
	
	public void unCancelGame(String gameName, String courseName){
		System.out.println("in cancel execute history ..");
		//gamedb.unDeleteGame(gameName, courseName);
		addChangeToDB(gameName, courseName, "unCanceled");
	}
	
	public void addChangeToDB(String gameName, String courseName, String event){
		
		try {
			PreparedStatement ps = connect.prepareStatement("insert into history (GameName, CourseName, Event) values (?,?,?)");
			ps.setString(1, gameName);
			ps.setString(2, courseName);
			ps.setString(3, event);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("can't add change to the database !!");
			e.printStackTrace();
		}
		
	}
	
}
