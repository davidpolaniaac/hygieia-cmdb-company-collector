package com.capitalone.dashboard.model;

import java.util.HashSet;
import java.util.Set;

public class DocumentManagement {
	
	private Set<DocumentApp> apps;
	private String name;
	private String id;
	
	public DocumentManagement(String name, String id) {
		this.name = name;
		this.id = id;
		this.apps = new HashSet<>();
	}
	
	public Set<DocumentApp> getApps() {
		return apps;
	}
	public void setApps(Set<DocumentApp> apps) {
		this.apps = apps;
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
	
	public DocumentApp getApp(DocumentApp app) {
		 return this.apps.parallelStream().filter(x -> x.equals(app)).findFirst().orElse(null);
	}
	
	public void addApp(DocumentApp app) {
		this.apps.add(app);
	}
		
}
