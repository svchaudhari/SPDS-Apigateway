package com.spds.apigateway.config;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author abinjola This class was creaded on 07-Jan-2025.
 */
@Configuration
public class GatewayConfig {

	private final CustomGatewayFilter customGatewayFilter;
	private final Environment environment;
	private static final String GATEWAY_ROUTES = "spds.gateway.routes[";

	@Autowired
	public GatewayConfig(CustomGatewayFilter customGatewayFilter, Environment environment) {
		this.customGatewayFilter = customGatewayFilter;
		this.environment = environment;
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {

		// Get the number of routes to be created from the environment or properties
		// file
		int routeCount = Integer.parseInt(environment.getProperty("spds.gateway.routes.count", "0"));

		// Start building routes dynamically
		Builder builder = routeLocatorBuilder.routes();
		IntStream.range(0, routeCount).forEach(i -> {
			String id = trimString(environment.getProperty(GATEWAY_ROUTES + i + "].id"));
			String uri = trimString(environment.getProperty(GATEWAY_ROUTES + i + "].uri"));
			String path = trimString(environment.getProperty(GATEWAY_ROUTES + i + "].predicates[0]", ""));
			String stripPrefix = trimString(environment.getProperty(GATEWAY_ROUTES + i + "].filters[0]", ""));
//			System.out.println(String.format("id:%s, uri:%s, path:%s, stripPrefix:%s", id, uri, path, stripPrefix));
			// Build each route dynamically
			if (Integer.parseInt(stripPrefix) == 0) {
				builder.route(id, r -> r.path(path).filters(f -> f.filter(customGatewayFilter)).uri(uri));
			} else {
				builder.route(id,
						r -> r.path(path)
								.filters(f -> f.filter(customGatewayFilter).stripPrefix(Integer.parseInt(stripPrefix)))
								.uri(uri));
			}
		});
		return builder.build();
	}

	private String trimString(String value) {
		if (value != null) {
			return value.trim();
		}
		return value;
	}
}
