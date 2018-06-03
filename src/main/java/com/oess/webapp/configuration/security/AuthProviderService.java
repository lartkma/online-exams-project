package com.oess.webapp.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.oess.model.entity.Student;
import com.oess.model.entity.Teacher;
import com.oess.model.entity.User;
import com.oess.model.repository.UserRepository;

@Component
public class AuthProviderService implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        } if (!encoder.matches(password, user.getEncryptedPassword())) {
            throw new BadCredentialsException("Password don't match");
        } else if (user.getInactive()) {
            throw new DisabledException("User is inactive");
        } else {
            UserRole role = null;
            if (user instanceof Teacher) {
                role = UserRole.TEACHER;
            } else if (user instanceof Student) {
                role = UserRole.STUDENT;
            }
            return new UserToken(user.getId(), user.getFullName(), user.getEmail(), role);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
