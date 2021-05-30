package com.pp.user.controller;

import com.pp.common.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@Api("用户模块--测试hystrix")//注解api说明该类需要生成api文档
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    private static int MAX_SLEEP_TIME = 2000;

    //http://localhost:6001/hystrix/timeout
    //可能会发生超时
    @ApiOperation("测试超时请求，请求超时时hystrix是否有调用降级方法")
    @GetMapping("/timeout")
    public ResultMessage getTimeout() {
        System.out.println("有请求来了；timeout");
        Long sleepTime = (Long) (MAX_SLEEP_TIME * (new Double(Math.random())).longValue());
        try {
            Thread.sleep(sleepTime);
        } catch (Exception ex){
            System.out.println("执行异常");
        }
        return new ResultMessage("200","执行成功！",null);
    }

    //http://localhost:6001/hystrix/exp/spring
    //可能会发生参数异常
    @ApiOperation("测试异常请求，发生异常时hystrix是否有调用降级方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg",//参数名字
                    value = "spring?",//参数的描述
                    required = true,//是否必须传参数，true是
                    paramType = "path",//参数类型 path代表路径参数
                    dataType = "String")//参数类型 int
    })
    @GetMapping("/exp/{msg}")
    public ResultMessage getExp(@PathVariable("msg") String msg){
        System.out.println("有请求来了；getExp");
        if("spring".equals(msg)){
            return new ResultMessage("200","执行成功！",null);
        } else{
            throw new RuntimeException("传入参数有误");
        }
    }
}
