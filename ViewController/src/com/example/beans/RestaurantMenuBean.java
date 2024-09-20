package com.example.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;

@SessionScoped
@ManagedBean(name="restaurantMenuBean")
public class RestaurantMenuBean {
    public RestaurantMenuBean() {
        super();
    }
    
    private String itemName;
    private Number price;
    private String availability;
    private String cuisine;
    private String restaurantId;
    
    ConstantBean constants = new ConstantBean();
    
    public ApplicationModule getApplicationModule() {
        BindingContext bindingContext = BindingContext.getCurrent();
        if (bindingContext != null) {
            DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
            DCDataControl dataControl = bindings.getDataControl();
            ApplicationModule am = (ApplicationModule)dataControl.getDataProvider();
           return am;
        } 
        else {
            System.out.println("BindingContext is null");
        }
        return null;
    }
    
    public String addMenuItem(){
        try{
        ApplicationModule am = getApplicationModule();
        ViewObject menu_restaurants_vo = am.findViewObject(constants.getMenu_restaurant_vo());
        ViewObject restaurants_vo = am.findViewObject(constants.getRestaurants_for_user_vo());
        Row restaurants_row = restaurants_vo.first();
        Row newRow = menu_restaurants_vo.createRow();
        
        DBSequence dbSeq = (DBSequence) restaurants_row.getAttribute("RestaurantId");
        int rest_id = dbSeq.getSequenceNumber().intValue();
        System.out.println("restaurant_id: "+rest_id);
        
        newRow.setAttribute("Price", this.price);
        newRow.setAttribute("Cuisine",this.cuisine);
        newRow.setAttribute("DishName", this.itemName);
        newRow.setAttribute("Availability", this.availability);
        newRow.setAttribute("RestaurantId",rest_id);    
        menu_restaurants_vo.insertRow(newRow);
        am.getTransaction().commit();
        System.out.println("restaurant menu added");
            
        this.availability="";
        this.price=0;
        this.cuisine="";
        this.itemName="";
        } catch(Exception e){
            e.printStackTrace();
        }

        
        
        return null;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public Number getPrice() {
        return price;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAvailability() {
        return availability;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}
