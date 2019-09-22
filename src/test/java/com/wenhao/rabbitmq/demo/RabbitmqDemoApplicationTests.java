package com.wenhao.rabbitmq.demo;

import com.wenhao.rabbitmq.demo.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqDemoApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendByTopics() {
        for (int i = 0; i < 10; i++) {
            String message = "sms email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM, "inform.sms.email", message);
            System.out.println("Send Message is:'" + message);
        }
    }

}
