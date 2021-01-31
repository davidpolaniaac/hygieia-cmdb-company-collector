package com.capitalone.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.capitalone.dashboard.configuration.ApiRest;
import com.capitalone.dashboard.configuration.AzUrlUtility;
import com.capitalone.dashboard.model.AzureDevOpsProject;
import com.capitalone.dashboard.model.Document;
import com.capitalone.dashboard.model.ReponseCollection;

@Component
public class DocumentServiceImpl implements DocumentService {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentServiceImpl.class);

	private final ApiRest apiRest;
	private final AzUrlUtility azUrlUtility;

	@Autowired
	public DocumentServiceImpl(ApiRest apiRest, AzUrlUtility azUrlUtility) {
		this.apiRest = apiRest;
		this.azUrlUtility = azUrlUtility;
	}

	@Override
	public List<Document> getDocuments(String collection) {

		List<Document> documents = new ArrayList<>();
		String urlDocuments = azUrlUtility.getDocuments(collection);

		try {
			ResponseEntity<ReponseCollection> responseCollection = apiRest.restCall(HttpMethod.GET, urlDocuments,
					ReponseCollection.class);
			documents = responseCollection.getBody().getValue();
		} catch (Exception e) {
			LOG.error("Get documents: " + urlDocuments, e.getMessage());
		}

		return documents;
	}

	@Override
	public Document getDocument(String collection, String documentId) {

		Document documentResponse = null;
		String urlDocuments = azUrlUtility.getDocument(collection, documentId);

		try {
			ResponseEntity<Document> responseDocument = apiRest.restCall(HttpMethod.GET, urlDocuments, Document.class);
			documentResponse = responseDocument.getBody();
			return documentResponse;
		} catch (Exception e) {
			LOG.error("Get document: " + urlDocuments, e.getMessage());
		}

		return documentResponse;
	}

	@Override
	public Document createDocument(Document document) {

		Document documentResponse = null;
		String urlDocuments = azUrlUtility.getDocuments(document.getCollection());

		try {
			ResponseEntity<Document> responseDocument = apiRest.restCall(HttpMethod.POST, urlDocuments, Document.class,
					document);

			documentResponse = responseDocument.getBody();
			return documentResponse;

		} catch (Exception e) {
			LOG.error("Create document: " + urlDocuments, e.getMessage());
		}

		return documentResponse;
	}

	@Override
	public Document updateDocument(Document document) {
		Document documentResponse = null;
		String urlDocuments = azUrlUtility.getDocuments(document.getCollection());

		try {
			ResponseEntity<Document> responseDocument = apiRest.restCall(HttpMethod.PUT, urlDocuments, Document.class,
					document);
			documentResponse = responseDocument.getBody();
			return documentResponse;

		} catch (Exception e) {
			LOG.error("Update Document: " + urlDocuments, e.getMessage());
		}

		return documentResponse;
	}

	@Override
	public Document setDocument(Document document) {

		Document documentResponse = null;
		document.set__etag(-1);
		String urlDocuments = azUrlUtility.getDocuments(document.getCollection());

		try {
			ResponseEntity<Document> responseDocument = apiRest.restCall(HttpMethod.PATCH, urlDocuments, Document.class,
					document);
			documentResponse = responseDocument.getBody();
		} catch (Exception e) {
			LOG.info("Set document: " + urlDocuments, e.getMessage());
		}

		return documentResponse;
	}

	@Override
	public boolean deleteDocument(Document document) {

		boolean result = false;
		
		String urlDocuments = azUrlUtility.getDocument(document.getCollection(), document.getId());

		try {
			apiRest.restCall(HttpMethod.DELETE, urlDocuments, Object.class);
			result = true;
		} catch (Exception e) {
			LOG.error("Delete document: " + urlDocuments, e.getMessage());
		}

		return result;
	}
	
	@Override
	public String getProjectId(String project) {
		String projectId = null;
		try {
			String urlproject= azUrlUtility.getProject(project);
			ResponseEntity<AzureDevOpsProject> responseProject = apiRest.restCall(HttpMethod.GET, urlproject, AzureDevOpsProject.class);
			projectId = responseProject.getBody().getID();
			
		} catch (Exception e) {
			LOG.error("Get project Id: " + project, e.getMessage());
		}
		return projectId;
	}

}
