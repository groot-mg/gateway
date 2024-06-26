package com.generoso.ft.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generoso.ft.gateway.client.RequestTemplate;
import com.generoso.ft.gateway.client.model.Endpoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("com.generoso.ft.gateway.*")
public class TestConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder().build();
    }

    @Bean
    public Map<Endpoint, RequestTemplate> privateRequestTemplates(
        @Qualifier("private") List<RequestTemplate> templates) {
        return buildMap(templates);
    }

    @Bean
    public Map<Endpoint, RequestTemplate> requestTemplates(
        @Qualifier("serviceRequest") List<RequestTemplate> templates) {
        return buildMap(templates);
    }

    private Map<Endpoint, RequestTemplate> buildMap(List<RequestTemplate> templates) {
        var map = new EnumMap<Endpoint, RequestTemplate>(Endpoint.class);
        templates.forEach(t -> map.put(t.getEndpoint(), t));
        return map;
    }
}
