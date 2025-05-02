package rut.miit.k2pgshard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rut.miit.k2pgshard.dto.UserDto;
import rut.miit.k2pgshard.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}