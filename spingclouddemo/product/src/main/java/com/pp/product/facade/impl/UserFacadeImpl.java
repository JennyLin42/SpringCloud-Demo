package com.pp.product.facade.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.pp.common.vo.ResultMessage;
import com.pp.common.vo.UserInfo;
import com.pp.product.facade.UserFacade;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.channels.FileLockInterruptionException;
import java.util.concurrent.Future;

@Service
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 同步发射一次请求
     * 超时熔断
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallback1")
    public ResultMessage timeout() {
        String url = "http://USER/hystrix/timeout";
        return restTemplate.getForObject(url,ResultMessage.class);
    }

    /**
     * 同步发射一次请求
     * 异常熔断
     * @param msg
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallback2")
    public ResultMessage getExp(String msg) {
        String url = "http://user/hystrix/exp/{msg}";

        return restTemplate.getForObject(url,ResultMessage.class,msg);
    }

    /**
     * 异步发射一次请求
     * 超时熔断
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback1")
    public Future<ResultMessage> asyncTimeout(){
        return new AsyncResult<ResultMessage>() {
            @Override
            public ResultMessage invoke() {
                String url = "http://USER/hystrix/timeout";
                return restTemplate.getForObject(url,ResultMessage.class);
            }
        };
    }

    /**
     * 热观察者发送多次请求
     * 把EAGER改成LAZY就是冷观察者了
     * 异常熔断
     * @param params
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback3",
    observableExecutionMode = ObservableExecutionMode.EAGER)
    public Observable<ResultMessage> userExpCommond(String[] params){
        String url = "http://USER/hystrix/exp/{msg}";
        Observable.OnSubscribe<ResultMessage> onSubs = (resSubs) ->{
            try{
                int count = 0;//计数器
                if(!resSubs.isUnsubscribed()){
                    for(String param : params){
                        count ++;
                        System.out.println("第【"+count+"】次发送");
                        ResultMessage resultMessage = restTemplate.getForObject(url,ResultMessage.class,param);
                        resSubs.onNext(resultMessage);
                    }
                    resSubs.onCompleted();
                }
            } catch (Exception e){
                resSubs.onError(e);
            }
        };
        return Observable.create(onSubs);
    }

    /**
     * 同步发射一次请求
     * 异常熔断
     * 定义忽略异常
     * @param filePath
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallback4",
            //忽略这些异常
            ignoreExceptions = {FileNotFoundException.class, FileLockInterruptionException.class,NullPointerException.class})
    public ResultMessage dealFile(String filePath) {
        if(filePath == null){
            throw new NullPointerException();
        }
        File file = new File(filePath);
        file.getAbsolutePath();
        return new ResultMessage("200","文件存在",null);
    }

    /**
     * 保存HYstrix缓存
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback5")
    @CacheResult  //将结果缓存起来
    public ResultMessage getUserInfo(Long id){
        String url = "http://localhost:6001/user/info/{id}";
        UserInfo userInfo = restTemplate.getForObject(url,UserInfo.class,id);
        System.out.println("获取对象:"+id);
        return new ResultMessage("200","获取user成功",userInfo.toString());
    }

    @HystrixCommand(fallbackMethod = "fallback5")
    public ResultMessage getUserInfo2(Long id){
        String url = "http://USER/user/info/{id}";
        UserInfo userInfo = restTemplate.getForObject(url,UserInfo.class,id);
        System.out.println("获取对象:"+id);
        return new ResultMessage("200","获取user成功",userInfo.toString());
    }

    /**
     * 移除hystrix缓存
     * @return
     */
    @HystrixCommand
    @CacheRemove(commandKey = "getUserInfo")//commanKey配置的是上面的CacheResult
    public ResultMessage updateUserInfo(@CacheKey("id") UserInfo userInfo){
        String url = "http://USER/user/info";
        //请求头
        HttpHeaders headers = new HttpHeaders();
        //封装请求实体对象 将用户信息对象设置成请求体
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //封装实体对象
        HttpEntity<UserInfo> request = new HttpEntity<>(userInfo,headers);
        System.out.println("执行更新对象"+userInfo.getId());
        restTemplate.put(url,request);
        return new ResultMessage("200","获取user成功",userInfo.toString());
    }




    public ResultMessage fallback1(){
        return new ResultMessage("404","timeout"+"熔断请求超时",null);
    }

    public ResultMessage fallback2(String msg){
        return new ResultMessage("500","getExp"+msg,null);
    }

    public ResultMessage fallback3(String[] params){
        return new ResultMessage("501","userExpCommond调用参数异常"+params,null);
    }

    public ResultMessage fallback4(String msg,Throwable ex){
        ex.printStackTrace();
        return new ResultMessage("600","dealFile获取异常",null);
    }

    public ResultMessage fallback5(Long id){
        return new ResultMessage("500","getUserInfo降级方法",null);
    }
}
