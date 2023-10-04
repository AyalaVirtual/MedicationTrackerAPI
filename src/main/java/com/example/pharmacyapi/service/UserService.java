package com.example.pharmacyapi.service;

import com.example.pharmacyapi.exception.InformationExistException;
import com.example.pharmacyapi.model.User;
import com.example.pharmacyapi.model.request.LoginRequest;
import com.example.pharmacyapi.repository.UserRepository;
import com.example.pharmacyapi.security.JWTUtils;
import com.example.pharmacyapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    @Autowired
    // @Lazy means to load it only when a user registers (only when needed)
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    /**
     * This checks to see if a user's email address already exists in the system before setting their email address and password and registering a new user
     *
     * @param userObject represents a user's registration details (email address and password)
     * @return newly registered user
     */
    public User createUser(User userObject) {

        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("user with email address " + userObject.getEmailAddress() + " already exists");
        }
    }

    /**
     * This checks the user's details to see if they have a JWT token
     *
     * @param loginRequest
     * @return user's JWT token if it exists and their login credentials are correct
     */
    public Optional<String> loginUser(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            return Optional.of(jwtUtils.generateJwtToken(myUserDetails));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * This finds a user by their email address
     *
     * @param emailAddress represents the user's email address
     * @return the user associated with the given email address
     */
    public User findUserByEmailAddress(String emailAddress) {
        return userRepository.findUserByEmailAddress(emailAddress);
    }
}
