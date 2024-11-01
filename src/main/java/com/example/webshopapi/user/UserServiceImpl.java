package com.example.webshopapi.user;

import com.example.webshopapi.auth.AuthRequest;
import com.example.webshopapi.auth.AuthResponse;
import com.example.webshopapi.auth.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleService roleService;
    private final JwtTokenService jwtTokenService;

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
    public AuthResponse authenticateUser(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.email()).orElse(null);

        if(passwordEncoder.matches(authRequest.password(), user.getPasswordHash())) {
            return new AuthResponse(
                    jwtTokenService.generateToken(user.getEmail(), user.getRole().getName()),
                    new UserResponseDto(
                            user.getFirstName(),
                            user.getLastName(),
                            user.getPrefix(),
                            user.getEmail()
                    ));
        }

        return null;
    }
}