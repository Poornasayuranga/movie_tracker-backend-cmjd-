
package com.example.demo.Repository;

import com.example.demo.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Calculate total booked tickets for a specific show
    @Query(value = """
            SELECT COALESCE(SUM(number_of_tickets), 0)
            FROM booking
            WHERE show_id = :showId
            """, nativeQuery = true)
    Integer getTotalBookedTickets(@Param("showId") Long showId);

}
