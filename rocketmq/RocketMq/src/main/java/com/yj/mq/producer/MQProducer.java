package com.yj.mq.producer;

import java.util.List;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yj.mq.entity.BusinessException;

@Component
public class MQProducer {
	private static final Logger logger = LoggerFactory.getLogger(MQProducer.class);
	
	@Autowired
	private DefaultMQProducer producer;

	public SendResult syncSend(Message message) throws BusinessException {
		SendResult result = null;
		try {
			result = producer.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("发送同步消息发生异常");
		}
		return result;
	}

	public void asyncSend(Message message) throws BusinessException {
		try {
			producer.send(message, new SendCallback() {
				@Override
				public void onSuccess(SendResult result) {
					logger.info("消息发布成功,messageId：" + result.getMsgId());
				}

				@Override
				public void onException(Throwable throwable) {
					logger.error("消息发布失败：" + throwable.getMessage(), throwable);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("发送异步消息发生异常");
		}
	}

	public SendResult sendOrder(Message message, Long orderId) throws BusinessException {
		SendResult result = null;
		try {
			result = producer.send(message, new MessageQueueSelector() {
				@Override
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
					Long id = (Long) arg;
					long index = id % mqs.size();
					return mqs.get((int) index);
				}
			}, orderId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("发送有序消息发生异常");
		}
		return result;
	}
}
