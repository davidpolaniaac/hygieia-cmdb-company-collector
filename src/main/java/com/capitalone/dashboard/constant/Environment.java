package com.capitalone.dashboard.constant;

public enum Environment {

	DEVELOPMENT("development"), RELEASE("release"), PRODUCTION("production");

	public final String label;

	private Environment(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return label;
	}

}