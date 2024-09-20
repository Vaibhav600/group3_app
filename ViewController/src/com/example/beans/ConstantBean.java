package com.example.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


@ApplicationScoped
@ManagedBean(name="constantBean")
public class ConstantBean {
    private String super_admin_navigation = "goToSuperAdmin";
    private String owner_navigation = "goToOwner";
    private String customer_navigation = "goToCustomer";
    private String login_page_navigation = "goToLogin";
    private String error_page_navigation = "goToError";
    private String menu_restaurant_vo = "G3MenuItemsVO";
    private String restaurants_for_user_vo = "RestaurantForUser";

    public void setLogin_page_navigation(String login_page_navigation) {
        this.login_page_navigation = login_page_navigation;
    }

    public String getLogin_page_navigation() {
        return login_page_navigation;
    }

    public void setSuper_admin_navigation(String super_admin_navigation) {
        this.super_admin_navigation = super_admin_navigation;
    }

    public String getSuper_admin_navigation() {
        return super_admin_navigation;
    }

    public void setOwner_navigation(String owner_navigation) {
        this.owner_navigation = owner_navigation;
    }

    public String getOwner_navigation() {
        return owner_navigation;
    }

    public void setCustomer_navigation(String customer_navigation) {
        this.customer_navigation = customer_navigation;
    }

    public String getCustomer_navigation() {
        return customer_navigation;
    }

    public ConstantBean() {
        super();
    }

    public String getError_page_navigation() {
        return error_page_navigation;
    }

    public String getMenu_restaurant_vo() {
        return menu_restaurant_vo;
    }

    public void setRestaurants_for_user_vo(String restaurants_for_user_vo) {
        this.restaurants_for_user_vo = restaurants_for_user_vo;
    }

    public String getRestaurants_for_user_vo() {
        return restaurants_for_user_vo;
    }
}
