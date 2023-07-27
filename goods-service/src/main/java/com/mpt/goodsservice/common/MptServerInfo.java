package com.mpt.goodsservice.common;

public enum MptServerInfo {
	AUTH_SERVER("mpt-auth-server.com", "8080");

	private String ip;
	private String port;

	MptServerInfo(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	public String getUrl() {
		return "http://" + ip + ":" + port;
	}
}
