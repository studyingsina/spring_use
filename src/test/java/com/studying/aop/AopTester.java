package com.studying.aop;

import com.studying.aop.inner.QueryService;
import com.studying.util.LoggerUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Recover the bug : https://jira.spring.io/browse/SPR-14241
 */
public class AopTester {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:core/bean-aop.xml");
        LoggerUtil.logger.info("ctx instance : {}", ctx);

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                OrderService orderService = (OrderService) ctx.getBean("orderService");
                orderService.createOrder();
            }

        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                QueryService queryService = (QueryService) ctx.getBean("queryService");
                queryService.query();
            }

        });

        t2.start();
        t1.start();
        LoggerUtil.logger.info("ctx instance : {}", ctx);
    }

}