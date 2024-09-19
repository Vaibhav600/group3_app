package com.example.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;


@SessionScoped
@ManagedBean(name="assignDeliveryAgent")
public class AssignDeliveryAgent {
    private String order_id;
    private String agent_id;

    public AssignDeliveryAgent() {
        super();
    }
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
    public String assignAgent(){
        try{
            ApplicationModule am = getApplicationModule();
            ViewObject orders_vo = am.findViewObject("G3OrdersVO");
            
            orders_vo.setNamedWhereClauseParam("bOrderId",order_id);
            orders_vo.executeQuery();
            
            Row row = orders_vo.first();

            row.setAttribute("DeliveryAgentId", agent_id);
            am.getTransaction().commit();
        }
        catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Agent Assignment Failed", null));
            System.out.println(e);
        }
        return null;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_id() {
        return agent_id;
    }
}
