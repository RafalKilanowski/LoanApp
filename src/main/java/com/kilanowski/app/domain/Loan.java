package com.kilanowski.app.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

@Document(collection = "loans")
@Builder
@Getter
public class Loan {

    @Id
    private String id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal amountWithInterest;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private Period period;

    @NotNull
    private Instant creationTime;


    public void extend(Period extensionTerm) {
        period = period.plus(extensionTerm);
    }
}
