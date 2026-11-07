package com.example.demo.Controller;

import com.example.demo.Dto.ShowDto;
import com.example.demo.Entity.Show;
import com.example.demo.Service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")

public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public List<Show> getShows(){
        return showService.getAllShows();
    }

    @GetMapping("/{id}")
    public Show getShowById(@PathVariable Long id){
    return showService.getShowById(id);
    }

    @PostMapping
    public Show createShow(@RequestBody ShowDto showDto){
        return showService.createShow(showDto);
    }

    @DeleteMapping("/{id}")
    public void deleteShow(@PathVariable Long id){
        showService.deleteShowById(id);
    }

//    @PutMapping("/{id}")
//    public Show updateShow(@RequestBody ShowDto showDto,Long id){
//        return showService.updateShow(showDto, id);
//    }

}
