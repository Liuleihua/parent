package com.mq.parent.topic;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTopicExchange {
    private static final String exchangeName = "test_topic_exchange";
    private static final String exchangeType = "topic";
    private static final String queueName = "test_topic_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, exchangeType);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, "user.#");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("topic 消费消息： " + msg);
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
