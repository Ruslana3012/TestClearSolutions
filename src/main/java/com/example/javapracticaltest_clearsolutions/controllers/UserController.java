package com.example.javapracticaltest_clearsolutions.controllers;

import com.example.javapracticaltest_clearsolutions.models.User;
import com.example.javapracticaltest_clearsolutions.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create/new/user")
    public User createNewUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update/user/{email}")
    public User updateUserFields(@PathVariable String email, @RequestBody User user) {
        return userService.updateUserFields(email, user);
    }

    @DeleteMapping("/delete/{email}")
    public void deleteUser(@PathVariable(name = "email") String email) {
        userService.deleteUser(email);
    }

    @GetMapping("/get/users/{from}/{to}")
    public List<User> getUsersByBirthDate(@PathVariable(name = "from") String from,
                                          @PathVariable(name = "to") String to) throws Exception {
        return userService.findUsersByBirthDate(from, to);
    }
}
