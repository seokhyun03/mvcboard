package com.goodee.mvcboard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	@PostMapping("login")
	public String login(HttpSession session, @RequestParam(name = "memberId") String memberId, @RequestParam(name = "memberPw") String memberPw) {
		
		
		session.setAttribute("loginMemberId", memberId);
		return "login";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:home";
	}
}
