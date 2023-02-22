package com.example.demo.controllers;

import com.example.demo.services.TestService1;
import com.example.demo.services.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private TestService1 testService1;

    private TestService2 testService2;

    TestController(TestService2 testService2){
        this.testService2 = testService2;
    }

    @GetMapping("/hello")
    public String test(){



        return "helloWorld";
    }
}
