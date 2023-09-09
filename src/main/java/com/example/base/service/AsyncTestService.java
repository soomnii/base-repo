package com.example.base.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTestService {

    public String step1() {
        return "step1";
    }

    @Async
    public void step2() throws InterruptedException {
        Thread.sleep(15000);
        System.out.println("step2");
    }

    public String step3() {
        return "step3";
    }

}
