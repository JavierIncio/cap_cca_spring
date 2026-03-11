package com.ccsw.tutorial.loan.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDTO {
    private Long id;

    private Long gameId;
    private String gameTitle;

    private Long clientId;
    private String clientName;

    private LocalDate startDate;
    private LocalDate endDate;
}
