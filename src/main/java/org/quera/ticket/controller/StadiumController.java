package org.quera.ticket.controller;

import org.quera.ticket.dto.request.StadiumRequestDto;
import org.quera.ticket.dto.response.StadiumResponseDto;
import org.quera.ticket.models.Stadium;
import org.quera.ticket.repository.UserRepository;
import org.quera.ticket.security.services.UserDetailsImpl;
import org.quera.ticket.service.StadiumServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


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

        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getId();
        StadiumResponseDto stadiumResponseDto = new StadiumResponseDto();
        try{
            if (stadiumRequestDto.getCapacity() < 0){
                stadiumResponseDto.setMessage("Error: invalid balance");
                return ResponseEntity.badRequest().body(stadiumResponseDto);
            }else if (!userRepository.existsById(userId)){
                stadiumResponseDto.setMessage("Error: user not found");
            }else {
                stadiumService.addNewStadium(stadiumRequestDto);
                stadiumResponseDto.setMessage("saved");
                return ResponseEntity.ok(stadiumResponseDto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(stadiumResponseDto);
    }
}
