package com.studying.aop.inner;

import com.studying.util.LoggerUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by junweizhang on 17/9/11.
 */
@Service
@Lazy
public class QueryService {

    public void query(){
        LoggerUtil.logger.info("QueryService query()");
    }

}
