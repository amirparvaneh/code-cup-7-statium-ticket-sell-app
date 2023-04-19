package org.quera.ticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping(value = "/ping")
    public ResponseEntity<String> validation(){
        return ResponseEntity.ok("ok");
    }
}
