package com.chang.recmv.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chang.recmv.model.Criteria;
import com.chang.recmv.model.Paging;
import com.chang.recmv.model.User;
import com.chang.recmv.service.AdminService;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class); 

	@Autowired
	private AdminService service;
	
	@Autowired
	private HttpSession session;
	
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 관리자 회원목록 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@GetMapping("/users")
	public String adminGetUsers(Model model, Criteria cri) throws Exception {
		logger.info("Admin: adminGetUsers(Model model, Criteria cri) 시작");
		String id = (String)session.getAttribute("id");
		if(id == null || !id.equals("admin")) 
			return "redirect:/user/login";	
		Paging page = new Paging(cri, service.adminGetNumUsers(id));
		model.addAttribute("page", page);
		model.addAttribute("users", service.adminGetUsers(cri, id));
		logger.info("Admin: adminGetUsers(Model model, Criteria cri) 끝");
		
		return "admin/users";
	}
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 관리자 회원목록 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 관리자 회원수정 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@GetMapping("/update/{num}")
	public String adminUpdateUserGET(Model model, @PathVariable Integer num) throws Exception {	
		logger.info("User: adminUpdateUserGET(Model model, @PathVariable Integer num) 시작");
		String id = (String)session.getAttribute("id");
		if(id == null || !id.equals("admin")) 
			return "redirect:/user/login";	
		User user = service.adminReadUser(num);
		logger.info("관리자 회원수정 전: " + user);
		model.addAttribute("user", user);
		logger.info("User: adminUpdateUserGET(Model model, @PathVariable Integer num) 끝");
		
		return "admin/updateUser";
	}	
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 관리자 회원수정 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
}