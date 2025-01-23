package com.spds.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author abinjola This class was creaded on 09-Jan-2025.
 */

@Configuration
public class NettyConfig {

	private final Environment env;

	@Autowired
	NettyConfig(Environment env) {
		this.env = env;
	}

	@Bean
	public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
		NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
		factory.setPort(Integer.parseInt(env.getProperty("server.port"))); // Set the port
		// Additional custom configurations, e.g., buffer sizes, connection settings
		return factory;
	}
}