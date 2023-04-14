package org.quera.ticket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    @NotBlank
    String name;

    @OneToMany(mappedBy = "home", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    List<Match> homeMatches = new ArrayList<>();

    @OneToMany(mappedBy = "away", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    List<Match> awayMatches = new ArrayList<>();
}