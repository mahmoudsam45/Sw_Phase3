package com.sw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sw.model.CancelGame;
import com.sw.model.Changes;
import com.sw.model.CommentDB;
import com.sw.model.CourseDB;
import com.sw.model.Game;
import com.sw.model.GameDB;
import com.sw.model.Invoker;
import com.sw.model.MCQ;
import com.sw.model.NotificationDB;
import com.sw.model.NotifyedUser;
import com.sw.model.UnCancelGame;

@Controller
public class GameController {
	Game game = new Game();
	GameDB gamedb = new GameDB();
	CourseDB course = new CourseDB();
	NotificationDB NotificationDB = new NotificationDB();
	Invoker invoker = new Invoker();

	List<MCQ> questions;
	static int x;
	int score = 0;

	@RequestMapping("/AddGame")
	public String addgame(@RequestParam("GName") String gamename, @RequestParam("CName") String coursename,
			Model model) {
		game.Game_Name = gamename;
		game.course_name = coursename;
		// .substring( 0,coursename.length()-1);
		// model.addAttribute("check","check");

		// System.out.println(game.course_name+"LLL");

		return "addgame";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/AddQuestion")
	public String AddQuestion(Model model, @RequestParam("Question") String Q, @RequestParam("Answer") String Ans,
			@RequestParam("A") String A, @RequestParam("B") String B, @RequestParam("C") String C,
			@RequestParam("D") String D) {

		game.MCQ_Questions.add(new MCQ(Q, Ans, A, B, C, D));
		return "addgame";
	}

	@RequestMapping("/playfirst")
	public String playfirst(Model model) {
		model.addAttribute("courses", course.SelectAll());
		return "playfirst";
	}

	@RequestMapping("/SaveAll")
	public String SaveAll(Model model, @RequestParam("Question") String Q, @RequestParam("Answer") String Ans,
			@RequestParam("A") String A, @RequestParam("B") String B, @RequestParam("C") String C,
			@RequestParam("D") String D) {
		game.MCQ_Questions.add(new MCQ(Q, Ans, A, B, C, D));

		boolean added = gamedb.AddGame(game);

		for (int t = 0; t < game.MCQ_Questions.size(); t++) {
			System.out.println("1-" + game.MCQ_Questions.get(t).Question);
			System.out.println("1-" + game.MCQ_Questions.get(t).answer);

		}
		if (added) {
			course.AddGameinCourse(game.course_name, game.Game_Name);
			NotificationDB.Add(new NotifyedUser(game.Game_Name, game.course_name, User.getAccount().getEmail(), ""));

			model.addAttribute("added", "Game Added Sussesfuly");
		} else {
			model.addAttribute("added", "Falied To Add Game");
		}
		game.MCQ_Questions.clear();
		return User.getAccount().getUserType();
	}

	@RequestMapping("/playgame")
	public String playgame(Model model, @RequestParam("GName") String gamename,
			@RequestParam("CName") String coursName) {
		try {
			game.setGame_Name(gamename);
			game.setCourse_name(coursName);
			x = 0;
			questions = gamedb.RetriveGame(gamename, coursName);

			model.addAttribute("Game", questions.get(0));

			return "playgame";
		} catch (Exception e) {
			return playfirst(model);

		}

	}

	@RequestMapping("/question")
	public String play(Model model, @RequestParam("a") String selected) {
		if (selected.equals(questions.get(x).getAnswer())) {

			score++;
		}
		x++;
		if (x >= questions.size()) {
			System.out.println("c g"+game.getGame_Name()+ " "+game.getCourse_name());
			gamedb.saveScore(score, User.getAccount().getEmail(), game.getGame_Name(),game.getCourse_name());
			System.out.println(score);
			model.addAttribute("score", score);
			score = 0;
			//System.out.println("asd" + User.getAccount().getUserType());
			CourseDB course = new CourseDB();
 			model.addAttribute("courses", course.SelectAll());
			return User.getAccount().getUserType();
		}
		model.addAttribute("Game", questions.get(x));

		return "playgame";

	}

	@RequestMapping("/copyGame")
	public String copyGame(Model model, @RequestParam("GName") String gameName,
			@RequestParam("CName") String courseName) {

		System.out.println("gameName = " + gameName + " , courseName = " + courseName);

		gamedb.copyGame(gameName, courseName, User.GetAccount().getUserName());
		model.addAttribute("copied", "Game copied Sussesfuly");
		CourseDB course=new CourseDB();
		model.addAttribute("courses", course.SelectAll());
		return "teacher";
	}

	@RequestMapping("/cancelGame")
	public String cancelGame(Model model, @RequestParam("GName") String gameName,
			@RequestParam("CName") String courseName) {

		System.out.println("gameName = " + gameName + " , courseName = " + courseName);
		// gamedb.deleteGame(gameName, courseName);

		Changes change = new CancelGame();
		Invoker invoker = new Invoker();
		invoker.PlaceChanges(change, gameName, courseName);
		gamedb.deleteGame(gameName, courseName);

		model.addAttribute("cancelled", "Game cancelled successfully");
		CourseDB course=new CourseDB();
		model.addAttribute("courses", course.SelectAll());
		return "teacher";
	}

	@RequestMapping("/unCancelGame")
	public String unCancelGame(Model model, @RequestParam("GName") String gameName,
			@RequestParam("CName") String courseName) {

		System.out.println("gameName = " + gameName + " , courseName = " + courseName);
		Changes change = new UnCancelGame();
		Invoker invoker = new Invoker();
		invoker.PlaceChanges(change, gameName, courseName);
		gamedb.unDeleteGame(courseName,gameName );
		model.addAttribute("unCancelled", "Game unCancelled successfully");
		CourseDB course=new CourseDB();
		model.addAttribute("courses", course.SelectAll());
		return "teacher";
	}

	@RequestMapping("/gameHistory")
	public String trackGameChanges(Model model, @RequestParam("GName") String gameName,
			@RequestParam("CName") String courseName) {

		model.addAttribute("history", invoker.getHistory(gameName, courseName));
		CourseDB course=new CourseDB();
		model.addAttribute("courses", course.SelectAll());
		return "gameHistory";
	}

}
