package com.example.pawfectlife.controller;

import com.example.pawfectlife.model.User;
import com.example.pawfectlife.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetSupplyController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        // Check if email already exists
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "signup";
        }
        userService.registerUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }

    @GetMapping("/products")
    public String products() {
        return "products";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }
}