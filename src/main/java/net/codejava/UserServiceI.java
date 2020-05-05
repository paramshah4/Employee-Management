package net.codejava;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceI extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
