package com.example.webshopapi.user;

import com.example.webshopapi.auth.AuthRequest;
import com.example.webshopapi.auth.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean registerUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.existsByEmail(userRegistrationDTO.email())) {
            throw new IllegalArgumentException("Email address already in use");
        }

        User user = new User();
        user.setFirstName(userRegistrationDTO.firstName());
        user.setLastName(userRegistrationDTO.lastName());
        user.setPrefix(userRegistrationDTO.prefix());
        user.setEmail(userRegistrationDTO.email());
        user.setPasswordHash(passwordEncoder.encode(userRegistrationDTO.password()));

        Role role = roleService.findByName("user");
        user.setRole(role);

        userRepository.save(user);
        return true;
    }

    @Override
    public String authenticateUser(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        User user = userRepository.findByEmail(authRequest.email()).orElse(null);
        if (user != null) {
            return jwtTokenService.generateToken(user.getEmail(), user.getRole().getName());
        }

        return null;
    }
}