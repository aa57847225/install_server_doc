package com.yj.mq.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessor {
	
	@Value("${server.port}")  
    private String port; 

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

	public Boolean handleMessage(MessageExt messageExt) {
		try {
			String body = null;
			// 消费者消费
			if (null == messageExt || null == messageExt.getBody()) {
				LOGGER.info("消息体为空");
				return false;
			}
			body = new String(messageExt.getBody());
			LOGGER.info("port:"+port+",消费Tag:" + messageExt.getTags() + ",body:" + body);
			//Order order = JSON.parseObject(body, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}