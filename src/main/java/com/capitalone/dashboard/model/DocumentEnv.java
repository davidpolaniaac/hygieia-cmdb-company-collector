package com.capitalone.dashboard.model;

import java.util.HashSet;
import java.util.Set;

public class DocumentEnv {
	
	private String name;
	private String id;
	private Set<Document> components;
	
	public DocumentEnv(String name, String id) {
		this.name = name;
		this.id = id;
		this.components = new HashSet<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<Document> getComponents() {
		return components;
	}
	public void setComponents(Set<Document> components) {
		this.components = components;
	}

	public void addComponent(Document component) {
		this.components.add(component);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentEnv other = (DocumentEnv) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
