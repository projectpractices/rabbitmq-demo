package com.wenhao.rabbitmq.demo.handler;

import com.rabbitmq.client.Channel;
import com.wenhao.rabbitmq.demo.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ReceiveHandler
 *
 * @blame Android Team
 */
@Component
public class ReceiveHandler {

    @RabbitListener(queues = RabbitMqConfig.QUEUE_INFORM_SMS)
    public void receivedSms(String msg, Message message, Channel channel) {
        System.out.println(msg);
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_INFORM_EMAIL)
    public void receivedEmail(String msg, Message message, Channel channel) {
        System.out.println(msg);
    }
}
