package org.quera.ticket.service;

import org.quera.ticket.dto.request.StadiumRequestDto;
import org.quera.ticket.models.Stadium;
import org.quera.ticket.repository.StadiumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StadiumServiceImpl {

    private final StadiumRepository stadiumRepository;

    public StadiumServiceImpl(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }

    public List<Stadium> getAllStadium() {
        List<Stadium> stadiums;
        stadiums = stadiumRepository.findAll();
        return stadiums;
    }

    public void addNewStadium(StadiumRequestDto stadiumRequestDto){
        Stadium stadium = new Stadium();

    }
}
