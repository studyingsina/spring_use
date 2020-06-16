package com.studying.mq.kafka;

import kafka.server.KafkaServerStartable;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by junweizhang on 2019/9/11.
 */
public class KafkaServerTester {

    @Test
    public void startCluster() throws Exception {
        String path = "/Users/junweizhang/workspace/me/studying/mq/kafka_2.12-2.3.0/config/server.properties";
        KafkaServerStartable server0 = startup(path);
        path = "/Users/junweizhang/workspace/me/studying/mq/kafka_2.12-2.3.0/config/server-1.properties";
        KafkaServerStartable server1 = startup(path);
        path = "/Users/junweizhang/workspace/me/studying/mq/kafka_2.12-2.3.0/config/server-2.properties";
        KafkaServerStartable server2 = startup(path);
        server0.awaitShutdown();
    }

    @Test
    public void startSingle() throws Exception {
        String path = "/Users/junweizhang/workspace/me/studying/mq/kafka_2.12-2.3.0/config/server.properties";
        startup(path);
    }

    @Test
    public void startOne() throws Exception {
        String path = "/Users/junweizhang/workspace/me/studying/mq/kafka_2.12-2.3.0/config/server-1.properties";
        startup(path);
    }

    @Test
    public void startTwo() throws Exception {
        String path = "/Users/junweizhang/workspace/me/studying/mq/kafka_2.12-2.3.0/config/server-2.properties";
        startup(path);
    }

    public KafkaServerStartable startup(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        Properties p = new Properties();
        p.load(is);
        is.close();
        KafkaServerStartable kafkaServerStartable = KafkaServerStartable.fromProps(p);
        kafkaServerStartable.startup();
        kafkaServerStartable.awaitShutdown();
        return kafkaServerStartable;
    }

}
