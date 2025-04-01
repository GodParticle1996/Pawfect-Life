package com.example.pawfectlife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetSupplyController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }

    @GetMapping("/deals")
    public String deals() {
        return "deals";
    }

    @GetMapping("/product")
    public String product() {
        return "product";
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
