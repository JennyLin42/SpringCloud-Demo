package com.pp.fund.facade;

import com.pp.common.vo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 为啥这个东西这么像开发一个控制器~~
 */
@FeignClient("user")//这里意思是调用user的微服务 底层使用ribben进行调用user微服务
public interface UserFacade {

    @GetMapping("/user/info/{id}")
    public UserInfo getUser(@PathVariable("id") Long id);

    @PutMapping("/user/info")
    public UserInfo putUser(@RequestBody UserInfo userInfo);

}
