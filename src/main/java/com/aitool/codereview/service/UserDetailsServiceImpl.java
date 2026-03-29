package com.aitool.codereview.service;


import com.aitool.codereview.exception.ResourceNotFoundException;
import com.aitool.codereview.model.User;
import com.aitool.codereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByGithubUsername(username).orElseThrow(
                ()-> new ResourceNotFoundException("No User Found")
        );
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getGithubUsername())
                .password("") // no password (OAuth)
                .authorities(user.getGlobalRole().name())
                .build();
    }
}
