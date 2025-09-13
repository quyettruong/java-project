package vn.project.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createMethodName() {

        User user = new User();
        user.setName("quyet");
        user.setEmail("Quyet@gmail.com");
        user.setPassword("852258789");

        this.userService.handleCreateUser(user);

        return "create for fun";
    }

    @PostMapping("/user")
    public User postMethodName(@RequestBody User createdUser) {

        User newUser = this.userService.handleCreateUser(createdUser);

        return newUser;
    }

    @DeleteMapping("/user/{id}")
    public String deleteMethodName(@PathVariable("id") long id) {
        this.userService.handleDeleteUser(id);
        return "newUser";
    }
}
