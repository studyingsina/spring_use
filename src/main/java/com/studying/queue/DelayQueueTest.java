package com.studying.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * @author ashraf
 */
public class DelayQueueTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Creates an instance of blocking queue using the DelayQueue.
        BlockingQueue<DelayObject> queue = new DelayQueue<DelayObject>();

        // Starting DelayQueue Producer to push some delayed objects to the queue
        new DelayQueueProducer(queue).start();

        // Starting DelayQueue Consumer to take the expired delayed objects from the queue
        new DelayQueueConsumer("Consumer Thread-1", queue).start();

    }

}
