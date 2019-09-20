package com.wenhao.rabbitmq.demo.send;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列模式
 */
public class SendPublishSubscribe001 {

    /**
     * 队列名称
     */
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
            //创建工厂连接
            ConnectionFactory factory = new ConnectionFactory();
            //设置队列服务器地址
            factory.setHost("39.104.50.29");
            //设置端口
            factory.setPort(7007);
            //设置用户名
            factory.setUsername("rabbitmq");
            //设置密码
            factory.setPassword("rabbitmq");
            //设置虚拟主机
            factory.setVirtualHost("/");
            //创建连接
            connection = factory.newConnection();
            //创建与exchange的通道,每个连接可以创建多个通道,每个通道代表一个会话任务
            channel = connection.createChannel();
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
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            /**
             * 队列和交换机绑定
             * param1:队列名称
             * param2:交换机名称
             */
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_FANOUT_INFORM, "");
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "");
            /**
             * 发布消息
             * 发布到不存在的交换机将会收到一个通道级别的协议异常,从而导致关闭改通道
             *  param1:消息发送至哪个交换机
             *  param2:路由key,交换机把消息转发至对应的队列
             *  param3:消息的属性
             *  param4:消息体
             */
            for (int i = 0; i < 10; i++) {
                String message = "hello rabbitmq"+ i;
                channel.basicPublish(EXCHANGE_FANOUT_INFORM, "", null, message.getBytes());
                System.out.println("发送的消息为:=" + message);
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }
}
