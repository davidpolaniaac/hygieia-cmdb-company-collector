package com.capitalone.dashboard.service;

import java.util.List;

import com.capitalone.dashboard.model.Document;

public interface DocumentService {

	List<Document> getDocuments(String collection);

	Document getDocument(String collection, String documentId);

	Document createDocument(Document document);

	Document updateDocument(Document document);

	Document setDocument(Document document);

	boolean deleteDocument(Document document);
	
	String getProjectId(String project) ;

}
