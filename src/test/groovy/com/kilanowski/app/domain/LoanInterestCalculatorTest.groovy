package com.kilanowski.app.domain

import spock.lang.Specification

class LoanInterestCalculatorTest extends Specification {

    private LoanInterestCalculator calculator = new LoanInterestCalculator()


    def "should calculate interest with 10%"() {
        given:
        BigDecimal loanAmount = new BigDecimal(3333.00)

        when:
        BigDecimal loanWithInterest = calculator.calculate(loanAmount)

        then:
        loanWithInterest == BigDecimal.valueOf(3666.30)
    }



}
