package com.ibm.microservices.wfd;

/**
 *  Patterned after https://github.com/aykutakin/SpringConfigurationPropertiesSample
 *  due to issues with YAML-formatted lists
 **/

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
@ConfigurationProperties(prefix = "wfd.menu")
public class MenuConfiguration{

  @Value("${wfd.menu.appetizers.url}")
  private String appetizers;

  @Value("${wfd.menu.entrees.url}")
  private String entrees;

  @Value("${wfd.menu.desserts.url}")
  private String desserts;

  MenuConfiguration(){
    this.appetizers = new String();
    this.entrees = new String();
    this.desserts = new String();
  }

  public String getAppetizers(){
    return this.appetizers;
  }

  public String getEntrees(){
    return this.entrees;
  }

  public String getDesserts(){
    return this.desserts;
  }

}
