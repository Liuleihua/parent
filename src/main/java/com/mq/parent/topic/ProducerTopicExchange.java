package com.mq.parent.topic;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerTopicExchange {

    private static final String exchangeName = "test_topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        String msg = "this is topic exchange test msg";

        channel.basicPublish(exchangeName, "user.add", null ,msg.getBytes());
        channel.basicPublish(exchangeName, "user.delete", null ,msg.getBytes());
        channel.basicPublish(exchangeName, "user.find.query", null ,msg.getBytes());
        channel.basicPublish(exchangeName, "user.find", null ,msg.getBytes());
        channel.basicPublish(exchangeName, "user.find.new", null ,msg.getBytes());
        channel.basicPublish(exchangeName, "user.update", null ,msg.getBytes());
        System.out.println("topic 生产消息。。。。");
        channel.close();
        connection.clearBlockedListeners();
    }
}
