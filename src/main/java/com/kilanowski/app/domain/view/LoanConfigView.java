package com.kilanowski.app.domain.view;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Period;

@Value
@Builder
public class LoanConfigView {

    private Period maxTerm;

    private Period minTerm;

    private BigDecimal maxAmount;

    private BigDecimal minAmount;

    public boolean isAmountValid(BigDecimal amount) {
        return maxAmount.compareTo(amount) >= 0 && minAmount.compareTo(amount) <= 0;
    }

    public boolean isPeriodValid(Period period) {
        return maxTerm.getDays() >= period.getDays() && minTerm.getDays() <= period.getDays();
    }

}
