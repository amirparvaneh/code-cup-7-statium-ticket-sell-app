package org.quera.ticket.controller;

import org.quera.ticket.models.User;
import org.quera.ticket.security.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/{id}/update_balance")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestParam BigDecimal balance) {
        User user = userService.findUserById(id);
        if (Objects.nonNull(user)){
            user.setBalance(balance);
            userService.saveUser(user);
            return ResponseEntity.ok(user);
        }else
            return null;
    }
}
