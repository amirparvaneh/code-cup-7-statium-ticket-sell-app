package org.quera.ticket.controller;

import org.quera.ticket.dto.PingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping(value = "/ping")
    public PingResponse validation(){
        PingResponse pingResponse = new PingResponse();
        pingResponse.setMessage("ok");
        return pingResponse;
    }
}
