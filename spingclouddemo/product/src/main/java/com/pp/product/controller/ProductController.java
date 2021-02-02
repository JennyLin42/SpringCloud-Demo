package com.pp.product.controller;

import com.pp.common.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    //http://localhost:7002/product/purchase/123/产品/12
    @GetMapping("/purchase/{userId}/{productId}/{amount}")
    public ResultMessage purchaseProduct(
            @PathVariable String userId,@PathVariable String productId,@PathVariable String amount
    ){
        String url = "http://FUND/fund/account/balance/{userId}/{amount}";
        //封装请求参数
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userId",userId);
        params.put("amount",amount);
        ResultMessage rm = restTemplate.postForObject(url,null,ResultMessage.class,params);
        System.out.println(rm.getMsg());
        return rm;
    }
}
