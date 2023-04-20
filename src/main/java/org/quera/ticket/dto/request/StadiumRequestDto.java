package org.quera.ticket.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class StadiumRequestDto implements Serializable {
    private String name;
    private Long capacity;
}
