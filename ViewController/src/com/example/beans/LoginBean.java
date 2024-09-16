package com.example.beans;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;


@SessionScoped
@ManagedBean(name="loginBean")
public class LoginBean {
    private String email;
    private String password;
    private String usersVO_name = "G3UsersVO";
    
    ConstantBean constants = new ConstantBean();
    
    public LoginBean() {
        super();
    }
    public ApplicationModule getApplicationModule() {
        BindingContext bindingContext = BindingContext.getCurrent();
            if (bindingContext != null) {
        DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
        DCDataControl dataControl = bindings.getDataControl();
        System.out.println("datacontrol");
        ApplicationModule am = (ApplicationModule)dataControl.getDataProvider();
           return am;
        } else {
            // Log or handle the case where BindingContext is null
            System.out.println("BindingContext is null");
        }
        return null;
    }
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String loginUser() {
        try {
            ApplicationModule am = getApplicationModule();
            ViewObject usersVO = am.findViewObject(usersVO_name);
            usersVO.setWhereClause("email = :email");
            usersVO.defineNamedWhereClauseParam("email", null, null);
            usersVO.setNamedWhereClauseParam("email", email);
            usersVO.executeQuery();
            
            if (usersVO.getEstimatedRowCount() == 1) {
                Row userRow = usersVO.first();
                String storedPassword = (String) userRow.getAttribute("password");
                if (storedPassword.equals(encryptPassword(password))) {
                    String role = (String) userRow.getAttribute("role");
                    if(role.equals("owner")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Owner Login Success"));
                        return constants.getOwner_navigation();
                    }
                    else if(role.equals("super_admin")){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Super Admin Login Success"));
                        return constants.getSuper_admin_navigation();
                    }
                    else if(role.equals("customer")){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Login Success"));
                        return constants.getCustomer_navigation();
                    }
                    else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unidentified Role"));
                    }
                }
                else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Password"));
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Credentials"));
            }
            return constants.getError_page_navigation(); 
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
            System.out.println(e);
            return null;
        }
    }
}
