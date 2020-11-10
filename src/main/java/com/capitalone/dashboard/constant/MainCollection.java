package com.capitalone.dashboard.constant;

public enum MainCollection {
	MANAGEMENTS("managements"), APPLICATIONS("applications"), COMPONENTS("components");

	private final String label;

	private MainCollection(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return label;
	}
}
