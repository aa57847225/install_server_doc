package com.yj.mq.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.yj.mq.entity.BusinessException;
import com.yj.mq.entity.Order;
import com.yj.mq.producer.MQProducer;

@RestController
public class MQController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQController.class);

	@Autowired
	private MQProducer producer;

	@Value("${rocketmq.topic}")
	private String topic;

	@Value("${rocketmq.order.topic}")
	private String orderTopic;

	@RequestMapping("/send")
	public SendResult send(@RequestBody String desc) {
		SendResult result = null;
		try {
			Order order = new Order();
			order.setOrderId(1L);
			order.setDesc(desc);
			String userStr = JSON.toJSONString(order);

			Message message = new Message();
			message.setTopic(topic);
			message.setBody(userStr.getBytes());
			message.setTags(desc + "_tag");
			result = producer.syncSend(message);
			LOGGER.info("SendResult:" + result);
		} catch (BusinessException e) {
			LOGGER.info("e:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/sendlist")
	public SendResult sendlist(@RequestBody String name) {
		SendResult result = null;
		try {
			List<Order> orderList = buildOrders();
			for (int i = 0; i < orderList.size(); i++) {
				String body = JSON.toJSONString(orderList.get(i));
				Message msg = new Message(topic, body.getBytes());
				result = producer.syncSend(msg);
				LOGGER.info("SendResult:" + result);
			}
		} catch (BusinessException e) {
			LOGGER.info("e:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 发送有序消息
	@RequestMapping("/sendOrderlist")
	public SendResult sendOrderlist(@RequestBody String name) {
		SendResult result = null;
		try {
			List<Order> orderList = buildOrders();
			for (int i = 0; i < orderList.size(); i++) {
				String body = JSON.toJSONString(orderList.get(i));
				Message msg = new Message(orderTopic, body.getBytes());
				result = producer.sendOrder(msg, orderList.get(i).getOrderId());
				LOGGER.info("SendResult:" + result);
			}
		} catch (BusinessException e) {
			LOGGER.info("e:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<Order> buildOrders() {
		List<Order> orderList = new ArrayList<Order>();
		for (int i = 0; i < 20; i++) {
			Order orderDemo = new Order();
			orderDemo.setOrderId(new Long((long) i));
			orderDemo.setDesc("创建订单");
			orderList.add(orderDemo);

			orderDemo = new Order();
			orderDemo.setOrderId(new Long((long) i));
			orderDemo.setDesc("订单付款");
			orderList.add(orderDemo);

			orderDemo = new Order();
			orderDemo.setOrderId(new Long((long) i));
			orderDemo.setDesc("付款成功");
			orderList.add(orderDemo);

			orderDemo = new Order();
			orderDemo.setOrderId(new Long((long) i));
			orderDemo.setDesc("订单完成");
			orderList.add(orderDemo);
		}
		return orderList;
	}
}
