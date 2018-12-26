package com.kilanowski.app.domain;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.CEILING;

@Service
public class LoanInterestCalculator {

    private static final BigDecimal INTEREST = valueOf(1.1).setScale(2, CEILING);

    public BigDecimal calculate(BigDecimal loanAmount) {
        return loanAmount.multiply(INTEREST);
    }
}
