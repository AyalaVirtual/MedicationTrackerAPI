package com.example.pharmacyapi.security;

import com.example.pharmacyapi.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;


public class MyUserDetails implements UserDetails {

    private final User user;

    // Try @Lazy in method signature as experimental fix for circular dependency errors with testing
    public MyUserDetails(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }


    // We don't generate a setter because we're only trying to get the username. The user sets the username.
    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    /**
     * This checks to see if the user's account is expired
     *
     * @return true if the user's account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * This checks to make sure the user's account isn't locked
     *
     * @return true if the user's account isn't locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * This checks whether or not the user's credentials have expired
     *
     * @return true if credentials have not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}

