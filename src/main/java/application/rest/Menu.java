package application.rest;

import application.rest.model.MealAppetizer;
import application.rest.model.MealDessert;
import application.rest.model.MealEntree;

public class Menu {
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
