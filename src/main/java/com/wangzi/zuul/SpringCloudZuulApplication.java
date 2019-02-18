package com.wangzi.zuul;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

import com.wangzi.zuul.filter.AccessFilter;

@SpringCloudApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class SpringCloudZuulApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringCloudZuulApplication.class).web(true).run(args);
	}
	
	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}
	@Bean
	public PatternServiceRouteMapper serviceRouteMapper(){
		return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", 
				"${version}/${name}");
	}
}
