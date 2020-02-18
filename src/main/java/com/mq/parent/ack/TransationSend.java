package com.mq.parent.ack;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TransationSend {

    private static String queueName = "test_transation_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        String msg = "hello world , rabbitmq transaction demo";

        try {
            channel.txSelect();
            int i = 1/0;
            channel.basicPublish("", queueName, null, msg.getBytes());
            channel.txCommit();
        } catch (Exception e) {
            System.out.println("transaction error");
            channel.txRollback();
        }

        channel.close();;

        connection.close();
    }
}
