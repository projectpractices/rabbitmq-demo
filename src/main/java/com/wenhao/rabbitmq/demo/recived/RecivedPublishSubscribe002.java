package com.wenhao.rabbitmq.demo.recived;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RecivedPublishSubscribe002 {
    /**
     * 队列名称
     */
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

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
            /**
             * 声明交换机
             * param1:交换机名称
             * param2:交换机类型
             */
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            /**
             * 声明队列:如果rabbitmq中没有 将会自动创建
             * param1:队列名称
             * param2:是否持久化
             * param3:是否独占此连接
             * param4:队列不再使用时是否自动删除
             * param4:队列参数
             */
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            /**
             * 队列和交换机绑定
             * param1:队列名称
             * param2:交换机名称
             */
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "");
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
                }
            };

            /**
             * param1:队列名称
             * param2:是否自动应答
             * param3:回调方法
             */
            channel.basicConsume(QUEUE_INFORM_SMS, true, consumer);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
