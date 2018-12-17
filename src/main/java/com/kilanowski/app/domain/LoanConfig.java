package com.kilanowski.app.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Period;

@Builder
@AllArgsConstructor
@Getter
@Document(collection = "loanConfig")
public class LoanConfig {

    @Id
    private String id;

    private Period maxTerm;

    private Period minTerm;

    private BigDecimal maxAmount;

    private BigDecimal minAmount;

    @Version
    private Integer version;
}
