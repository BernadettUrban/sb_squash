package pti.sb_squash.controller;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash.db.Database;
import pti.sb_squash.model.Location;
import pti.sb_squash.model.Match;
import pti.sb_squash.model.Role;
import pti.sb_squash.model.User;

@Controller
public class AppController {

	@GetMapping("/")
	public String index() {

		return "index";
	}

	@GetMapping("/admin")
	public String admin() {

		return "admin";
	}



	public String generatePassword(int length) {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		Random random = new Random();
		char[] password = new char[length];

		password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		password[3] = numbers.charAt(random.nextInt(numbers.length()));

		for (int i = 4; i < length; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		String pwd = new String(password);
		return pwd;
	}

	@GetMapping("/reguser")
	public String registration(Model model, @RequestParam(name = "name") String name) {

		User user = new User();
		user.setName(name);
		user.setPassword(generatePassword(6));
		user.setRole(Role.USER);
		user.setPasswordchanged(false);

		Database db = new Database();
		db.saveUser(user);
		db.close();

		model.addAttribute("user", user);
		return "admin";
	}

	@GetMapping("/reglocation")
	public String regLocation(Model model, @RequestParam(name = "name") String name,
			@RequestParam(name = "address") String address) {

		Location location = new Location();
		location.setName(name);
		location.setAddress(address);
		location.setRate(1);

		// http://api.napiarfolyam.hu/?bank=mnb&valuta=eur

		/**
		 * SAXBuilder sb = new SAXBuilder(); String XML = "..."; Document document =
		 * sb.build( new StringReader(XML) );
		 * 
		 */

		Database db = new Database();
		db.saveLocation(location);
		db.close();

		model.addAttribute("location", location);
		return "admin.html";
	}
	@GetMapping("/regmatch_start")
	public String regMatch_start(Model model) {
		Database db = new Database();

		List<User> users = db.getUsers();
		List<Location> locations = db.getLocations();

		model.addAttribute("users", users);
		model.addAttribute("locations", locations);

		return "regmatch.html";
	}

	@GetMapping("/regmatch_finish")
	public String regMatch_finish(@RequestParam(name = "playerone") int playerone,
								  @RequestParam(name = "pointsplayerone") int pointsplayerone,
								  @RequestParam(name = "playertwo") int playertwo,
								  @RequestParam(name = "pointsplayertwo") int pointsplayertwo,
								  @RequestParam(name = "location") int location,
								  @RequestParam(name = "date") String date) {


		Database db = new Database();
		Match match = new Match();
		match.setUseridPlayerOne(db.getUserById(playerone));
		match.setPointsPlayerOne(pointsplayerone);
		match.setUseridPlayerTwo(db.getUserById(playertwo));
		match.setPointsPlayerTwo(pointsplayertwo);
		match.setLocationid(location);
		match.setDate(date);

		db.saveMatch(match);
		db.close();


		return "admin.html";
	}
	@GetMapping("/regmatch")
	public String regMatch(Model model, @RequestParam(name = "playerone") int playerone,
			@RequestParam(name = "pointsplayerone") int pointsplayerone,
			@RequestParam(name = "playertwo") int playertwo,
			@RequestParam(name = "pointsplayertwo") int pointsplayertwo, @RequestParam(name = "location") int location,
			@RequestParam(name = "date") String date) {

		/*Match match = new Match();
		match.setUseridPlayerOne(playerone);
		match.setPointsPlayerOne(pointsplayerone);
		match.setUseridPlayerTwo(playertwo);
		match.setPointsPlayerTwo(pointsplayertwo);
		match.setLocationid(location);
		match.setDate(date);*/

		Database db = new Database();
		Match match = new Match();
		match.setUseridPlayerOne(db.getUserById(playerone));
		match.setPointsPlayerOne(pointsplayerone);
		match.setUseridPlayerTwo(db.getUserById(playertwo));
		match.setPointsPlayerTwo(pointsplayertwo);
		match.setLocationid(location);
		match.setDate(date);

		db.saveMatch(match);
		List<User>users = db.getUsers();
		List<Location> locations = db.getLocations();
		db.close();
		
		model.addAttribute("users", users);
		model.addAttribute("locations", locations);

		return "admin.html";
		
		/*
		 * <select name="playerone">
		  <option th:each="playerone : ${users} th:number=${playerone}" ></option>
		</select>
		*
		* <input type="number" name="playerone" placeholder="playerOne" />
		 */
	}

	@GetMapping("/showmatches")
	public String showMatches(Model model) {
		Database db = new Database();
		List<Match> matches = db.getMatches();
		db.close();

		model.addAttribute("matches", matches);
		return "showmatches";
	}

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@PostMapping("/login")
	public String hello(Model model, @RequestParam(name = "name") String name,
			@RequestParam(name = "password") String pwd) {
		String targetPage = "";
		Database db = new Database();
		boolean registeredUser = db.registeredUser(name, pwd);
		User user = db.getUserByName(name);
		String id = "";
		if (registeredUser == true) {

			if(user.isPasswordchanged() == true){
				targetPage = "showmatches";
			}else{
				targetPage = "changepwd";
			}


		} else {
			targetPage = "login";
			model.addAttribute("error", "Not a registered user.");
		}

		return targetPage;
	}

	@PostMapping("/changepwd")
	public String changePassword(@RequestParam(name = "pwd")String pwd){

		return "showmatches";
	}

}
