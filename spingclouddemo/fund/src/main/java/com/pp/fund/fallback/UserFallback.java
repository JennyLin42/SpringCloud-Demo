package com.pp.fund.fallback;

import com.pp.common.vo.ResultMessage;
import com.pp.common.vo.UserInfo;
import com.pp.fund.facade.UserFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class UserFallback implements UserFacade {
    @Override
    public UserInfo getUser(Long id) {
        return new UserInfo(null,null,null);
    }

    @Override
    public UserInfo putUser(UserInfo userInfo) {
        return new UserInfo(null,null,null);
    }

    @Override
    public ResponseEntity<List<UserInfo>> findUsers2(Long[] ids) {
        return null;
    }

    @Override
    public ResultMessage deleteUser(Long id) {
        return new ResultMessage("","降级服务");
    }

    @Override
    public ResultMessage uploadFile(MultipartFile file) {
        return new ResultMessage("","降级服务");
    }

    @Override
    public ResultMessage getTimeout() {
        return new ResultMessage("","降级服务");
    }
}
