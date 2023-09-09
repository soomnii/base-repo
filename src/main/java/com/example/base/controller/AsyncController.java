package com.example.base.controller;

import com.example.base.service.AsyncTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    @Autowired
    AsyncTestService service;

    @GetMapping(value = "/asynctest")
    String asynctest() throws InterruptedException {
        System.out.println(service.step1());
        service.step2();
        System.out.println(service.step3());
        return "success";
    }

}
