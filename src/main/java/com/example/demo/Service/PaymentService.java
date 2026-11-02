package com.example.demo.Service;


import com.example.demo.Dto.BookingDto;
import com.example.demo.Dto.PaymentDto;
import com.example.demo.Dto.ShowDto;
import com.example.demo.Entity.Booking;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.Show;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.PaymentRepository;
import com.example.demo.Repository.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private ShowRepository showRepository;
    private BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository, ShowRepository showRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
    }

    public Payment createPayment(PaymentDto paymentDto) {

        Payment payment = new Payment();

        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setPaymentMethod(paymentDto.getPaymentMethod());
        payment.setId(payment.getId());

        return paymentRepository.save(payment);
    }


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(final Long id) {
        return paymentRepository.getOne(id);
    }

    public void DeletePaymentById(final Long id) {
        paymentRepository.deleteById(id);
    }

    public Payment updatePayment(PaymentDto paymentDto, Long id) {
        Payment existPayment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found with id: " + id)
                );

        existPayment.setPaymentDate(paymentDto.getPaymentDate());
        existPayment.setPaymentMethod(paymentDto.getPaymentMethod());
        existPayment.setAmount(paymentDto.getAmount());


        return paymentRepository.save(existPayment);
    }

    // Implement the total cost-------------------------------------------------------------------------------

    public Payment createPayment(
            ShowDto showDto, BookingDto bookingDto) {


        Show show = showRepository.findById(showDto.getShowId())
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        Booking booking = bookingRepository.findById(bookingDto.getBookingId())
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        Double priceOfTickets = show.getTicketPrice();
        int numberOfTicket =booking.getNumberOfTickets();
        Double totalPrice = priceOfTickets * numberOfTicket;

        Payment payment = new Payment();
        payment.setAmount(totalPrice);


        Payment savedPayment =
                paymentRepository.save(payment);

        return savedPayment;


    }
}

