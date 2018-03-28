package application.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import application.rest.model.MealAppetizer;
import application.rest.model.MealDessert;
import application.rest.model.MealEntree;

@Path("menu")
public class MenuResource {

	  private MenuAggregate aggregate = new MenuAggregate();

    //@Fallback (fallbackMethod = "fallbackService")
		//@CircuitBreaker(requestVolumeThreshold=5, failureRatio=0.5, delay=6000, successThreshold=4)
	  @GET
	  @Produces({ "application/json" })
	  public List findAll() {

	  Menu menu = new Menu();

	  MealAppetizer apps = aggregate.getCurrentAppetizers();
	  menu.setAppetizers(apps);

	  MealEntree entrees = aggregate.getCurrentEntrees();
	  menu.setEntrees(entrees);

	  MealDessert desserts = aggregate.getCurrentDesserts();
	  menu.setDesserts(desserts);

	  ArrayList<Menu> MenuList = new ArrayList<Menu>();
      MenuList.add(menu);
	  return MenuList;

	  }

	  /*@GET
	  @Produces({ "application/json" })
	  public List fallbackService() {

	  Menu menu = new Menu();

	  MealAppetizer apps = aggregate.getDefaultAppetizers();
	  menu.setAppetizers(apps);

	  MealEntree entrees = aggregate.getDefaultEntrees();
	  menu.setEntrees(entrees);

	  MealDessert desserts = aggregate.getDefaultDesserts();
	  menu.setDesserts(desserts);

	  ArrayList<Menu> MenuList = new ArrayList<Menu>();
      MenuList.add(menu);
	  return MenuList;

	}*/

}
