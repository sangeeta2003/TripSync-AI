package com.tripsync.backend.dto.auth.trip;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTripRequest {
    @NotBlank(message = "Trip title is required")
    @Size(max = 150)
    String title;
    @NotBlank(message = "Destination is required")
    @Size(max = 200)
    String destination;
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    LocalDate startDate;
    @NotNull(message = "End date is required")
    LocalDate endDate;
    @DecimalMin(value = "0.01",message = "Budget must be positive")
    @Digits(integer = 10,fraction = 2)
    BigDecimal budget;
    @Pattern(
            regexp = "^[A-Za-z]{3}$",
            message = "Currency must be a 3-letter code"
    )
    String currency;


}
