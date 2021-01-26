package org.scd.controller;

import org.scd.config.exception.BusinessException;
import org.scd.model.User;
import org.scd.model.dto.UserLoginDTO;
import org.scd.model.dto.UserRegisterDTO;
import org.scd.model.dto.UserUpdateDTO;
import org.scd.model.security.CustomUserDetails;
import org.scd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(path = "/me")
    public ResponseEntity<User> getCurrentUser() {
        final User currentUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return ResponseEntity.ok(currentUser);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<User> loginUser(@RequestBody final UserLoginDTO userLoginDTO) throws BusinessException {
        return ResponseEntity.ok(userService.login(userLoginDTO));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<User> registerUser(@RequestBody final UserRegisterDTO userRegisterDTO) throws BusinessException{
        return ResponseEntity.ok(userService.register(userRegisterDTO));
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") final long id) throws BusinessException{
        userService.delete(id);
        return "User ID selected = " + id + "; User Deleted";
    }

    @PutMapping(path ="/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody final UserUpdateDTO userUpdateDTO,@PathVariable ("id") final long id)throws BusinessException{
        userService.update(userUpdateDTO,id);
        return ResponseEntity.ok(userService.update(userUpdateDTO,id));
    }
}
