package com.spds.apigateway.config;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author abinjola This class was creaded on 07-Jan-2025.
 */
@Component
@Slf4j
public class CustomGatewayFilter implements GatewayFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Processing request: {} {} from IP: {} with headers: {}", exchange.getRequest().getMethod(),
				exchange.getRequest().getURI(), exchange.getRequest().getRemoteAddress(),
				exchange.getRequest().getHeaders());

//		var request = exchange.getRequest();

		// Modify headers (Example: Add a custom header)
//        HttpHeaders modifiedHeaders = HttpHeaders.writableHttpHeaders(request.getHeaders());
//		modifiedHeaders.add("X-Request-Id", generateRequestId());

		// Modify the request by creating a new one with the modified headers
//        var modifiedRequest = request.mutate()
//                                     .headers(headers -> headers.putAll(modifiedHeaders))
//                                     .build();

		// Create a new exchange with the modified request
//        var modifiedExchange = exchange.mutate().request(modifiedRequest).build();
		// Proceed with the chain
		return chain.filter(exchange).doOnTerminate(() -> {
			// Log after request is processed
			log.info("Completed request: {} {} with status: {}", exchange.getRequest().getMethod(),
					exchange.getRequest().getURI(), exchange.getResponse().getStatusCode());
		});
	}

	private String generateRequestId() {

		return UUID.randomUUID().toString();
	}
}
