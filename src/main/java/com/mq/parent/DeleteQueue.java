package com.mq.parent;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DeleteQueue {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //channel.queueDelete("test");
        channel.queueDelete("test_work_queue");
        //channel.exchangeDelete("test_topic_exchange");
        //channel.exchangeDelete("test_topic_exchange");

        channel.close();
        connection.clearBlockedListeners();
    }
}
