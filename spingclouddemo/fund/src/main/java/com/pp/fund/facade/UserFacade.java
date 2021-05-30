package com.pp.fund.facade;

import com.pp.common.vo.ResultMessage;
import com.pp.common.vo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 为啥这个东西这么像开发一个控制器~~
 */
@FeignClient("user")//这里意思是调用user的微服务 底层使用ribben进行调用user微服务
public interface UserFacade {

    @GetMapping("/user/info/{id}")
    public UserInfo getUser(@PathVariable("id") Long id);

    @PutMapping("/user/info")
    public UserInfo putUser(@RequestBody UserInfo userInfo);

    //根据ids获取用户列表
    @GetMapping("/user/infoes2")
    public ResponseEntity<List<UserInfo>> findUsers2(@RequestParam("ids") Long [] id);

    @DeleteMapping("/user/info")
    public ResultMessage deleteUser(@RequestHeader("id") Long id);

    /**
     * MULTIPART_FORM_DATA_VALUE 标识传递一个“nultipart/form-data"类型表单
     * RequestPart 代表传递文件流
     * @param file
     * @return
     */
    @RequestMapping(value = "/user/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultMessage uploadFile(@RequestPart MultipartFile file);

}
