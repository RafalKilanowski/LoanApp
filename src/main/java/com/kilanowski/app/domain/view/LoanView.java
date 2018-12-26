package com.kilanowski.app.domain.view;


import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Value
@Builder
@Document(collection = "loans")
public class LoanView {

    private String id;

    private BigDecimal amount;

    private BigDecimal amountWithInterest;

    private LocalDate startDate;

    private LocalDate endDate;

    private Period period;
}
