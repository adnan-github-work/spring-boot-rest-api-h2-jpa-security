package com.sample.person.service;

import com.sample.person.model.UserEntity;
import com.sample.person.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity applicationUser = userRepository.findByUsername(username);

        if (applicationUser == null) {
            log.info("User not found for name - {}", username);
            throw new UsernameNotFoundException(username);
        }
        log.info("Logged in user id - {}, name - {}", applicationUser.getId(), applicationUser.getUsername());

        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}
