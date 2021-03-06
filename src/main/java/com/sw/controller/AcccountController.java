package com.sw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sw.model.Account;
import com.sw.model.AccountDB;
import com.sw.model.CourseDB;
import com.sw.model.Game;

@Controller
public class AcccountController {
	AccountDB account = new AccountDB();
	Account user_account;
	//User user = new User();
	boolean reg;
	public String usertype;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/teacher")
	public String teacher() {
		return "teacher";
	}
//method = RequestMethod.POST,
	@RequestMapping( value = "/check")
	public ModelAndView CheckLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			Model model) {
		ModelAndView modelandview = new ModelAndView();
		// String s = "index";
		CourseDB course=new CourseDB();
		model.addAttribute("courses", course.SelectAll());

		usertype = account.LoginDB(email, password);
		modelandview.addObject("result", "Incorrect Login ");
		user_account = new Account("", password, email, usertype);
	System.out.println("user "+user_account.getUserType());

		User.setAccount(user_account);
		modelandview.setViewName(User.getAccount().getUserType());
		return modelandview;
	}

	@RequestMapping("/singup")
	public String CreatAccount() {
		return "CreateAccount";

	}

	@RequestMapping("/userinfo")
	public String UserInformation(Model model) {
		CourseDB coursedb = new CourseDB();
		Account ac = account.GetUserInfo();
		model.addAttribute("info", ac);
		System.out.println(ac.getUserName());
		model.addAttribute("courses", coursedb.ShowUserCourses());
		return "Profile";
	}

	@RequestMapping("/studentinfo")
	public String studentinfo(Model model) {
		Account ac = account.GetUserInfo();
		System.out.println(ac.getUserName());
		model.addAttribute("info", ac);
		List<Game> list = new ArrayList<Game>();
		list = account.Retrivescores();
		// System.out.println("l"+list.get(1).Game_Name+" "+list.get(1).score);
		model.addAttribute("scores", list);
		return User.GetAccount().getUserType();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/creat_account")
	public ModelAndView Register(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("type") String type) {
		user_account = new Account(name, password, email, type);
		User.setAccount(user_account);

		ModelAndView modelandview = new ModelAndView();
		if (!account.exists(email)) {
			reg = account.CreateAccountDB(user_account);
		} else {
			modelandview.addObject("result", "Incorrect Registeration Email Exist ya m3lem:) ");
			modelandview.setViewName("index");
			return modelandview;
		}
		if (reg) {
			modelandview.setViewName(type);
		} else {
			modelandview.setViewName("index");
			modelandview.addObject("result", "Incorrect Registeration ");
		}
		CourseDB course=new CourseDB();
		modelandview.addObject("courses", course.SelectAll());
		return modelandview;
	}

}