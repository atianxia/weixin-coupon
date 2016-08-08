package com.groundnine.coupon.consts;

public enum ResponseCodeEnum {
	
	SUCCESS(0),NOT_LOGIN(1),RECEIVED(2), SELL_OUT(3);
	
	int code;
	
	ResponseCodeEnum(int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	
}
