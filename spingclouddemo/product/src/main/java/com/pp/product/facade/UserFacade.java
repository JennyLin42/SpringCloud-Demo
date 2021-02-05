package com.pp.product.facade;

import com.pp.common.vo.ResultMessage;
import rx.Observable;

import java.util.concurrent.Future;

public interface UserFacade {

    public ResultMessage timeout();

    public ResultMessage getExp(String exp);

    public Future<ResultMessage> asyncTimeout();

    public Observable<ResultMessage> userExpCommond(String[] param);

    public ResultMessage dealFile(String filePath);}
