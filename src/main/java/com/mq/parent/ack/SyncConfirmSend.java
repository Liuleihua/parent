package com.mq.parent.ack;

import com.mq.parent.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class SyncConfirmSend {

    private static String queueName = "test_sync_confirm_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);


        channel.confirmSelect();
        //未确认的消息放入
        SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long delivery, boolean mul) throws IOException {
                if (mul) {
                    System.out.println("handle ack mul true");
                    confirmSet.headSet(delivery + 1).clear();
                } else{
                    System.out.println("handle ack mul single");
                    confirmSet.remove(delivery);
                }
            }

            @Override
            public void handleNack(long delivery, boolean mul) throws IOException {
                if (mul) {
                    System.out.println("handle nack mul true");
                    confirmSet.headSet(delivery + 1).clear();
                } else{
                    System.out.println("handle nack mul single");
                    confirmSet.remove(delivery);
                }
            }
        });

        String msg = "hello world , rabbitmq sync confirm demo";
        while (true) {
            long delivery = channel.getNextPublishSeqNo();
            channel.basicPublish("", queueName, null, msg.getBytes());
            confirmSet.add(delivery);
        }
    }
}
