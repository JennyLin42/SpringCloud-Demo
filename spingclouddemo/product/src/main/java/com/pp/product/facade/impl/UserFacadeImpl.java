package com.pp.product.facade.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pp.common.vo.ResultMessage;
import com.pp.product.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "fallback1")
    public ResultMessage timeout() {
        String url = "http://USER/hystrix/timeout";
        return restTemplate.getForObject(url,ResultMessage.class);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback2")
    public ResultMessage getExp(String msg) {
        String url = "http://USER/hystrix/exp/{msg}";

        return restTemplate.getForObject(url,ResultMessage.class,msg);
    }

    public ResultMessage fallback1(){
        return new ResultMessage("404","熔断请求超时",null);
    }

    public ResultMessage fallback2(String msg){
        return new ResultMessage("500",msg,null);
    }
}
