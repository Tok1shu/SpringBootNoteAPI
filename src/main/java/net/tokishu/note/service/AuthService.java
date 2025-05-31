package net.tokishu.note.service;

import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.LoginRequest;
import net.tokishu.note.dto.request.RegisterRequest;
import net.tokishu.note.dto.response.LoginResponse;
import net.tokishu.note.dto.response.RegisterResponse;
import net.tokishu.note.dto.response.RootResponse;
import net.tokishu.note.model.User;
import net.tokishu.note.repo.UserRepository;
import net.tokishu.note.security.JwtService;
import net.tokishu.note.security.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    public final UserRepository userRepository;
    public final UserService userService;
    public final PasswordService passwordService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RootResponse registerRoot() {
        userRepository.findById("root").ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Root user already exists");
        });

        String rawPassword = passwordService.generate(16);
        String hashedPassword = passwordEncoder.encode(rawPassword);

        User rootUser = new User();
        rootUser.setUsername("root");
        rootUser.setPassword(hashedPassword);
        rootUser.setRole("ADMIN");

        userRepository.save(rootUser);

        return RootResponse.builder()
                .username(rootUser.getUsername())
                .password(rawPassword)
                .role(rootUser.getRole())
                .build();
    }


    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .message("Registration successful")
                .build();
    }

    public LoginResponse authorizeUser(LoginRequest request) {
        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build()
        );

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

}
