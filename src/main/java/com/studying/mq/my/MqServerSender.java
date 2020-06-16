package com.studying.mq.my;

/**
 * Created by junweizhang on 2019/8/22.
 */
public class MqServerSender {

    public int send(){
        // accept event
        // select mq table
        // save send_receive_mq_relation table
        // send
        // update send_receive_mq_relation status
        return MqServer.SUCCESS_SEND;
    }

}
