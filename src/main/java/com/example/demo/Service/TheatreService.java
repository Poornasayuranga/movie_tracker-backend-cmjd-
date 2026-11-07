package com.example.demo.Service;

import com.example.demo.Dto.TheatreDto;
import com.example.demo.Entity.Show;
import com.example.demo.Entity.Theatre;
import com.example.demo.Repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service


public class TheatreService {
    public  TheatreRepository theatreRepository;

    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    public Theatre createTheatre(TheatreDto theatreDto){
        Theatre theatre = new Theatre();

        theatre.setTheatreName(theatreDto.getTheatreName());
        theatre.setTheatreId(theatreDto.getTheatreId());
        theatre.setLocation(theatreDto.getLocation());
        theatre.setTheatreId(theatreDto.getTheatreId());

        return theatreRepository.save(theatre);
    }

    public Theatre getTheatreById(String theatreId) {
        return theatreRepository.findById(theatreId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Theatre not found with id: " + theatreId
                        )
                );
    }


    public List<Theatre> getAllTheatre() {

        return theatreRepository.findAll();
    }

    public Theatre updateTheatre(TheatreDto theatreDto, String theatreId){
        Theatre existTheatre = theatreRepository.findById(theatreId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Theatre not found with id: " + theatreId
                        )
                );

        existTheatre.setTheatreName(theatreDto.getTheatreName());
        existTheatre.setCapacity(theatreDto.getCapacity());
        existTheatre.setLocation(theatreDto.getLocation());

        return theatreRepository.save(existTheatre);
    }

    public void deleteTheatre(String theatreId){
        theatreRepository.deleteById(theatreId);
    }

}
