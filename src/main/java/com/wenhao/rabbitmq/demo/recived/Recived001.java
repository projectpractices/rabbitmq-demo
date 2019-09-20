package com.wenhao.rabbitmq.demo.recived;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recived001 {
    //定义队列名称
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory;
        Connection connection;
        try {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("11.0.0.184");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("rabbitmq");
            connectionFactory.setPassword("rabbitmq");
            connectionFactory.setVirtualHost("/");
            connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                }
            };

            /**
             * param1:队列名称
             * param2:是否自动应答
             * param3:回调方法
             */
            channel.basicConsume(QUEUE_NAME,true,consumer);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
