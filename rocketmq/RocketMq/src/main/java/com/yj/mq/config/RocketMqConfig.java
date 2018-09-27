package com.yj.mq.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMqConfig {
    @Value("${rocketmq.producer.groupName}")
    private String producerGroupName;
    @Value("${rocketmq.consumer.groupName}")
    private String consumerGroupName;
    @Value("${rocketmq.consumer.order.groupName}")
    private String consumerOrderGroupName;
    @Value("${rocketmq.namesrv.addr}")
    private String nameServerAddress;
    @Value("${rocketmq.topic}")
    private String topic;
    @Value("${rocketmq.order.topic}")
    private String orderTopic;
    
    @Autowired
    private MessageListenerConcurrently messageListener;
    
    @Autowired
    private MessageListenerOrderly orderMessageListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQProducer producer() {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroupName);
        //VipChannel阿里内部使用版本才用，开源版本没有，默认为true，占用10909端口，此时虚拟机需要开放10909端口，否则会报 ：connect to <：10909> failed异常，可以直接设置为false
        //producer.setVipChannelEnabled(false);
        producer.setNamesrvAddr(nameServerAddress);
        return producer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer consumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroupName);
        consumer.setNamesrvAddr(nameServerAddress);
        consumer.setMaxReconsumeTimes(0);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(messageListener);
        //consumer.setMessageModel(MessageModel.CLUSTERING);集群模式(默认)
        //consumer.setMessageModel(MessageModel.BROADCASTING);//广播模式
        try {
            consumer.subscribe(topic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return consumer;
    }
    
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer orderConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerOrderGroupName);
        consumer.setNamesrvAddr(nameServerAddress);
        consumer.setMaxReconsumeTimes(0);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //consumer.setMessageModel(MessageModel.CLUSTERING);集群模式(默认)
        //consumer.setMessageModel(MessageModel.BROADCASTING);//广播模式
        consumer.registerMessageListener(orderMessageListener);
        try {
            consumer.subscribe(orderTopic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return consumer;
    }
}
