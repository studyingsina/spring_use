package com.studying.aop.inner;

import com.studying.util.LoggerUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class UserService {

    public void init() {
        LoggerUtil.logger.info("UserService init()");
    }

    public void add() {
        LoggerUtil.logger.info("UserService add()");
    }

}