package com.msdio.belajar_spring_boot.controller;

import com.msdio.belajar_spring_boot.entity.User;
import com.msdio.belajar_spring_boot.model.RegisterUserRequest;
import com.msdio.belajar_spring_boot.model.UpdateUserRequest;
import com.msdio.belajar_spring_boot.model.UserResponse;
import com.msdio.belajar_spring_boot.model.WebResponse;
import com.msdio.belajar_spring_boot.repository.UserRepository;
import com.msdio.belajar_spring_boot.service.AuthService;
import com.msdio.belajar_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterUserRequest request){
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/users/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> get(User user){
        UserResponse userResponse = userService.get(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @PatchMapping(
            path = "/api/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest request){
        UserResponse userResponse = userService.update(user, request);

        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @DeleteMapping(
            path = "/api/auth/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user){
        authService.logout(user);
        return WebResponse.<String>builder().data("OK").build();
    }
}
