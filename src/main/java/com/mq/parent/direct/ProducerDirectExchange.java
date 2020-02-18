package com.mq.parent.direct;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerDirectExchange {

    private static final String exchangeName = "test_direct_exchange";

    private static final String routeKey = "test.direct";


    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        String msg = "this is direct exchange test msg";

        channel.basicPublish(exchangeName, routeKey, null ,msg.getBytes());

        System.out.println("direct 生产消息。。。。");
        channel.close();
        connection.close();
    }

}
