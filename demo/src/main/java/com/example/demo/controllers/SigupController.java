package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SigupController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String sign(Model model){
        model.addAttribute("login", "Вход");
        return "login";
    }

    @PostMapping("/login")
    public String signUp(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, HttpServletRequest request, Model model,@Autowired AuthenticationManagerBuilder auth){
        if (bindingResult.hasErrors()) {
            return "login";
        }
        if (!userService.isExist(userForm.getUsername())){
            model.addAttribute("usernameError", "Пользователь не найден");
            return "login";
        }
        //        authWithHttpServletRequest(request, userForm.getUsername(),userForm.getPassword());

//        try {
//            auth.jdbcAuthentication();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        return "redirect:/";
    }

    private void authWithHttpServletRequest(HttpServletRequest request, String username, String password) { //https://www.baeldung.com/spring-security-auto-login-user-after-registration
        try {
            request.login(username, password); // why is it don't working?? it's only redirect to /login !!!!!
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
