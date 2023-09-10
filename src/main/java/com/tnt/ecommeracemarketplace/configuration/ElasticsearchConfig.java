package com.tnt.ecommeracemarketplace.configuration;

import com.tnt.ecommeracemarketplace.repository.ProductEsRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = ProductEsRepository.class)
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

  @Value("${elastic.host}")
  private String host;

  @Value("${elastic.username}")
  private String username;

  @Value("${elastic.password}")
  private String password;
  
  @Bean
  @Override
  public RestHighLevelClient elasticsearchClient() {
    final ClientConfiguration config = ClientConfiguration.builder()
        .connectedTo(host)
        .usingSsl()
        .withBasicAuth(username, password)
        .build();

    return RestClients.create(config).rest();
  }
}
