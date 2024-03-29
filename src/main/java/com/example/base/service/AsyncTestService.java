package com.example.base.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTestService {

    public String step1() {
        return "step1";
    }

    @Async
    public String step2() throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + "::" + "step2 start");
        Thread.sleep(15000);
        System.out.println(Thread.currentThread().getId() + "::" + "step2 done");
        return "step2";
    }

    public String step3() {
        return "step3";
    }

}
