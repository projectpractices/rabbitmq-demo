package com.wenhao.rabbitmq.demo.recived;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recived002 {
    //定义队列名称
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection;
        try {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("39.104.50.29");
            connectionFactory.setPort(7007);
            connectionFactory.setUsername("rabbitmq");
            connectionFactory.setPassword("rabbitmq");
            connectionFactory.setVirtualHost("/");
            connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //交换机
                    String exchange = envelope.getExchange();
                    //路由key
                    String routingKey = envelope.getRoutingKey();
                    //消息标记
                    long deliveryTag = envelope.getDeliveryTag();
                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println("收到的消息为：" + message+"");
                    //int i = 1 / 0;
                    //System.out.println(i);
                }
            };

            /**
             * param1:队列名称
             * param2:是否自动应答
             * param3:回调方法
             */
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
