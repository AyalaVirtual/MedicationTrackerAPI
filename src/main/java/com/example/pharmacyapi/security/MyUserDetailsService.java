package com.example.pharmacyapi.security;

import com.example.pharmacyapi.model.User;
import com.example.pharmacyapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * This finds a user by their email address and either loads the associated user's details or throws a UsernameNotFoundException
     *
     * @param emailAddress the username identifying the user whose data is required.
     * @return the user's details
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(emailAddress);
        return new MyUserDetails(user);
    }
}
