package com.capitalone.dashboard.model;

import java.util.List;

public class ReponseCollection {
	private long count;
	private List<Document> value;

	public long getCount() {
		return count;
	}

	public void setCount(long value) {
		this.count = value;
	}

	public List<Document> getValue() {
		return value;
	}

	public void setValue(List<Document> value) {
		this.value = value;
	}
}
