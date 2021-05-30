package com.pp.fund.controller;

import com.pp.common.vo.ResultMessage;
import com.pp.common.vo.UserInfo;
import com.pp.fund.facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@RestController
@RequestMapping("/feign")
@Api("测试openFeign")
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
    @ApiOperation("")
    @GetMapping("/user/{id}/{userName}/{note}")
    public UserInfo updateUser(@PathVariable("id") Long id,@PathVariable("userName") String userName,@PathVariable("note") String note){
        UserInfo userInfo = new UserInfo(id,userName,note);
        return userFacade.putUser(userInfo);
    }

    //http://localhost:8001/feign/users
    @GetMapping("/users/{ids}")
    public ResponseEntity<List<UserInfo>> findUsers2(@PathVariable("ids") Long []ids) {
        return userFacade.findUsers2(ids);
    }

    @GetMapping("/user/delete/{id}")
    public ResultMessage deleteUser(@PathVariable("id") Long id) {
        return userFacade.deleteUser(id);
    }


    @PostMapping("/user/file/upload")
    public ResultMessage uploadFile(@RequestPart("file") MultipartFile file) {
        return userFacade.uploadFile(file);
    }
}
