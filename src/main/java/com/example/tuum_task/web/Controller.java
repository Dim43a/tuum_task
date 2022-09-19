package com.example.tuum_task.web;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tuum")
public class Controller {

    @PostMapping("/account")
    public String createUser() {
        return "Success";
    }



}
