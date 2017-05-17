package com.sw.model;

public class UnCancelGame implements Changes{

	private History history;
	
	public UnCancelGame(){
		history = new History();
	}
	
	public void execute(String gameName, String courseName){
		System.out.println("in uncancel change ..");
		history.unCancelGame(gameName, courseName);
	}

//	@Override
//	public void execute() {
//		// TODO Auto-generated method stub
//		
//	}
}
