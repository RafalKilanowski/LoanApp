package com.kilanowski.app.domain;


import com.kilanowski.app.domain.port.ApplyForLoanPort;
import com.kilanowski.app.domain.request.ApplyForLoanRequest;
import com.kilanowski.app.domain.view.LoanConfigView;
import com.kilanowski.app.domain.view.LoanView;
import com.kilanowski.app.exception.InvalidRequestException;
import com.kilanowski.time.CurrentTimeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Log4j2
@AllArgsConstructor
class LoanFactory implements ApplyForLoanPort {

    private static final int MAX_LOAN_HOUR = 6;

    private LoanRepository repository;

    private LoanConfigService loanConfigService;

    private LoanInterestCalculator interestCalculator;

    private CurrentTimeService timeService;

    @Override
    public LoanView apply(ApplyForLoanRequest request) {
        assertIfLoanIsPossible(request);
        Loan loan = buildWith(request);
        repository.save(loan);
        return toLoanView(loan);
    }

    private void assertIfLoanIsPossible(ApplyForLoanRequest request) {
        LoanConfigView loanConfig = loanConfigService.retrieve();
        if (loanConfig.getMaxAmount().equals(request.getAmount())) {
            assertMaxLoanIsInAppropriateTime();
        }
        assertLoanIsWithinTermAndAmountRange(request, loanConfig);
    }

    private void assertMaxLoanIsInAppropriateTime() {
        Instant now = timeService.now();
        LocalDateTime currentTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        if (currentTime.getHour() > MAX_LOAN_HOUR) {
            return;
        }
        log.warn("Error while requesting for a loan - hour is not between 5:59 a.m to 11:59 p.m");
        throw new InvalidRequestException("Invalid loan request ! Not a valid time !");
    }

    private void assertLoanIsWithinTermAndAmountRange(ApplyForLoanRequest request, LoanConfigView loanConfig) {
        if (loanConfig.isAmountValid(request.getAmount()) &&
                loanConfig.isPeriodValid(request.getPeriod())) {
            return;
        }
        log.warn("Error while requesting for a loan - period or amount is not valid. Request : " + request);
        throw new InvalidRequestException("Invalid loan request ! Amount/Term range is not approperiate");
    }

    private Loan buildWith(ApplyForLoanRequest request) {
        BigDecimal loanWithInterest = interestCalculator.calculate(request.getAmount());
        LocalDate endDate = request.getStartDate().plus(request.getPeriod());
        return Loan.builder()
                .amount(request.getAmount())
                .amountWithInterest(loanWithInterest)
                .creationTime(timeService.now())
                .startDate(request.getStartDate())
                .endDate(endDate)
                .isExtended(Boolean.FALSE)
                .period(request.getPeriod())
                .build();
    }


    private LoanView toLoanView(Loan loan) {
        return LoanView.builder()
                .id(loan.getId())
                .amount(loan.getAmount())
                .amountWithInterest(loan.getAmountWithInterest())
                .period(loan.getPeriod())
                .startDate(loan.getStartDate())
                .build();
    }

}
