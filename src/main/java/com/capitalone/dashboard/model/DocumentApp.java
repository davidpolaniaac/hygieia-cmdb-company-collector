package com.capitalone.dashboard.model;

import java.util.HashSet;
import java.util.Set;

public class DocumentApp {
	
	private String name;
	private String id;
	private Set<DocumentEnv> enviroments;
	
	public DocumentApp(String name, String id) {
		this.name = name;
		this.id = id;
		this.enviroments = new HashSet<>();
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
	public Set<DocumentEnv> getEnviroments() {
		return enviroments;
	}
	public void setEnviroments(Set<DocumentEnv> enviroments) {
		this.enviroments = enviroments;
	}
	
	public void addEnviroment(DocumentEnv enviroment) {
		this.enviroments.add(enviroment);
	}

	public DocumentEnv getEnvironment(DocumentEnv env) {
		 return this.enviroments.parallelStream().filter(x -> x.equals(env)).findFirst().orElse(null);
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
		DocumentApp other = (DocumentApp) obj;
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
