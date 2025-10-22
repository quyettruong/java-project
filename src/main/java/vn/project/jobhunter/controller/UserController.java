package vn.project.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.domain.dto.ResultPaginationDTO;
import vn.project.jobhunter.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/create")
    public String createMethodName() {

        // User user = new User();
        // user.setName("quyet");
        // user.setEmail("Quyet@gmail.com");
        // user.setPassword("852258789");
        // this.userService.handleCreateUser(user);

        return "create for fun";
    }

    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> getUser(
            @Filter Specification<User> spec, Pageable pageable) {
        // String sCurrent = currentOptional.isPresent() ? currentOptional.get() : "";
        // String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() :
        // "";

        // int current = Integer.parseInt(sCurrent);
        // int pageSize = Integer.parseInt(sPageSize);

        // Pageable pageable = PageRequest.of(current - 1, pageSize);

        // List<User> getAllUserById = this.userService.handleGetAllUser(pageable);
        return ResponseEntity.ok(this.userService.handleGetAllUser(spec, pageable));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {

        User getUserById = this.userService.handleGetUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(getUserById);

    }

    @PostMapping("/users")
    public ResponseEntity<User> postMethodName(@RequestBody User createdUser) {
        String HashCode = passwordEncoder.encode(createdUser.getPassword());
        createdUser.setPassword(HashCode);
        User newUser = this.userService.handleCreateUser(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteMethodName(@PathVariable("id") long id) {
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User newInfo) {
        User updateUser = userService.handleUpdateUser(newInfo);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }
}
