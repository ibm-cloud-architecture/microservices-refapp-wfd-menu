package application.rest.model;

import java.util.ArrayList;
import java.util.List;

public class MealDessert {
	private int order;

	  private List<String> menu;

	  private String type;

	  public MealDessert(){
	    this.order = 3;
	    this.menu = new ArrayList<String>();
	    this.type = "dessert";
	  }

	  public MealDessert(int order, List<String> menu, String type){
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
