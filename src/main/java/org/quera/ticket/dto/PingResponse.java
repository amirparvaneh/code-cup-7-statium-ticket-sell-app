package org.quera.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PingResponse {

    @JsonProperty(value = "message")
    private String message;
}
