package com.capitalone.dashboard.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestOperations;

import com.capitalone.dashboard.collector.CmdbSettings;
import com.capitalone.dashboard.configuration.ApiRest;

@RunWith(MockitoJUnitRunner.class)
public class ApiRestTest {
	@Mock
	private RestOperations rest;

	@Mock
	private Supplier<RestOperations> restOperationsSupplier;

	@Mock
	private CmdbSettings settings;

	
	@Test
	public void proxy() {
		
		Mockito.when(settings.getProxyHost()).thenReturn("");
		Mockito.when(settings.getProxyPort()).thenReturn("");
		Mockito.when(settings.getNonProxy()).thenReturn("");
		
		Mockito.when(restOperationsSupplier.get()).thenReturn(rest);

		new ApiRest(settings, restOperationsSupplier);
		
		Mockito.verify(restOperationsSupplier).get();
	}

}
