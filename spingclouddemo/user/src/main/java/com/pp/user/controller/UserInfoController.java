package com.pp.user.controller;

import com.pp.common.vo.UserInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    //模拟根据ID查询用户
    //http://localhost:6001/user/info/1
    @GetMapping("/info/{id}")
    public UserInfo getUser(@PathVariable("id") Long id){
        System.out.println("getUser");
        UserInfo userInfo = new UserInfo(id,"user_name_"+id,"note_"+id);
        return userInfo;
    }

    //模拟添加用户
    @PutMapping("/info")
    public UserInfo putUser(@RequestBody UserInfo userInfo){
        System.out.println("putUser");
        return userInfo;
    }
}
