package com.mq.parent.workqueue;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 想要做到公平分发（fair dispatch），必须关闭自动应答ack，改成手动应答。
 * 使用basicQos(perfetch=1)限制每次只发送不超过1条消息到同一个消费者，消费者必须手动反馈告知队列，才会发送下一个。

 */
public class FairSendWorkQueue {

    private static final String queueName = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false, null);

        //每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息。
        channel.basicQos(1);

        for (int i=1; i<50; i++) {
            String msg = "hello work queue, i = " + i;
            channel.basicPublish("", queueName, null, msg.getBytes());
            System.out.println("Mq send: " + msg);
            Thread.sleep(10);
        }

        channel.close();
        connection.close();


    }
}
