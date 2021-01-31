package com.capitalone.dashboard.model;

public class AzureDevOpsProject {

	private String id;
	private String name;
	private String description;
	private String url;
	private String state;
	private Long revision;
	private String visibility;
	private String lastUpdateTime;

	public String getID() {
		return id;
	}

	public void setID(String value) {
		this.id = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String value) {
		this.url = value;
	}

	public String getState() {
		return state;
	}

	public void setState(String value) {
		this.state = value;
	}

	public Long getRevision() {
		return revision;
	}

	public void setRevision(Long value) {
		this.revision = value;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String value) {
		this.visibility = value;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String value) {
		this.lastUpdateTime = value;
	}
}
