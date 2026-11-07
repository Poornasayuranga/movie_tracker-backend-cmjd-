package com.example.demo.Controller;

import com.example.demo.Dto.TheatreDto;
import com.example.demo.Entity.Theatre;
import com.example.demo.Repository.TheatreRepository;
import com.example.demo.Service.TheatreService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatre")

public class TheatreController {
    public TheatreService theatreService;

    public TheatreController(TheatreService theatreService){
        this.theatreService = theatreService;}


    @GetMapping
    public List<Theatre> getAllTheatre(){
        return theatreService.getAllTheatre();
    }

    @GetMapping("/{id}")
    public Theatre getTheatreById(@PathVariable String id){
        return theatreService.getTheatreById(id);
    }

    @PostMapping
    public Theatre addTheatre(@RequestBody TheatreDto theatreDto){
        return theatreService.createTheatre(theatreDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTheatre(@PathVariable String id){
        theatreService.deleteTheatre(id);
    }

    @PutMapping
    public Theatre updateTheatre(@RequestBody TheatreDto theatreDto, @PathVariable String id){
        return theatreService.updateTheatre(theatreDto, id);
    }

}
