package com.example.MG_RestaurantManager20.auth;

import com.example.MG_RestaurantManager20.user.domain.UserRole;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    private static final String USER_SESSION_ID = "usersSessionId";
    private static final String USER_SESSION_ROLE = "usersRole";

    public void createNewSession(Long usersSessionId, UserRole userRole) {
        VaadinSession session = VaadinSession.getCurrent();
        session.setAttribute(USER_SESSION_ID, usersSessionId);
        session.setAttribute(USER_SESSION_ROLE, userRole);
    }

    public boolean checkIfAuthenticatedAdmin() {
        VaadinSession session = VaadinSession.getCurrent();
        var sessionUserId = session.getAttribute(USER_SESSION_ID);
        var sessionUserRole = session.getAttribute(USER_SESSION_ROLE);
        return sessionUserId != null && sessionUserRole.equals(UserRole.ADMIN);
    }

    public boolean checkIfAuthenticatedEmployee() {
        VaadinSession session = VaadinSession.getCurrent();
        var sessionUserId = session.getAttribute(USER_SESSION_ID);
        var sessionUserRole = session.getAttribute(USER_SESSION_ROLE);
        return sessionUserId != null && sessionUserRole.equals(UserRole.EMPLOYEE);
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