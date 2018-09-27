package com.yj.mq.listener;

import java.util.List;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yj.mq.consumer.MessageProcessor;

@Component
public class OrderMessageListener implements MessageListenerOrderly{
	
	@Autowired
	private MessageProcessor messageProcessor;

	@Override
	public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {//有序监听
		context.setAutoCommit(true); 
        for (MessageExt messageExt: msgs) {    
        	boolean result = messageProcessor.handleMessage(messageExt);
            if (!result) {
                return ConsumeOrderlyStatus.SUCCESS;
            }
        }   
        return ConsumeOrderlyStatus.SUCCESS;    
	}
}
