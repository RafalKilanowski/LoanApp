package com.kilanowski.app.domain

import java.time.Instant
import java.time.LocalDate
import java.time.Period

class LoanMotherObject {


    private static final LocalDate START_DATE = LocalDate.of(2019, 1, 1)
    private static final Period LOAN_PERIOD = Period.ofDays(100)
    private static final BigDecimal LOAN_AMOUNT = BigDecimal.valueOf(1000)
    private static final BigDecimal LOAN_AMOUNT_WITH_INTEREST = BigDecimal.valueOf(1100)

    static Loan aLoanWithoutExtension() {
        Loan.builder()
                .amount(LOAN_AMOUNT)
                .amountWithInterest(LOAN_AMOUNT_WITH_INTEREST)
                .startDate(START_DATE)
                .period(LOAN_PERIOD)
                .endDate(START_DATE.plus(LOAN_PERIOD))
                .creationTime(Instant.ofEpochMilli(1000000L))
                .isExtended(Boolean.FALSE)
                .build()
    }

    static Loan aLoanWithExtension() {
        Loan.builder()
                .amount(LOAN_AMOUNT)
                .amountWithInterest(LOAN_AMOUNT_WITH_INTEREST)
                .startDate(START_DATE)
                .period(LOAN_PERIOD)
                .endDate(START_DATE.plus(LOAN_PERIOD))
                .creationTime(Instant.ofEpochMilli(1000000L))
                .isExtended(Boolean.TRUE)
                .build()
    }
}
