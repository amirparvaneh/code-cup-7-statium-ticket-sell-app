package org.quera.ticket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StadiumResponseDto implements Serializable {

    @JsonProperty(value = "message")
    private String message;
}
