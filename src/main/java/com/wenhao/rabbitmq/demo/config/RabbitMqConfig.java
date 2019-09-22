package com.wenhao.rabbitmq.demo.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQConfig
 *
 * @blame Android Team
 */
@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    /**
     * 设置交换机
     *
     * @return Exchange
     */
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange settingExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    /**
     * 设置短信队列
     *
     * @return Queue
     */
    @Bean(QUEUE_INFORM_SMS)
    public Queue settingQueueSms() {
        return new Queue(QUEUE_INFORM_SMS);
    }

    /**
     * 设置邮件队列
     *
     * @return Queue
     */
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue settingQueueEmail() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    /**
     * 短信队列绑定至交换机
     *
     * @param queue    队列
     * @param exchange 交换机
     * @return Binding
     */
    @Bean
    public Binding bingQueueSms(@Qualifier(QUEUE_INFORM_SMS) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
    }

    /**
     * Email队列绑定至交换机
     *
     * @param queue    队列
     * @param exchange 交换机
     * @return Binding
     */
    @Bean
    public Binding bingQueueEmail(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
    }
}
