package org.quera.ticket.controller;

import org.quera.ticket.dto.request.StadiumRequestDto;
import org.quera.ticket.dto.response.StadiumResponseDto;
import org.quera.ticket.models.Stadium;
import org.quera.ticket.repository.UserRepository;
import org.quera.ticket.service.StadiumServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping(value = "/stadiums")
public class StadiumController {

    private final StadiumServiceImpl stadiumService ;

    private final UserRepository userRepository;

    public StadiumController(StadiumServiceImpl stadiumService,UserRepository userRepository){
        this.stadiumService = stadiumService;
        this.userRepository = userRepository;
    }


    @GetMapping
    public ResponseEntity<List<Stadium>> getAllStadium(){
        List<Stadium> stadiums = stadiumService.getAllStadium();
        return ResponseEntity.ok(stadiums);
    }


    @PostMapping
    public ResponseEntity<StadiumResponseDto> addNewStadium(@RequestBody StadiumRequestDto stadiumRequestDto,
                                                            Authentication authentication){

        try{
            if (stadiumRequestDto.getCapacity() < 0){
                StadiumResponseDto stadiumResponseDto = new StadiumResponseDto();
                stadiumResponseDto.setMessage("Error: invalid balance");
                return ResponseEntity.badRequest().body(stadiumResponseDto);
            }else if (!userRepository.existsById())
        }
    }
}
