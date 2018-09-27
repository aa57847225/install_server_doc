package com.yj.mq.entity;

import java.io.Serializable;

public class Order implements Serializable{
	private static final long serialVersionUID = -2203625690315456938L;
	private Long orderId;
	private String desc;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", desc=" + desc + "]";
	}
}
