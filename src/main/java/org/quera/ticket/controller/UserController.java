package org.quera.ticket.controller;

import org.quera.ticket.models.User;
import org.quera.ticket.security.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all-users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}/update_balance")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestParam BigDecimal balance) {
        User user = userService.findUserById(id);
        if (Objects.nonNull(user)) {
            user.setBalance(balance);
            userService.saveUser(user);
            return ResponseEntity.ok(user);
        } else
            return null;
    }
}
