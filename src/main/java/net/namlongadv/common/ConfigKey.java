package net.namlongadv.common;

public enum ConfigKey {	
	ADV_POSITION("ADV_POSITION");
	
	private String key;

	private ConfigKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
}
