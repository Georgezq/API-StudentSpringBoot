package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class UserController {
    private  final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUser(){
        return userService.getUser();
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long userId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String email){
        userService.updateUser(userId, name, email);
    }
}
