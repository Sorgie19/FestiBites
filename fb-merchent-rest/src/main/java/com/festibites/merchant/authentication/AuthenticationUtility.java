package com.festibites.merchant.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.festibites.merchant.model.user.User;
import com.festibites.merchant.service.user.UserService;

@Component
public class AuthenticationUtility {

    private final UserService userService;

    @Autowired
    public AuthenticationUtility(UserService userService) {
        this.userService = userService;
    }

    public Long getCurrentAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User currentUser = userService.getUserByEmail(username);
            return currentUser.getId();
        } else {
            throw new AccessDeniedException("Authentication principal is not of expected type");
        }
    }
}
