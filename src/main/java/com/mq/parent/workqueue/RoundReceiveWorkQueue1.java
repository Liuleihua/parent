package com.mq.parent.workqueue;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoundReceiveWorkQueue1 {
    private static final String queueName = "test_round_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        System.out.println("RoundReceiveWorkQueue1 start...");
        channel.queueDeclare(queueName,false,false,false, null);


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("1、recevice msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("1、receive msg done ");
                }
            }
        };

        channel.basicConsume(queueName,true, consumer);
    }


}
