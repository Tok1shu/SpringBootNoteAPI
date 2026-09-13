package net.tokishu.note.service;

import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.LoginRequest;
import net.tokishu.note.dto.request.RegisterRequest;
import net.tokishu.note.dto.response.LoginResponse;
import net.tokishu.note.dto.response.RegisterResponse;
import net.tokishu.note.dto.response.RootResponse;
import net.tokishu.note.dto.response.UserResponse;
import net.tokishu.note.model.User;
import net.tokishu.note.model.UserRole;
import net.tokishu.note.repo.UserRepository;
import net.tokishu.note.security.JwtService;
import net.tokishu.note.util.GravatarUtil;
import net.tokishu.note.util.PasswordGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    public RootResponse registerRoot() {
        String rawPassword = PasswordGenerator.generate(16);
        User rootUser = userService.createUser("root", rawPassword, UserRole.ADMIN, null);

        return RootResponse.builder()
                .username(rootUser.getUsername())
                .password(rawPassword)
                .role(rootUser.getRole())
                .build();
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        User user = userService.createRegularUser(request.getUsername(), request.getPassword());

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
                        .roles(user.getRole().name())
                        .build()
        );

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to identify user");
        }

        return userRepository.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    public User getCurrentUserOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        } else {
            return null;
        }

        return userRepository.findById(username).orElse(null);
    }
}