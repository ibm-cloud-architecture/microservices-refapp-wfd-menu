package com.ibm.microservices.wfd.model;

import java.util.List;
import java.util.ArrayList;

public class MealAppetizer {

  private int order;

  private List<String> menu;

  private String type;

  public MealAppetizer(){
    this.order = 1;
    this.menu = new ArrayList<String>();
    this.type = "appetizer";
  }

  public MealAppetizer(int order, List<String> menu, String type){
    this.order = order;
    this.menu = menu;
    this.type = type;
  }

  public void setOrder(int order){
    this.order = order;
  }

  public int getOrder(){
    return this.order;
  }

  public void setMenu(List<String> menu){
    this.menu = menu;
  }

  public List<String> getMenu(){
    return this.menu;
  }

  public void setType(String type){
    this.type = type;
  }

  public String getType(){
    return this.type;
  }


}
