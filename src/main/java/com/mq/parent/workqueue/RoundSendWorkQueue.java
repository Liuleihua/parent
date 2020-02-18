package com.mq.parent.workqueue;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 两个消费者同时监听一个的队列。
 * 其中一个线程sleep2秒，另一个消费者线程sleep1秒，但是处理的消息是一样多。
 * 这种方式叫轮询分发（round-robin）不管谁忙，都不会多给消息，总是你一个我一个。
 */
public class RoundSendWorkQueue {

    private static final String queueName = "test_round_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false, null);

        for (int i=1; i<50; i++) {
            String msg = "hello round work queue, i = " + i;
            channel.basicPublish("", queueName, null, msg.getBytes());
            System.out.println("Mq send: " + msg);
            Thread.sleep(10);
        }

        channel.close();
        connection.close();


    }
}
