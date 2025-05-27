package net.tokishu.note.service;

import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.RegisterRequest;
import net.tokishu.note.model.User;
import net.tokishu.note.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }
}
