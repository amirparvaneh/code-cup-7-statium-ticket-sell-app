package org.quera.ticket.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "home_id")
    Team home;

    @ManyToOne(optional = false)
    @JoinColumn(name = "away_id")
    Team away;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stadium_id")
    Stadium stadium;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date date;

    @OneToMany(mappedBy = "match", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    List<SeatClass> seatClasses = new ArrayList<>();
}