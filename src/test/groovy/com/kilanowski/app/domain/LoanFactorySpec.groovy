package com.kilanowski.app.domain

import com.kilanowski.app.domain.request.ApplyForLoanRequest
import com.kilanowski.app.domain.view.LoanConfigView
import com.kilanowski.app.exception.InvalidRequestException
import com.kilanowski.time.CurrentTimeService
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDate
import java.time.Period

import static java.time.Period.ofDays


class LoanFactorySpec extends Specification {

    private static final long INVALID_HOUR_FOR_A_LOAN = 1545008400000L
    private static final long VALID_HOUR_FOR_A_LOAN = 1545037200000L
    private static final int LOAN_MAX_AMOUNT = 10000
    private static final int LOAN_MIN_AMOUNT = 1000
    public static final Period LOAN_MIN_TERM = ofDays(10)
    public static final Period LOAN_MAX_TERM = ofDays(100)
    public static final Period LOAN_VALID_TERM = ofDays(20)
    private LocalDate LOAN_START = LocalDate.of(2018, 12, 24);

    private LoanRepository repository = Mock()

    private LoanConfigService configService = Mock()

    private CurrentTimeService currentTimeService = Mock()

    private LoanFactory loanFactory = new LoanFactory(repository, configService, currentTimeService)


    def "Should return 400 when user is trying to request a loan with max amount between 0 a.m and 6 a.m"() {
        given:
        currentTimeService.now() >> Instant.ofEpochMilli(INVALID_HOUR_FOR_A_LOAN)
        configService.retrieve() >> aLoanConfigView(LOAN_MIN_AMOUNT, LOAN_MAX_AMOUNT, LOAN_MIN_TERM, LOAN_MAX_TERM)

        when:
        ApplyForLoanRequest request = new ApplyForLoanRequest(LOAN_MAX_AMOUNT, LOAN_START, LOAN_VALID_TERM)
        loanFactory.apply(request)

        then:
        thrown InvalidRequestException
    }

    def "should return 400 when trying to request for a loan above max amount"() {
        given:
        currentTimeService.now() >> Instant.ofEpochMilli(VALID_HOUR_FOR_A_LOAN)
        configService.retrieve() >> aLoanConfigView(LOAN_MIN_AMOUNT, LOAN_MAX_AMOUNT, LOAN_MIN_TERM, LOAN_MAX_TERM)
        when:
        ApplyForLoanRequest request = new ApplyForLoanRequest(LOAN_MAX_AMOUNT + 1, LOAN_START, LOAN_VALID_TERM)
        loanFactory.apply(request)

        then:
        thrown InvalidRequestException
    }

    def "should return 400 when trying to request for a loan below min amount"() {
        given:
        currentTimeService.now() >> Instant.ofEpochMilli(VALID_HOUR_FOR_A_LOAN)
        configService.retrieve() >> aLoanConfigView(LOAN_MIN_AMOUNT, LOAN_MAX_AMOUNT, LOAN_MIN_TERM, LOAN_MAX_TERM)
        when:
        ApplyForLoanRequest request = new ApplyForLoanRequest(LOAN_MIN_AMOUNT - 1, LOAN_START, LOAN_VALID_TERM)
        loanFactory.apply(request)

        then:
        thrown InvalidRequestException
    }

    def "should return 400 when trying to request for a loan below min term"() {
        given:
        currentTimeService.now() >> Instant.ofEpochMilli(VALID_HOUR_FOR_A_LOAN)
        configService.retrieve() >> aLoanConfigView(LOAN_MIN_AMOUNT, LOAN_MAX_AMOUNT, LOAN_MIN_TERM, LOAN_MAX_TERM)
        when:
        ApplyForLoanRequest request = new ApplyForLoanRequest(LOAN_MIN_AMOUNT + 1, LOAN_START, LOAN_MIN_TERM.minusDays(1))
        loanFactory.apply(request)

        then:
        thrown InvalidRequestException
    }

    def "should return 400 when trying to request for a loan above max term"() {
        given:
        currentTimeService.now() >> Instant.ofEpochMilli(VALID_HOUR_FOR_A_LOAN)
        configService.retrieve() >> aLoanConfigView(LOAN_MIN_AMOUNT, LOAN_MAX_AMOUNT, LOAN_MIN_TERM, LOAN_MAX_TERM)
        when:
        ApplyForLoanRequest request = new ApplyForLoanRequest(LOAN_MIN_AMOUNT + 1, LOAN_START, LOAN_MAX_TERM.plusDays(1))
        loanFactory.apply(request)

        then:
        thrown InvalidRequestException
    }

    def "should create a loan when proper request is created"() {
        given:
        currentTimeService.now() >> Instant.ofEpochMilli(VALID_HOUR_FOR_A_LOAN)
        configService.retrieve() >> aLoanConfigView(LOAN_MIN_AMOUNT, LOAN_MAX_AMOUNT, LOAN_MIN_TERM, LOAN_MAX_TERM)
        when:
        ApplyForLoanRequest request = new ApplyForLoanRequest(LOAN_MIN_AMOUNT + 1, LOAN_START, LOAN_VALID_TERM)
        loanFactory.apply(request)

        then:
        noExceptionThrown()
        1 * repository.save(_)
    }


    LoanConfigView aLoanConfigView(Integer minAmount, Integer maxAmount, Period minTerm, Period maxTerm) {
        LoanConfigView.builder()
                .minAmount(BigDecimal.valueOf(minAmount))
                .maxAmount(BigDecimal.valueOf(maxAmount))
                .minTerm(minTerm)
                .maxTerm(maxTerm)
                .build()
    }
}
