package com.whl.demo.test;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @program: rocketmq-springboot
 * @description:
 * @author: Mr.Wang
 * @create: 2018-09-26 16:49
 **/
public class TestCreateTopic {

    public static void main(String[] args) throws UnsupportedEncodingException, MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.0.69:9876");
        producer.createTopic("CSDN","CSDN",1);
    }
}
