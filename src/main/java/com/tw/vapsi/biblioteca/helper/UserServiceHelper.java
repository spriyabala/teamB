package com.tw.vapsi.biblioteca.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public class UserServiceHelper {
    @Autowired
    UserDetailsService userDetailsService;
    public String fetchUserName()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName ;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;

    }

    public Collection<? extends GrantedAuthority> userRole() {

        Collection<? extends GrantedAuthority> userRole = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null ) {

               userRole     = auth.getAuthorities();
        }
        return userRole;

    }

}
