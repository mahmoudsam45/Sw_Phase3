package com.sw.model;

import java.util.ArrayList;
import java.util.List;

import com.sw.controller.User;

public class NotifyedUser implements Observer{
String GameName,CourseName,Teacher,Student;
NotificationDB notificationdb=new NotificationDB();
//User user=new User();

	public NotifyedUser(String gameName, String courseName, String teacher, String student) {
	super();
	GameName = gameName;
	CourseName = courseName;
	Teacher = teacher;
	Student = student;
}
	@Override
	public List<NotifyedUser> ListNotification(String Email) {
 		return null;
	}

	public String getGameName() {
		return GameName;
	}

	public void setGameName(String gameeName) {
		GameName = gameeName;
	}

	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	public String getTeacher() {
		return Teacher;
	}

	public void setTeacher(String teacher) {
		Teacher = teacher;
	}

	public String getStudent() {
		return Student;
	}

	public void setStudent(String student) {
		Student = student;
	}

}
