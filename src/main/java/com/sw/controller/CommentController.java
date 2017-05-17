package com.sw.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sw.model.Comment;
import com.sw.model.CommentDB;
import com.sw.model.GameDB;
@Controller
public class CommentController {
//	CommentDB commentdb=new CommentDB();
//	GameDB gamedb=new GameDB();
//	List<Comment> unseen=new ArrayList<Comment>();
//	@RequestMapping(method = RequestMethod.POST, value = "/WriteCommen")
//	public String AddComment(Model model, @RequestParam("GName") String gamename,
//			@RequestParam("CName") String coursName, @RequestParam("Comment") String comment) {
//		
//		commentdb.AddComment(new Comment(gamename, coursName, comment, commentdb.GameAdmin(gamename,coursName), User.getAccount().getEmail()
//				, 0));
//		
//		return User.getAccount().getUserType();
//	}
//	@RequestMapping(method = RequestMethod.POST, value = "/ListComments")
//	public String AddComment(Model model) {
//		unseen=commentdb.SelectUnseen(User.getAccount().getEmail());
//		model.addAttribute("comments", unseen);
//		return "Notification";
//		
//	}
//	@RequestMapping(method = RequestMethod.POST, value = "/RemoveComment")
//	public String AddComment(Model model)
//	

}
