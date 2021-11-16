package com.chang.recmv.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chang.recmv.model.User;
import com.chang.recmv.service.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {
	/* GET 방식: thymeleaf에서 / 없음
	 * POST 방식: ModelAndView에서 redirect 사용
	 * String을 json으로 전달: 쌍따옴표 제거, replaceAll("^\"|\"$", "")
	 * 
	 * 
	 */
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class); 
	
	@Autowired
	private UserService service;
	
	@Autowired
	private HttpSession session;	
	
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원가입 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// http://localhost:8088/recmv/user/signup
	@GetMapping("/signup")
	public String signupGet() throws Exception {
		logger.info("User: signupGet() 시작");
		logger.info("User: signupGet() 끝");
			
		return "user/signup";
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원가입 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 로그인 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// http://localhost:8088/recmv/user/login
	@GetMapping("/login")
	public String loginGet() throws Exception {
		logger.info("User: loginGet() 시작");		
		logger.info("User: loginGet() 끝");
		
		return "user/login";
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 로그인 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 메인 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@GetMapping("/main")
	public String main(Model model) throws Exception {		
		logger.info("User: main(Model model) 시작");
		String id = (String)session.getAttribute("id");
		if(id == null) 
			return "redirect:./login";
		model.addAttribute("id", id);
		logger.info("User: main(Model model) 끝");		
		
		return "main/main";
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 메인 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 로그아웃 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@GetMapping("/logout")
	public String logout() throws Exception {
		logger.info("User: logout() 시작");
		session.invalidate();
		logger.info("User: logout() 끝");
		
		return "redirect:./login";
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 로그아웃 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원조회 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@GetMapping("/read")
	public String readUser(Model model) throws Exception {		
		logger.info("User: readUser(Model model) 시작");
		String id = (String)session.getAttribute("id");
		if(id == null) 
			return "redirect:./login";
		User user = service.readUser(id);
		logger.info("회원조회: " + user);
		model.addAttribute("user", user);
		logger.info("User: readUser(Model model) 끝");
		
		return "user/read";
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원조회 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원수정 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@GetMapping("/update")
	public String updateUserGet(Model model) throws Exception {	
		logger.info("User: updateUserGet(Model model) 시작");
		String id = (String)session.getAttribute("id");
		if(id == null)
			return "redirect:./login";
		User user = service.readUser(id);
		logger.info("회원수정 전: " + user);
		model.addAttribute("user", user);
		logger.info("User: updateUserGet(Model model) 끝");
		
		return "user/update";
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원수정 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원탈퇴 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@GetMapping("/delete")
	public String deleteUserGet(Model model) throws Exception {
		logger.info("User: deleteUserGet(Model model) 시작");
		String id = (String)session.getAttribute("id");
		if(id == null) 
			return "redirect:./login";		
		User user = service.readUser(id);
		logger.info("회원삭제 전: " + user);		
		model.addAttribute("user", user);
		logger.info("User: deleteUserGet(Model model) 끝");
		
		return "user/delete";
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원탈퇴 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원목록 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@GetMapping("/user/all")
	public ModelAndView getAllUser(HttpSession session) throws Exception {
		logger.info("User: getAllUser(HttpSession session) 시작");
		ModelAndView mav = new ModelAndView("thymeleaf/user/all");
		String id = (String)session.getAttribute("id");		
		if(id == null || !id.equals("admin")) {
			mav.setViewName("redirect:./login");
			return mav;			
		}		
		mav.addObject("users", service.getAllUser(id));
		logger.info("User: getAllUser(HttpSession session) 끝");
		
		return mav;
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 회원목록 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
}