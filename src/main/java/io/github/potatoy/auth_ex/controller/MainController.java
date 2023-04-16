package io.github.potatoy.auth_ex.controller;

import io.github.potatoy.auth_ex.config.Greeting;
import io.github.potatoy.auth_ex.config.HelloMessage;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/chat")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String login(HttpServletRequest request) {
        return "chat";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
    }
}