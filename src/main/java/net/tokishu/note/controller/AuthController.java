package net.tokishu.note.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.LoginRequest;
import net.tokishu.note.dto.request.RegisterRequest;
import net.tokishu.note.dto.response.LoginResponse;
import net.tokishu.note.dto.response.RegisterResponse;
import net.tokishu.note.dto.response.RootResponse;
import net.tokishu.note.model.User;
import net.tokishu.note.service.AuthService;
import net.tokishu.note.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;
    public final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse response = authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.authorizeUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/new-root")
    public ResponseEntity<RootResponse> newRoot() {
        RootResponse response = authService.registerRoot();
        return ResponseEntity.ok(response);
    }
}