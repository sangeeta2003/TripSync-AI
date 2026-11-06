package com.tripsync.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter

public class Requests {
    private Requests(){

    }
    public record Signup(@NotBlank String name , @Email @NotBlank String email, @NotBlank @Size(min = 8,max = 72)String password){

    }
    public record Login(@Email @NotBlank String email,@NotBlank String password){

    }
    public record CreateTrip(@NotBlank String title, @NotBlank String destination, @NotNull LocalDate startDate, @NotNull LocalDate endDate, @NotNull @DecimalMin("0.00")
                             BigDecimal budget, @NotBlank @Size(min = 3,max = 3) String currency){

    }
    public record Invite(@Email @NotBlank String email) {
    }

    public record Itinerary(@Min(1) int dayNumber, @NotBlank String title, String description, String location,
                            LocalTime startTime, @NotNull @DecimalMin("0.00") BigDecimal estimatedCost, @Min(0) int sortOrder) {
    }

    public record Expense(@NotBlank String title, @NotBlank String category,
                          @NotNull @DecimalMin("0.01") BigDecimal amount, @NotBlank @Size(min = 3, max = 3) String currency,
                          @NotEmpty List<UUID> participantIds) {
    }

    public record Chat(@NotBlank @Size(max = 4000) String content) {
    }

    public record AiTrip(@NotBlank String destination, @NotNull LocalDate startDate, @NotNull LocalDate endDate,
                         @NotNull BigDecimal budget, @NotBlank String currency, @Min(1) int travelers, String preferences) {
    }

    public record AiBudget(@NotBlank String destination, @NotNull BigDecimal totalBudget, @Min(1) int travelers,
                           @Min(1) int days, @NotBlank String currency) {
    }

    public record AiExpense(@NotBlank String context, @NotNull BigDecimal amount, @NotBlank String currency,
                            @Min(1) int participants) {
    }


}
