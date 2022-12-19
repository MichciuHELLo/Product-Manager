package com.example.MG_RestaurantManager20.auth;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    private static final String USER_SESSION_ID = "usersSessionId";

    public void createNewSession(Long usersSessionId) {
        VaadinSession session = VaadinSession.getCurrent();
        session.setAttribute(USER_SESSION_ID, usersSessionId);
    }

    public boolean checkIfAuthenticated() {
        VaadinSession session = VaadinSession.getCurrent();
        var sessionAttribute = session.getAttribute(USER_SESSION_ID);
        return sessionAttribute != null;
    }

    public Long getUserSessionId() {
        VaadinSession session = VaadinSession.getCurrent();
        var sessionAttribute = session.getAttribute(USER_SESSION_ID);
        return (Long) sessionAttribute;
    }

    public void removeCurrentSession() {
        VaadinSession session = VaadinSession.getCurrent();
        session.setAttribute(USER_SESSION_ID, null);
    }

}