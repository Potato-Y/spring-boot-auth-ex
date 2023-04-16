package io.github.potatoy.auth_ex.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/chat")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String login(HttpServletRequest request) {
        return "chat";
    }
}