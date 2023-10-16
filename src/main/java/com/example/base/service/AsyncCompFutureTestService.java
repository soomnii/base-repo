package com.example.base.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncCompFutureTestService {

    public String step1() {
        return "step1";
    }

    public CompletableFuture<String> cp() {

        return CompletableFuture.supplyAsync(() -> {
            try {
                return step2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "exception";
        });
    }

    public String step2() throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + "::" + "step2 start");
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getId() + "::" + "step2 done");
        return "step2";
    }


    public String step3() {
        return "step3";
    }

}
