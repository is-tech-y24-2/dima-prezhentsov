package ru.itmo.kotikijava2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/")
    public ResponseEntity helloAll() {
        return ResponseEntity.ok("<h2>Hello Everyone!</h2>");
    }
//    @GetMapping(path = "/addOwner")
//    public String addOwnerPage() {
//        return "add";
//    }
}
