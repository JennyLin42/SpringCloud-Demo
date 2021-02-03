package com.pp.product.facade;

import com.pp.common.vo.ResultMessage;

public interface UserFacade {

    public ResultMessage timeout();

    public ResultMessage getExp(String exp);
}
