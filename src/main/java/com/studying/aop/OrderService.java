package com.studying.aop;

import com.studying.aop.inner.UserService;
import com.studying.util.LoggerUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by junweizhang on 17/9/11.
 */
@Service
@Lazy
public class OrderService implements InitializingBean{

    @Autowired
    private UserService userService;


    @Override
    public void afterPropertiesSet() throws Exception {
        LoggerUtil.logger.info("OrderService init.");
        userService.init();
    }

    /**
     * 创建订单.
     */
    public void createOrder() {
        LoggerUtil.logger.info("createOrder success");
    }

}
