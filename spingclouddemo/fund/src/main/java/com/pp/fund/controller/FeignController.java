package com.pp.fund.controller;

import com.pp.common.vo.UserInfo;
import com.pp.fund.facade.UserFacade;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Resource
    private UserFacade userFacade = null;

    //测试openFeign
    //http://localhost:8001/feign/user/12
    @GetMapping("/user/{id}")
    public UserInfo getUser(@PathVariable("id") Long id){
        UserInfo user = userFacade.getUser(id);
        return user;
    }

    //http://localhost:8001/feign/user/1/lpp/linpiaopiao
    @GetMapping("/user/{id}/{userName}/{note}")
    public UserInfo updateUser(@PathVariable("id") Long id,@PathVariable("userName") String userName,@PathVariable("note") String note){
        UserInfo userInfo = new UserInfo(id,userName,note);
        return userFacade.putUser(userInfo);
    }
}
