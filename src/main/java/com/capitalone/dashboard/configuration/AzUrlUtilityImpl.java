package com.capitalone.dashboard.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.capitalone.dashboard.collector.CmdbSettings;

@Component
public class AzUrlUtilityImpl implements AzUrlUtility {

	private static final String BASE = "https://extmgmt.dev.azure.com";
	private static final String SEGMENT_EXTENSION = "_apis/ExtensionManagement/InstalledExtensions";
	private static final String COLLECTION = "company/Data/Scopes/Default/Current/Collections";
	private static final String DOCUMENTS = "Documents";
	private static final String API_VERSION = "api-version";
	private static final String AZURE_DEVPOS_BASE = "https://dev.azure.com";
	private static final String API = "_apis";
	private static final String PROJECTS = "projects";

	private final CmdbSettings settings;

	@Autowired
	public AzUrlUtilityImpl(CmdbSettings settings) {
		this.settings = settings;
	}

	@Override
	public String getDocument(String collection, String key) {
		UriComponentsBuilder url = getDocumentsBase(collection);
		return url.pathSegment(key).queryParam(API_VERSION, settings.getApiVersion()).build(true).toString();
	}

	@Override
	public String getDocuments(String collection) {
		UriComponentsBuilder url = getDocumentsBase(collection);
		return url.queryParam(API_VERSION, settings.getApiVersion()).build(true).toString();
	}

	private UriComponentsBuilder getDocumentsBase(String collection) {
		UriComponentsBuilder url = apiBase();
		return url.pathSegment(collection).pathSegment(DOCUMENTS);
	}

	private UriComponentsBuilder apiBase() {

		return UriComponentsBuilder.fromUriString(BASE).pathSegment(settings.getOrganizationName())
				.path(SEGMENT_EXTENSION).pathSegment(settings.getPublisher()).path(COLLECTION);
	}

	@Override
	public String getProject(String project) {
		return UriComponentsBuilder.fromUriString(AZURE_DEVPOS_BASE).pathSegment(settings.getOrganizationName())
				.pathSegment(API).pathSegment(PROJECTS).pathSegment(project)
				.queryParam(API_VERSION, settings.getApiVersion()).build().toUri().toASCIIString();
	}

}
