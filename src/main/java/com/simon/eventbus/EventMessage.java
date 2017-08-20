package com.simon.eventbus;

public class EventMessage {

	final int msgcode;
	final String msg;

	public EventMessage(int msgcode, String msg) {
		this.msgcode = msgcode;
		this.msg = msg;
	}

	public int getMsgcode() {
		return msgcode;
	}

	public String getMsg() {
		return msg;
	}
}
