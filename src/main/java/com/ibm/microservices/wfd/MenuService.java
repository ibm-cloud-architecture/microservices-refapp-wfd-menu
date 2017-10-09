package com.ibm.microservices.wfd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.client.RestTemplate;

import com.ibm.microservices.wfd.MenuAggregate;
import com.ibm.microservices.wfd.model.MealEntree;
import com.ibm.microservices.wfd.model.MealAppetizer;
import com.ibm.microservices.wfd.model.MealDessert;

@Component
@RestController
@ResponseBody
public class MenuService {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private MenuAggregate aggregate;

  @ApiOperation(value = "Get the menu", notes = "Returns a list of the appetizers, starters and desserts that are on the menu")
  @RequestMapping(method = RequestMethod.GET, path="/menu", produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success", response = MenuService.class),
    @ApiResponse(code = 401, message = "Unauthorized"),
    @ApiResponse(code = 403, message = "Forbidden"),
    @ApiResponse(code = 404, message = "Not Found"),
    @ApiResponse(code = 500, message = "Failure")})
  public Menu getMenu() {

    Menu menu = new Menu();

    MealAppetizer apps = aggregate.getCurrentAppetizers();
    menu.setAppetizers(apps);

    MealEntree entrees = aggregate.getCurrentEntrees();
    menu.setEntrees(entrees);

    MealDessert desserts = aggregate.getCurrentDesserts();
    menu.setDesserts(desserts);

    return menu;
  }

}

class Menu {

  private MealEntree entrees;
  private MealAppetizer apps;
  private MealDessert desserts;

  Menu(){
    this.entrees = new MealEntree();
    this.apps = new MealAppetizer();
    this.desserts = new MealDessert();
  }

  public MealEntree getEntrees(){
    return this.entrees;
  }

  public void setEntrees(MealEntree entrees){
    this.entrees = entrees;
  }

  public MealAppetizer getAppetizers(){
    return this.apps;
  }

  public void setAppetizers(MealAppetizer apps){
    this.apps = apps;
  }

  public MealDessert getDesserts(){
    return this.desserts;
  }

  public void setDesserts(MealDessert desserts){
    this.desserts = desserts;
  }
}
