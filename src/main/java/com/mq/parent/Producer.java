package com.mq.parent;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static String queueName = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);

        String msg = "hello world , rabbitmq simple demo";

        channel.basicPublish("", queueName, null, msg.getBytes());

        System.out.println("msg send ");

        channel.close();;

        connection.clearBlockedListeners();
    }
}
