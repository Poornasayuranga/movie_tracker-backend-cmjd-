package com.example.demo.Controller;


import com.example.demo.Dto.BookingDto;
import com.example.demo.Entity.Booking;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Service.BookingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private BookingRepository bookingRepository;
    private BookingService bookingService;


    public BookingController(BookingRepository bookingRepository, BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking addBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }

    @GetMapping
    public List<Booking> getBookings() {
        return bookingService.getAllBooking();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id){
        bookingService.deleteById(id);
    }

    @PutMapping
    public Booking updateBooking(@RequestBody BookingDto bookingDto, @PathVariable Long id) {
        return bookingService.updateBooking(bookingDto,id);
    }


    @GetMapping("/remaining-seats/{showId}")
    public ResponseEntity<Integer> getRemainingSeats(
            @PathVariable Long showId) {

        int remainingSeats = bookingService.getRemainingSeats(showId);

        return ResponseEntity.ok(remainingSeats);
    }



}
