package com.barabanov.spring.http.controller;

import com.barabanov.spring.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController
{
    @GetMapping("/login")
    public String loginPage()
    {
        return "user/login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute("login") LoginDto loginDto, Model model)
    {
//        return "forward:/WEB-INF/jsp/user/login.jsp";
        return "redirect:https://google.com";
    }
}
