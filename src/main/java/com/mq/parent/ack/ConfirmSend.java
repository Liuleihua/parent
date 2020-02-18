package com.mq.parent.ack;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 普通confirm模式，发送一条或多条后调用waitConfirm
 */
public class ConfirmSend {

    private static String queueName = "test_confirm1_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        String msg = "hello world , rabbitmq common confirm demo";

        channel.confirmSelect();
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", queueName, null, msg.getBytes());
        }
        if (!channel.waitForConfirms()) {
            System.out.println("发送失败");
        } else {
            System.out.println("发送成功");
        }

        channel.close();
        connection.close();
    }
}
