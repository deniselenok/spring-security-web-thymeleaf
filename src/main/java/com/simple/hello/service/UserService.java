package com.simple.hello.service;

import com.google.common.base.Preconditions;
import com.simple.hello.config.security.extra.CryptTool;
import com.simple.hello.domain.User;
import com.simple.hello.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private CryptTool cryptTool;
    private UserRepository userRepository;

    public UserService(CryptTool cryptTool, UserRepository userRepository) {
        this.cryptTool = cryptTool;
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Preconditions.checkNotNull(username);
        log.info("Load user by username {}", username);
        UserDetails userDetails = userRepository.findByUserName(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return userDetails;
    }

    public User addNewUser(User user) {
        Preconditions.checkNotNull(user);
        log.info("Adding new user {}", user);
        user.setPassword(cryptTool.encrypt(user.getPassword()));
        return userRepository.save(user);
    }
}
