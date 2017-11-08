package com.ibm.microservices.wfd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ibm.microservices.wfd.model.MealEntree;
import com.ibm.microservices.wfd.model.MealAppetizer;
import com.ibm.microservices.wfd.model.MealDessert;

import java.util.List;
import java.util.ArrayList;

@Component
@EnableConfigurationProperties
public class MenuAggregate {

  @Autowired
  private MenuConfiguration config;

  @Autowired
  private RestTemplate restTemplate;

  public MealAppetizer getCurrentAppetizers(){
    return this.restTemplate.getForObject(this.config.getAppetizers(), MealAppetizer.class);
  }

  public MealEntree getCurrentEntrees(){
    return this.restTemplate.getForObject(this.config.getEntrees(), MealEntree.class);
  }

  public MealDessert getCurrentDesserts(){
    return this.restTemplate.getForObject(this.config.getDesserts(), MealDessert.class);
  }

}
