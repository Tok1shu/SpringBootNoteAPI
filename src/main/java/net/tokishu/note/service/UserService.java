package net.tokishu.note.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.UpdateProfileRequest;
import net.tokishu.note.dto.response.UserResponse;
import net.tokishu.note.model.User;
import net.tokishu.note.model.UserRole;
import net.tokishu.note.repo.NoteRepository;
import net.tokishu.note.repo.UserRepository;
import net.tokishu.note.util.CheckAuthUtil;
import net.tokishu.note.util.GravatarUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NoteRepository noteRepository;

    public UserResponse getCurrentUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .gravatarUrl(GravatarUtil.generateGravatarUrl(user.getGravatarEmail()))
                .build();
    }

    public User createUser(String username, String password, UserRole role, User currentUser) {
        if (userRepository.existsById(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        boolean adminExists = userRepository.existsByRole(UserRole.ADMIN);
        boolean isCreatingAdmin = UserRole.ADMIN.equals(role);

        if (isCreatingAdmin && adminExists) {
            if (currentUser == null || currentUser.getRole() != UserRole.ADMIN) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admin can create another admin");
            }
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    public User createRegularUser(String username, String password) {
        return createUser(username, password, UserRole.USER, null);
    }

    @Transactional
    public UserResponse updateCurrentUser(UpdateProfileRequest request, User currentUser) {
        String oldUsername = currentUser.getUsername();
        String newUsername = request.getUsername();

        if (!oldUsername.equals(newUsername)) {
            if (userRepository.existsById(newUsername)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
            }

            userRepository.updateUsername(oldUsername, newUsername);
            noteRepository.updateOwnerUsername(oldUsername, newUsername);

            currentUser.setUsername(newUsername);
        }

        currentUser.setGravatarEmail(request.getGravatarEmail());
        User saved = userRepository.save(currentUser);

        return UserResponse.builder()
                .username(saved.getUsername())
                .role(currentUser.getRole())
                .gravatarUrl(GravatarUtil.generateGravatarUrl(saved.getGravatarEmail()))
                .build();
    }

}