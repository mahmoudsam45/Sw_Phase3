package com.sw.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.sw.controller.ConnectionDB;

public class Invoker {
	
	private Connection connect;
	private Statement st;
	private Changes change;
	//private Stack<Changes> changesQueue = new Stack<Changes>();
	//private Stack<Changes> tmpStack = new Stack<Changes>();	
	//
	
	public Invoker(){
		ConnectionDB connectiondb = new ConnectionDB();
		connect = connectiondb.Connect();
	}
	
	
	public void PlaceChanges(Changes change, String gameName, String courseName){
		System.out.println("in place changes ..");
		//changesQueue.add(change);
		//addChangeToDB(change);
		change.execute(gameName, courseName);		
	}
	
	
	public ArrayList<String> getHistory(String gameName, String courseName){
		
		String query = "select * from history where GameName = " + "'" + gameName + "'" + " and CourseName = " + "'" + courseName + "'";
		try {
			st = connect.createStatement();
			ResultSet result = st.executeQuery(query);
			ArrayList<String> list = new ArrayList<String>();
			
			while(result.next()){
				String gName = result.getString("GameName");
				String cName = result.getString("CourseName");
				String event = result.getString("Event");
				
				String temp = "game (" + gName + ") in " + cName + " is " + event;
				
				list.add(temp);
				return list;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
		
		return null;
	}
/*	
	public void Undo(){
		Changes tmpChange = changesQueue.peek();
		changesQueue.pop();
		tmpStack.push(tmpChange);
	}

	public void Redo(){
		Changes tmpChange = tmpStack.peek();
		tmpStack.pop();
		changesQueue.add(tmpChange);
	}
	
	/**
	 * @return the current state of the system while using undo and redo
	 */
	/*public Changes getState(){
		return changesQueue.peek();
	}*/
}
