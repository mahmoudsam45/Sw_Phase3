package com.sw.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sw.model.Comment;
import com.sw.model.CommentDB;
import com.sw.model.CourseDB;
import com.sw.model.GameDB;
import com.sw.model.NotificationDB;
import com.sw.model.NotifyedUser;

@Controller
public class NotificationController {
	NotificationDB NotDB = new NotificationDB();
	List<NotifyedUser> notuser = new ArrayList<NotifyedUser>();
	List<Comment> unseen = new ArrayList<Comment>();

	//User user = new User();
	CourseDB course = new CourseDB();
	String usertype;
	CommentDB commentdb = new CommentDB();

	@RequestMapping("/playfirst2")
	public String playfirst(Model model, @RequestParam("x") int x) {
		NotDB.Remove(new NotifyedUser(notuser.get(x).getGameName(), notuser.get(x).getCourseName(),
				notuser.get(x).getTeacher(), notuser.get(x).getStudent()));
		notuser.remove(x);
		model.addAttribute("courses", course.SelectAll());
		return "playfirst";
	}

	@RequestMapping("/ListNotification")
	public String List(Model model) {
		//System.out.println(user.getAccount().getEmail());
		usertype = User.getAccount().getUserType();
		unseen = commentdb.SelectUnseen(User.getAccount().getEmail());
		model.addAttribute("comments", unseen);
		notuser = NotDB.ListNotification(User.getAccount().getEmail());
		model.addAttribute("Notification", notuser);
		CourseDB course=new CourseDB();
		model.addAttribute("courses", course.SelectAll());
		return "Notification";
	}

	@RequestMapping("/homepage")
	public ModelAndView homepage(Model model) {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName(usertype);
		return modelandview;
	}

	@RequestMapping("/Remove")
	public String Remove(Model model, @RequestParam("x") int x) {
		NotDB.Remove(new NotifyedUser(notuser.get(x).getGameName(), notuser.get(x).getCourseName(),
				notuser.get(x).getTeacher(), notuser.get(x).getStudent()));
		notuser.remove(x);
		model.addAttribute("Notification", notuser);

		return "Notification";
	}

	@RequestMapping(  value = "/WriteComment")
	public String AddComment(Model model, @RequestParam("GName") String gamename,
			@RequestParam("CName") String coursName, @RequestParam("Comment") String comment) {
System.out.println(gamename+" "+coursName+" "+comment);
 		commentdb.AddComment(new Comment(gamename, coursName, comment, commentdb.GameAdmin(gamename, coursName),
				User.getAccount().getEmail(), 0));

		return User.getAccount().getUserType();
	}

	@RequestMapping( value = "/RemoveComment")
	public String RemoveComment(Model model, @RequestParam("x") int x) {
 		commentdb.MakeSeen(new Comment(unseen.get(x).getGname(), unseen.get(x).getCname(), unseen.get(x).getComment(),
				unseen.get(x).getToEmail(), unseen.get(x).getFromEmail(), 1));
		unseen.remove(x);
		model.addAttribute("comments", unseen);
		return "Notification";
	}

}
