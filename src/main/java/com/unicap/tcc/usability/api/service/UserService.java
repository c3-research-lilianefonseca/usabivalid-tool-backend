package com.unicap.tcc.usability.api.service;


import com.unicap.tcc.usability.api.exception.SenhaInvalidaException;
import com.unicap.tcc.usability.api.exception.UserAlreadyRegisteredException;
import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail().trim());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyRegisteredException();
        }
        user.setEmail(user.getEmail().trim());
        return userRepository.save(user);
    }

    public UserDetails auth(User user) {
        UserDetails userDetails = loadUserByUsername(user.getLogin());
        Boolean validPassword = passwordEncoder.matches(user.getPassword(), userDetails.getPassword());
        if (validPassword) {
            return userDetails;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLoginAndRemovedDateIsNull(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database."));
        user.setAdmin(!Objects.isNull(user.getAdmin()) && user.getAdmin());
        String[] roles = user.isAdmin() ? new String[]{"USER", "ADMIN"} : new String[]{"USER"};

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }
}
