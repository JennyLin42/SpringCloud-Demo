package com.pp.fund.controller;

import com.pp.common.vo.ResultMessage;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 资金conrtroller
 */
@RestController
@RequestMapping("/fund")
public class AccountController {

    //http://localhost:8002/fund/account/balance/123/12
    //扣除用户佣金
    @RequestMapping("/account/balance/{userid}/{amount}")
    public ResultMessage deductingBalance(@PathVariable("userid") String userid, @PathVariable("amount") String amount, HttpServletRequest request){
        String message = "端口:【"+request.getServerPort()+"】的【"+userid+"】扣减【"+amount+"】成功";
        ResultMessage rm = new ResultMessage("200",message,null);
        return rm;
    }
}
