package com.sw.model;

public class EditGame implements Changes{

	private History history;
	
	public EditGame(){
		history = new History();
	}
	
	public void execute(String gameName, String courseName){
		history.editGame(gameName, courseName);
	}
	
}
