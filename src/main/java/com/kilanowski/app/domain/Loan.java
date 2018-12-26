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
    private LocalDate endDate;

    @NotNull
    private Period period;

    private LocalDate extendedEndDate;

    @NotNull
    private Instant creationTime;

    private Boolean isExtended;


    public void extend(Period extensionTerm) {
        extendedEndDate = endDate.plus(extensionTerm);
        isExtended = Boolean.TRUE;
    }

    public boolean isExtended() {
        return isExtended;
    }
}
