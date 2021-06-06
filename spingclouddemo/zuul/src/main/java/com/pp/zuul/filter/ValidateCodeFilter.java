package com.pp.zuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.pp.common.vo.ResultMessage;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ValidateCodeFilter extends ZuulFilter {

    // 验证码键和值的参数名称
    private final static String  VALIDATE_KEY_PARAM_NAME = "validateKey";
    private final static String  VALIDATE_CODE_PARAM_NAME = "validateCode";

    //过滤器类型
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    //过滤器的顺序
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 15;
    }

    //是否执行过滤器
    @Override
    public boolean shouldFilter() {
        //获取请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        if(ctx.getRequestQueryParams() == null){
            return false;
        }
        // 是否存在对应的参数
        return ctx.getRequestQueryParams()
                .containsKey(VALIDATE_CODE_PARAM_NAME)
                && ctx.getRequestQueryParams()
                .containsKey(VALIDATE_KEY_PARAM_NAME);
    }

    //真正的过滤器内容
    @Override
    public Object run() throws ZuulException { // 过滤器逻辑 ④
        // 获取请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取请求参数验证key
        String validateKey
                =  ctx.getRequest().getParameter(VALIDATE_KEY_PARAM_NAME);
        // 请求参数验证码
        String validateCode
                =  ctx.getRequest().getParameter(VALIDATE_CODE_PARAM_NAME);
        // 这里设置一个验证码
        String redisValidateCode
                = "123";
        // 如果两个验证码相同，就放行
        if (validateCode.equals(redisValidateCode)) {
            return null;// 放行
        }
        // 不再放行路由，逻辑到此为止
        ctx.setSendZuulResponse(false);// ⑤ 这里最重要，设置zuul逻辑终止
        // 设置响应码为401-未签名
        ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        // 设置响应类型
        ctx.getResponse()
                .setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 响应结果
        ResultMessage result
                = new ResultMessage("失败", "验证码错误，请检查您的输入");
        // 将result转换为JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        String body = null;
        try {
            body = mapper.writeValueAsString(result); // 转变为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 设置响应体
        ctx.setResponseBody(body);
        return null;
    }

}
