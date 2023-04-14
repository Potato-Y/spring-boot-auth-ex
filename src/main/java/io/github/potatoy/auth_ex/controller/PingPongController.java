package io.github.potatoy.auth_ex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PingPongController {

    @GetMapping("/ping")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("pong");
    }
}
