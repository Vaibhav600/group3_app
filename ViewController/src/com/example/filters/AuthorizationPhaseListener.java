package com.example.filters;


import javax.faces.application.FacesMessage;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.faces.application.NavigationHandler;

import oracle.adf.share.ADFContext;

public class AuthorizationPhaseListener implements javax.faces.event.PhaseListener {

    @Override
    public void beforePhase(PhaseEvent event) {
        if (ADFContext.getCurrent()==null) {
                    ADFContext.initADFContext(null, null, null, null);
                }
        System.out.println("Before phase running");
        FacesContext facesContext = event.getFacesContext();
        System.out.println("Before phase running0: "+facesContext.getViewRoot());
        if(facesContext.getViewRoot()==null){
            return;
        }
        System.out.println("Before phase running: viewRoot not null ");


        String currentPage = facesContext.getViewRoot().getViewId();
        System.out.println("Before phase running1: ");

//        boolean isLoginPage = currentPage.contains("loginRegisterPage.xhtml");

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String userRole = (String) session.getAttribute("userRole");
        System.out.println("Before phase running2: "+userRole);


        if ((userRole == null || !isUserAuthorizedForPage(currentPage, userRole))) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "loginPage"); // Redirect to login page if unauthorized
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unauthorized Access"));

        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        // No action needed after phase
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW; // Check before the page is rendered
    }

    private boolean isUserAuthorizedForPage(String page, String role) {
        // Implement the logic to check if the user with the given role is authorized to access the page
        // For example, map roles to pages they are allowed to access
        if (role.equals("owner") && page.contains("ownerTF")) {
                    return true;
            } else if (role.equals("super_admin") && page.contains("superAdminTF")) {
                 return true;
            } else if (role.equals("customer") && page.contains("customerTF")) {
                return true;
            }
        return false;

    }
}
