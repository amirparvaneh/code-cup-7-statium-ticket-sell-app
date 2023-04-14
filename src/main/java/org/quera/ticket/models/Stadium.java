package org.quera.ticket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stadiums")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", unique = true)
    @NotBlank
    String name;

    @Column(name = "capacity")
    @NotNull
    @Min(1)
    Long capacity;

    @OneToMany(mappedBy = "stadium", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    List<Match> matches = new ArrayList<>();
}