package com.mq.parent.workqueue;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FairReceiveWorkQueue2 {

    private static  final String queueName = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.basicQos(1);//保证一次只分发一个消息

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("2、recevice msg:" + msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("receive msg done");
                    //手动回执消息
                    channel.basicAck(envelope.getDeliveryTag(), false);

                }
            }
        };

        channel.basicConsume(queueName, false, consumer);
    }
}
