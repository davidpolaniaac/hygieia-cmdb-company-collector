package com.capitalone.dashboard.collector;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.capitalone.dashboard.collector.RestOperationsSupplier;

@RunWith(MockitoJUnitRunner.class)
public class RestOperationsSupplierTest {

	@Test
	public void testGet() throws Exception {
		RestOperationsSupplier rest = new RestOperationsSupplier();
		RestOperations result = rest.get();
		Assert.assertThat(result, instanceOf(RestTemplate.class));
	}
}
