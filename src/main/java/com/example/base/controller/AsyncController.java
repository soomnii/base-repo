package com.example.base.controller;

import com.example.base.service.AsyncCompFutureTestService;
import com.example.base.service.AsyncFutureTestService;
import com.example.base.service.AsyncTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class AsyncController {

    @Autowired
    AsyncTestService service;

    @Autowired
    AsyncFutureTestService futureTestService;

    @Autowired
    AsyncCompFutureTestService compFutureTestService;

    @GetMapping(value = "/asynctest")
    String asynctest() throws InterruptedException {

        System.out.println(Thread.currentThread().getId() + "::" + service.step1());
        service.step2();
        System.out.println(Thread.currentThread().getId() + "::" + service.step3());
        return "success";
    }

    @GetMapping(value = "/asynctest/f")
    String asyncFutureTest() throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getId() + "::" + futureTestService.step1());
        Future<String> ret = futureTestService.step2();
        System.out.println(Thread.currentThread().getId() + "::" + futureTestService.step3());
        //System.out.println(Thread.currentThread().getId() + "::" + ret.get()); -> blocking code로 변경 따라서 blocking을 최소화 할 수 있도록 로직 위치 조정 필요 (최대한 마지막으로)
        return "success";
    }

    @GetMapping(value = "/asynctest/cf")
    String asyncCompletableFutureTest() throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + "::" + compFutureTestService.step1());
        CompletableFuture<String> comp = compFutureTestService.cp();
        comp.thenAccept(res -> {
            System.out.println(Thread.currentThread().getId() + "::" + res);
        });
        System.out.println(Thread.currentThread().getId() + "::" + compFutureTestService.step3());
        return "success";
    }

     /*
    DB  요청 - 응답 transaction-id
    순서 상관이 없어


    포인트차감 요청

    화면              -> 주문서비스   ->    포인트서비스 ( 포인트 차감처리)
                       로컬 트랜잭션
                       포인트 차감요청
                    <-
    * 포인트 차감 요청 접수되었습니다.

                                          포인트차감처리
                           구독          <-
             주문 추가 트랜잭션
    * 포인트 차감 성공      <- 주문완료

 ------------------------------------------
    화면              -> 주문서비스   ->    포인트서비스 ( 포인트 차감처리)
                       로컬 트랜잭션
                       포인트 차감요청
                       polling()
                                          포인트차감처리
                           구독          <-
             주문 추가 트랜잭션
    * 포인트 차감 성공      <- 주문완료


    화면 -> 주문
    ( 기다리고) nonblocking sync

    polling - sub listener
    스레드가 띄워져있어 - 스레드 안기려도돼. Pub 끝나 구독 별도로

    비동기를 없애거나




    대외기간 <-> 내부 서비스
    동기 동기
    비동기 동기 ( 비동기 - @async)
    비동기 비동기
    동기 비동기 ( 동기 방식으로 묶어줘야 -- waiting )
        pub / sub -> SAGA 패턴
        devon
        pub - 끝나 스레드
        s

        http ( 요청/ 응답 각각 다른 http call) 서로 다른 스레드 -> polling 하고 있어야

     */
}
