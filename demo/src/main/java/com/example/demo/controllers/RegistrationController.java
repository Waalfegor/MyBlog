package com.example.demo.controllers;

// was taken from https://habr.com/ru/articles/482552/


import com.example.demo.models.User;
import com.example.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        authWithHttpServletRequest(request, userForm.getUsername(),userForm.getPassword());

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
