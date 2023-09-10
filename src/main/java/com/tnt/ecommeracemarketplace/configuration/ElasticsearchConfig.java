package com.tnt.ecommeracemarketplace.configuration;

import com.tnt.ecommeracemarketplace.repository.ProductEsRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = ProductEsRepository.class)
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

  private static final String host = "emp.es.ap-northeast-2.aws.elastic-cloud.com:443";
  private static final String username = "elastic";
  private static final String password = "rGCzdigCfUD6vzoxDNjq6QJl";


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
