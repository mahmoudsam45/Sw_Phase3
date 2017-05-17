package com.sw.model;

public class AddNewCollaborator implements Changes{
	
	private History history;
	
	public AddNewCollaborator(){
		history = new History();
	}
	
	public void execute(String gameName, String courseName){
		history.addNewCollaborator(gameName, courseName);
	}

}
