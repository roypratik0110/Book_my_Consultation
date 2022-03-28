package com.upgrad.userservice.filter;

import com.upgrad.userservice.model.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserPrincipal loadUserByUsername(String id) throws UsernameNotFoundException {

        return UserPrincipal.builder().username(id).build();
    }

}
