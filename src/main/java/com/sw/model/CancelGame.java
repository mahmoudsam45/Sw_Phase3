package com.sw.model;

public class CancelGame implements Changes{

	private History history;
	
	public CancelGame(){
		history = new History();
	}
	
	public void execute(String gameName, String courseName){
		System.out.println(" in cancel execute ..");
		history.cancelGame(gameName, courseName);
	}

	 

}