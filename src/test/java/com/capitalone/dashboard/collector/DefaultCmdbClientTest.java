package com.capitalone.dashboard.collector;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.capitalone.dashboard.model.Cmdb;
import com.capitalone.dashboard.model.Document;
import com.capitalone.dashboard.service.DocumentService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCmdbClientTest {
	
	@Mock
	private DocumentService documentService;

	@Mock
	private Document document;
	
	@Mock
	private CmdbSettings cmdbSettings;

	@InjectMocks
	private CmdbClientImpl defaultCmdbClient;

	@Before
	public void setup() throws Exception {
		when(cmdbSettings.getProjectId()).thenReturn("123456");
	}
	
	@Test
	public void whenTheElementsAreNullYouMustReturnAnEmptyList() throws Exception {

		List<Cmdb> result = defaultCmdbClient.getApps();
		Assert.assertTrue(result.isEmpty());
	}

	@Test
	public void shouldReturnListCmdbWithAppWhenAppExistsUdeploy() throws Exception {

		List<Document> documents = new ArrayList<>();
		Mockito.when(document.getUserName()).thenReturn("");
		Mockito.when(document.getId()).thenReturn("");
		documents.add(document);
		Mockito.when(documentService.getDocuments(any())).thenReturn(documents);
		
		List<Document> apps = new ArrayList<>();
		Document app = new Document();
		app.setId("");
		app.setUserName("");
		apps.add(app);

		Mockito.when(documentService.getDocuments(any())).thenReturn(apps);

		List<Cmdb> result = defaultCmdbClient.getApps();
		Assert.assertEquals("app", result.get(0).getConfigurationItemType());
	}

	@Test
	public void ShouldReturnCmdbAppWithEnvironmentsWhenAppHasEnvironment() throws Exception {

		List<Document> apps = new ArrayList<>();
		Document app = new Document();
		app.setId("");
		app.setUserName("");
		apps.add(app);
		Mockito.when(documentService.getDocuments(any())).thenReturn(apps);

		List<Cmdb> result = defaultCmdbClient.getApps();
		Assert.assertEquals("app", result.get(0).getConfigurationItemType());
		Assert.assertEquals("development", result.get(0).getEnvironments().get(0));

	}

}
