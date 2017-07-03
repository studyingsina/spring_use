package com.studying.job;

import com.cip.crane.client.spring.annotation.Crane;

/**
 * Created by junweizhang on 17/6/3.
 */
public class CraneJobTester {

    @Crane("crane-demo")    //crane-demo为在管理平台新建的任务名
    public void testAnnotation(){   //如果有参数，可通过新建任务时的方法参数传递到客户端
        // log.info("this is crane task");
        //实现自己的任务逻辑
    }

}
