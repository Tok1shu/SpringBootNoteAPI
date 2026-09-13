package net.tokishu.note.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import net.tokishu.note.annotation.CurrentUser;
import net.tokishu.note.dto.request.ChangePasswordRequest;
import net.tokishu.note.dto.request.UpdateProfileRequest;
import net.tokishu.note.dto.request.UserByAdminRequest;
import net.tokishu.note.dto.response.ApiResponse;
import net.tokishu.note.dto.response.UserResponse;
import net.tokishu.note.model.User;
import net.tokishu.note.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse me(@CurrentUser User sender){
        return userService.getCurrentUserResponse(sender);
    }

    @PutMapping("/me")
    public UserResponse updateMyProfile(@RequestBody @Valid UpdateProfileRequest request, @CurrentUser User sender) {
        return userService.updateCurrentUser(request, sender);
    }

    @PutMapping("/me/password")
    public ApiResponse changePassword(@RequestBody @Valid ChangePasswordRequest request, @CurrentUser User sender) {
        // userService.changePassword(request);
        return ApiResponse.builder().status(200).message("JoJ").build();
    }



    /*
    ADMIN ACTIONS
    */

//    @PostMapping("/new")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<?> newUser(@RequestBody @Valid UserByAdminRequest request, @CurrentUser User sender){
//        return userService.createUserByAdmin(request);
//    }
//
//    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<List<UserResponse>> getAllUsers(@CurrentUser User sender) {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/{username}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public UserResponse getUserByUsername(@PathVariable String username, @CurrentUser User sender) {
//        return userService.getUserByUsername(username);
//    }
//
//
//    @PutMapping("/{username}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public UserResponse editUser(@RequestBody @Valid UserByAdminRequest request, @PathVariable String username, @CurrentUser User sender){
//        return userService.editUser(username, request);
//    }
//
//    @DeleteMapping("/{username}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ApiResponse removeUser(@PathVariable String username, @CurrentUser User sender){
//        return userService.deleteUser(username);
//    }
}
