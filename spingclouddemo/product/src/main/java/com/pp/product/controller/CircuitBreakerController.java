package com.pp.product.controller;

import com.pp.common.vo.ResultMessage;
import com.pp.product.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cr")
public class CircuitBreakerController {

    @Autowired
    UserFacade userFacade;

    //http://localhost:7001/cr/timeout
    @GetMapping("/timeout")
    public ResultMessage getTimeout(){
        return userFacade.timeout();
    }

    @GetMapping("/exp/{msg}")
    public ResultMessage getExp(@PathVariable("msg") String msg){
        return userFacade.getExp(msg);
    }
}
