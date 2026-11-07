package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDto {

    private Long id;
    private Long movieId;
    private String theatreId;
    private String showDate;
    private String showTime;
    private Double ticketPrice;
}