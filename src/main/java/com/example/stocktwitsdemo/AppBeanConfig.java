package com.example.stocktwitsdemo;

import com.stocktwitlist.api.client.StocktwitsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppBeanConfig {

  @Bean
  @Scope("singleton")
  public StocktwitsClient stocktwitsClient() {
    return new StocktwitsClient();
  }
}
