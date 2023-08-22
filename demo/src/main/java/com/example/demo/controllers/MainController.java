package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home( String name, Model model) {
        model.addAttribute("title","Главная страничка");
        return "home";
    }

    @GetMapping("/about")
    public String about( String name, Model model) {
        model.addAttribute("about","Обо мне");
        return "redirect:https://waalfegor.github.io/LastTest/";
    }

    @GetMapping("/login")
    public String login( String name, Model model) {
        model.addAttribute("login","Вход");
        return "login";
    }
}



