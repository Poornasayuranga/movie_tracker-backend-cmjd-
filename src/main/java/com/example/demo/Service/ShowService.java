package com.example.demo.Service;

import com.example.demo.Dto.ShowDto;
import com.example.demo.Entity.Movie;
import com.example.demo.Entity.Show;
import com.example.demo.Entity.Theatre;
import com.example.demo.Repository.MovieRepository;
import com.example.demo.Repository.ShowRepository;
import com.example.demo.Repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;

    public ShowService(
            ShowRepository showRepository,
            MovieRepository movieRepository,
            TheatreRepository theatreRepository) {

        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Show getShowById(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));
    }

    public Show createShow(ShowDto showDto) {

        Movie movie = movieRepository.findById(showDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Theatre theatre = theatreRepository.findById(showDto.getTheatreId())
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        Show show = new Show();

        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowDate(showDto.getShowDate());
        show.setShowTime(showDto.getShowTime());
        show.setTicketPrice(showDto.getTicketPrice());

        return showRepository.save(show);
    }

    public void deleteShowById(Long id) {
        showRepository.deleteById(id);
    }
}