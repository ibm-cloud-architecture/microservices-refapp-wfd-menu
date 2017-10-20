package application.rest;

import java.util.ArrayList;
import java.util.List;

import application.rest.model.MealAppetizer;
import application.rest.model.MealDessert;
import application.rest.model.MealEntree;

public class MenuList {
	
	private List<MealAppetizer> mealapp;
	private List<MealDessert> mealdes;
	private List<MealEntree> mealent;
	
	MenuList(){
	    this.mealapp =  new ArrayList<MealAppetizer>();
	    this.mealdes = new ArrayList<MealDessert>();
	    this.mealent = new ArrayList<MealEntree>();
	  } 
	
	public List<MealAppetizer> getMealapp() {
		return mealapp;
	}
	public void setMealapp(List<MealAppetizer> mealapp) {
		this.mealapp = mealapp;
	}
	public List<MealDessert> getMealdes() {
		return mealdes;
	}
	public void setMealdes(List<MealDessert> mealdes) {
		this.mealdes = mealdes;
	}
	public List<MealEntree> getMealent() {
		return mealent;
	}
	public void setMealent(List<MealEntree> mealent) {
		this.mealent = mealent;
	}
	

}
