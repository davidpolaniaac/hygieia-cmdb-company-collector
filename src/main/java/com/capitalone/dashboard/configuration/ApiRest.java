package com.capitalone.dashboard.configuration;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import com.capitalone.dashboard.collector.CmdbSettings;
import com.capitalone.dashboard.util.Supplier;

@Component
public class ApiRest {

	private final RestOperations rest;
	private final CmdbSettings settings;

	@Autowired
	public ApiRest(CmdbSettings settings, Supplier<RestOperations> restOperationsSupplier) {
		this.settings = settings;
		this.rest = restOperationsSupplier.get();

		System.getProperties().put("http.proxyHost", this.settings.getProxyHost());
		System.getProperties().put("http.proxyPort", this.settings.getProxyPort());
		System.getProperties().put("https.proxyHost", this.settings.getProxyHost());
		System.getProperties().put("https.proxyPort", this.settings.getProxyPort());
		System.getProperties().put("http.nonProxyHosts", this.settings.getNonProxy());
	}

	@Retryable(maxAttempts = 10, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 900000))
	public <T, E> ResponseEntity<T> restCall(HttpMethod method, String url, Class<T> responseType, E requestType) {
		URI thisuri = URI.create(url);
		String apikey = this.settings.getApiKey();
		try {

			return rest.exchange(thisuri, method, new HttpEntity<>(requestType, createHeaders(apikey)), responseType);

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Retryable(maxAttempts = 10, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 900000))
	public <T> ResponseEntity<T> restCall(HttpMethod method, String url, Class<T> responseType) {
		URI thisuri = URI.create(url);
		String apikey = this.settings.getApiKey();
		try {

			return rest.exchange(thisuri, method, new HttpEntity<>(createHeaders(apikey)), responseType);

		} catch (RestClientException e) {
			throw e;
		}
	}

	protected HttpHeaders createHeaders(final String apiKey) {
		String auth = "hygieia" + ":" + apiKey;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
		String authHeader = "Basic " + new String(encodedAuth);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, authHeader);
		headers.set(HttpHeaders.ACCEPT, "application/json");
		return headers;
	}
}
