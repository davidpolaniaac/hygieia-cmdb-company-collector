package com.capitalone.dashboard.constant;

public enum CmdbType {

	APP("app"), COMPONENT("component"), ENVIRONMENT("environment");

	public final String label;

	private CmdbType(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}
