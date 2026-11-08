
package com.example.demo.Service;

import com.example.demo.Dto.BookingDto;
import com.example.demo.Entity.Booking;
import com.example.demo.Entity.Show;
import com.example.demo.Entity.Theatre;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;

    public BookingService(
            BookingRepository bookingRepository,
            ShowRepository showRepository) {

        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
    }

    // CREATE BOOKING
    public Booking createBooking(BookingDto dto) {

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        // Get theatre capacity
        Theatre theatre = show.getTheatre();

        int capacity = theatre.getCapacity();

        // Get total booked tickets
        int bookedTickets =
                bookingRepository.getTotalBookedTickets(show.getId());

        // Calculate remaining seats
        int remainingSeats = capacity - bookedTickets;

        // Validate ticket count
        if (dto.getNumberOfTickets() <= 0) {
            throw new RuntimeException(
                    "Ticket count must be greater than zero");
        }

        if (dto.getNumberOfTickets() > remainingSeats) {
            throw new RuntimeException(
                    "Not enough seats available. Remaining seats: "
                            + remainingSeats);
        }

        // Validate seat numbers
        if (dto.getSeatNumbers() == null ||
                dto.getSeatNumbers().size() != dto.getNumberOfTickets()) {

            throw new RuntimeException(
                    "Seat numbers must match the ticket count");
        }

        // Create booking
        Booking booking = new Booking();

        booking.setUserId(dto.getUserId());
        booking.setBookingDate(dto.getBookingDate());
        booking.setNumberOfTickets(dto.getNumberOfTickets());
        booking.setSeatNumbers(dto.getSeatNumbers());
        booking.setShow(show);

        // Calculate total amount using backend
        booking.setTotalAmount(
                dto.getNumberOfTickets() * show.getTicketPrice()
        );

        return bookingRepository.save(booking);
    }

    // UPDATE BOOKING
    public Booking updateBooking(BookingDto dto, Long id) {

        Booking existBooking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        int capacity = show.getTheatre().getCapacity();

        int bookedTickets =
                bookingRepository.getTotalBookedTickets(show.getId());

        // Exclude the current booking if updating the same show
        if (existBooking.getShow().getId().equals(show.getId())) {
            bookedTickets -= existBooking.getNumberOfTickets();
        }

        int remainingSeats = capacity - bookedTickets;

        if (dto.getNumberOfTickets() <= 0 ||
                dto.getNumberOfTickets() > remainingSeats) {

            throw new RuntimeException(
                    "Invalid ticket count. Remaining seats: "
                            + remainingSeats);
        }

        if (dto.getSeatNumbers() == null ||
                dto.getSeatNumbers().size() != dto.getNumberOfTickets()) {

            throw new RuntimeException(
                    "Seat numbers must match the ticket count");
        }

        existBooking.setBookingDate(dto.getBookingDate());
        existBooking.setNumberOfTickets(dto.getNumberOfTickets());
        existBooking.setSeatNumbers(dto.getSeatNumbers());
        existBooking.setShow(show);

        existBooking.setTotalAmount(
                dto.getNumberOfTickets() * show.getTicketPrice()
        );

        return bookingRepository.save(existBooking);
    }

    // DELETE BOOKING
    public void deleteById(Long id) {

        Booking existBooking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        bookingRepository.delete(existBooking);
    }

    // GET ALL BOOKINGS
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    // GET BOOKING BY ID
    public Booking getBookingById(Long id) {

        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));
    }

    // GET REMAINING SEATS FOR A SHOW
    public int getRemainingSeats(Long showId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        int capacity = show.getTheatre().getCapacity();

        int bookedTickets =
                bookingRepository.getTotalBookedTickets(showId);

        return capacity - bookedTickets;
    }
}
