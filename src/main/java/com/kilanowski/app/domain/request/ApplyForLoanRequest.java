package com.kilanowski.app.domain.request;


import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Value
@ToString
public class ApplyForLoanRequest {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private Period period;

}
