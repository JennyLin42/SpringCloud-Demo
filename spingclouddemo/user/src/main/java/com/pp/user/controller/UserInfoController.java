package com.pp.user.controller;

import com.pp.common.vo.ResultMessage;
import com.pp.common.vo.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取用户信息列表
     * @param ids
     * @return
     */
    @GetMapping("/infoes2")
    public ResponseEntity<List<UserInfo>> findUsers(@RequestParam("ids") Long [] ids){
        List<UserInfo> userList = new ArrayList<>();
        for(Long id : ids){
            UserInfo userInfo = new UserInfo(id,"user_name_"+id,"note_"+id);
            userList.add(userInfo);
        }
        ResponseEntity<List<UserInfo>> response = new ResponseEntity<>(userList, HttpStatus.OK);
        return response;
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping("/info")
    public ResultMessage deleteUser(@RequestHeader("id") Long id){
        boolean success = id != null;
        String msg = success ? "传递成功" : "传递失败";
        return new ResultMessage(id+"",msg);
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResultMessage uploadFile(@RequestPart("file")MultipartFile file){
        boolean success = file != null && file.getSize() > 0 ;
        String msg = success ? "传递成功" : "传递失败";
        return new ResultMessage(file.getName(),msg);
    }

}
