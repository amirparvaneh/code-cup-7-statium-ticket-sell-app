package org.quera.ticket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seat_classes")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class SeatClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "min_number")
    Long minNumber;

    @Column(name = "max_number")
    Long maxNumber;

    @ManyToOne
    @JoinColumn(name = "match_id")
    Match match;

    @Column(name = "price")
    @Digits(integer = 15, fraction = 2)
    BigDecimal price;

    @OneToMany(mappedBy = "seatClass", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    List<Ticket> tickets = new ArrayList<>();
}