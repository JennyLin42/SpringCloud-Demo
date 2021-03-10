package com.pp.product.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.pp.common.vo.ResultMessage;
import com.pp.common.vo.UserInfo;
import com.pp.product.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    //http://localhost:7001/cr/exp/spring
    @GetMapping("/exp/{msg}")
    public ResultMessage getExp(@PathVariable("msg") String msg){
        return userFacade.getExp(msg);
    }

    //http://localhost:7001/cr/asyncTimeout
    @GetMapping("/asyncTimeout")
    public ResultMessage asyncTimeout(){
        Future<ResultMessage> future= userFacade.asyncTimeout();
        ResultMessage rm = null;//只有调用get的时候才真正发送命令
        try {
            rm = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rm;
    }

    //http://localhost:7001/cr/listResult
    //懒得打开postman了，偷懒做法
    @GetMapping("/listResult")
    public List<ResultMessage> listResultCommand(){
        String[] params = {"spring","mvc"};
        List<ResultMessage> resList = new ArrayList<>();
        Observable<ResultMessage> observable= userFacade.userExpCommond(params);
        observable.forEach((ResultMessage resultMsg) ->{
            resList.add(resultMsg);
        });
        return resList;
    }

    /**
     * 测试缓存
     * @return
     */
    //http://localhost:7001/cr/user/info/cache/1
    @GetMapping("/user/info/cache/{id}")
    public ResultMessage getUserInfo(@PathVariable Long id){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try{
            //userFacade.getUserInfo(id);
            //userFacade.getUserInfo(id);
            //UserInfo userInfo = new UserInfo(id,"user_name_update","note_update");
            //userFacade.updateUserInfo(userInfo);
            return userFacade.getUserInfo2(id);
        } finally {
            context.close();
        }
    }

    @GetMapping("/dealFile")
    public ResultMessage dealFile(String filePath){
        return userFacade.dealFile(filePath);
    }
}
