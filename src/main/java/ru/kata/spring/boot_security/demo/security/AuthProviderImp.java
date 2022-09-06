package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.Collections;

@Component
public class AuthProviderImp implements AuthenticationProvider {
    private UserServiceImp userServiceImp;

    public AuthProviderImp(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
//        System.out.println("authentication.getName() -- " + username);
        User user = (User) userServiceImp.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();
        System.out.println("authentication.getCredentials().toString() -- " + password);
        System.out.println(password + user + user.getPassword());
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(user, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
