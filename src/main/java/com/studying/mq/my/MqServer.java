package com.studying.mq.my;

/**
 * Created by junweizhang on 2019/8/22.
 */
public class MqServer {

    public static final int SUCCESS_RECEIVE = 1;
    public static final int SUCCESS_SEND = 2;
    public static final int SUCCESS_SEND_ACK = 3;

    public void start(){
        startSender();
        startReciver();
    }

    private void startReciver() {
    }

    private void startSender() {
    }

}
