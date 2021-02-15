package com.sample.person.controller;

import com.sample.person.model.UserEntity;
import com.sample.person.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    static Logger log = LoggerFactory.getLogger(UserRestController.class);

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRestController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody UserEntity userEntity) {
        log.info("Singing up new user - {}", userEntity.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }
}
