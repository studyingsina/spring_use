package com.studying.aop;

import com.studying.aop.inner.QueryService;
import com.studying.util.LoggerUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Recover the bug : https://jira.spring.io/browse/SPR-14241
 *
 * For recover this bug: add a breakpoint in @see AbstractAspectJAdvice 621 line, for example:
 * <code>
 *     return this.aspectJAdviceMethod.invoke(this.aspectInstanceFactory.getAspectInstance(), actualArgs);
 * </code>
 * and let thread-2 go into getAspectInstance() and gaps the lock, but don't create bean; then switch to thread-1 in the IDE debug view,
 * and let thread-1 which has gapped the spring singleton map lock execute afterPropertiesSet() method and
 * initialize the same aspect @see Operator, thread-1 will wait the lock when it get into getAspectInstance().
 *
 */
public class AopDeadlockTester {

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
//        t1.start();
        LoggerUtil.logger.info("ctx instance : {}", ctx);
    }

}