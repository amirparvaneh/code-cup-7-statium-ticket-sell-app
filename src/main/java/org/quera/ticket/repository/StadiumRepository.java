package org.quera.ticket.repository;

import org.quera.ticket.models.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium,Long> {
}
