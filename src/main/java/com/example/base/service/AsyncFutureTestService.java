package com.example.base.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncFutureTestService {

    public String step1() {
        return "step1";
    }

    @Async
    public Future<String> step2() throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + "::" + "step2 start");
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getId() + "::" + "step2 done");
        return new AsyncResult<String>("step2");
    }

    public String step3() {
        return "step3";
    }

}
