package ru.denis.katacourse.ProjectBoot.controller;



import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denis.katacourse.ProjectBoot.exception.UserWithThisEmailAlreadyExists;
import ru.denis.katacourse.ProjectBoot.model.Role;
import ru.denis.katacourse.ProjectBoot.model.User;
import ru.denis.katacourse.ProjectBoot.service.RoleService;
import ru.denis.katacourse.ProjectBoot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> user = userService.getAllUsers();

        return user != null && !user.isEmpty()
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        final User user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping()
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserWithThisEmailAlreadyExists u) {
            throw new UserWithThisEmailAlreadyExists("A user with this email already exists");
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.updateUser(user, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserWithThisEmailAlreadyExists u) {
            throw new UserWithThisEmailAlreadyExists("A user with this email already exists");
        }
    }
        @DeleteMapping("{id}")
        public ResponseEntity<?> deleteUser ( @PathVariable("id") int id){
            userService.removeUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }



}
