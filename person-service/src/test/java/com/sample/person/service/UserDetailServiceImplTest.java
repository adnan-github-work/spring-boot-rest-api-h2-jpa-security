package com.sample.person.service;

import com.sample.person.model.UserEntity;
import com.sample.person.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.sample.person.util.Constants.TEST_USER;
import static com.sample.person.util.Constants.TEST_USER_ID;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailServiceImplTest {

    private static final String TEST_PASSWORD = "test_password";
    @Mock
    private UserRepository userRepository;

    private UserEntity userEntity;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void setup() {

        userEntity = new UserEntity();
        userEntity.setUsername(TEST_USER);
        userEntity.setId(TEST_USER_ID);
        userEntity.setPassword(TEST_PASSWORD);

    }

    @Test
    public void testLoadUserByUsername() {

        when(userRepository.findByUsername(TEST_USER)).thenReturn(userEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(TEST_USER);

        assertNotNull(userDetails);
        assertEquals(TEST_USER, userDetails.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByInvalidUsername() {

        when(userRepository.findByUsername(TEST_USER)).thenReturn(null);

        userDetailsService.loadUserByUsername(TEST_USER);

    }

}
