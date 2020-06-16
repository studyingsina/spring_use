package com.studying.mq.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by junweizhang on 2019/8/22.
 */
public class KafkaProducerTester {

    public static final String TOPIC = "test-topic";

    @Test
    public void send() throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 1000);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        try {
            for (int i = 10; i < 100000; i++) {
                Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(TOPIC, "msg-" + i));
                System.out.println(future.get().toString());
            }
            List<PartitionInfo> partitions = new ArrayList<PartitionInfo>();
            partitions = producer.partitionsFor(TOPIC);
            for (PartitionInfo p : partitions) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }


}
