package com.pp.user.controller;

import com.pp.common.vo.ResultMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    private static int MAX_SLEEP_TIME = 2000;

    //http://localhost:6001/histrix/timeout
    //可能会发生超时
    @GetMapping("/timeout")
    public ResultMessage getTimeout() {
        Long sleepTime = (Long) (MAX_SLEEP_TIME * (new Double(Math.random())).longValue());
        try {
            Thread.sleep(sleepTime);
        } catch (Exception ex){
            System.out.println("执行异常");
        }
        return new ResultMessage("200","执行成功！",null);
    }

    //http://localhost:6001/histrix/exp/aaa
    //可能会发生参数异常
    @GetMapping("/exp/{msg}")
    public ResultMessage getExp(@PathVariable("msg") String msg){
        if("spring".equals(msg)){
            return new ResultMessage("200","执行成功！",null);
        } else{
            throw new RuntimeException("传入参数有误");
        }
    }
}
