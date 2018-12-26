package com.kilanowski.app.domain;


import com.kilanowski.app.domain.port.RetrieveLoanConfigPort;
import com.kilanowski.app.domain.view.LoanConfigView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.CEILING;

@Service
@AllArgsConstructor
public class LoanConfigService implements RetrieveLoanConfigPort {

    private static BigDecimal MIN_AMOUNT = valueOf(1000).setScale(2, CEILING);
    private static BigDecimal MAX_AMOUNT = valueOf(300000).setScale(2, CEILING);
    private static Period MIN_TERM = Period.ofDays(30);
    private static Period MAX_TERM = Period.ofDays(3000);


    private LoanConfigRepository repository;

    @Override
    public LoanConfigView retrieve() {
        List<LoanConfig> loanConfigs = repository.findAll();
        if (isOnlyOneConfiguration(loanConfigs)) {
            return toLoanConfigView(loanConfigs.get(0));
        }
        return getDefaultLoanConfig();
    }

    private boolean isOnlyOneConfiguration(List<LoanConfig> loanConfigs) {
        return loanConfigs.size() == 1;
    }


    private LoanConfigView getDefaultLoanConfig() {
        return LoanConfigView.builder()
                .minAmount(MIN_AMOUNT)
                .maxAmount(MAX_AMOUNT)
                .minTerm(MIN_TERM)
                .maxTerm(MAX_TERM)
                .build();
    }

    private LoanConfigView toLoanConfigView(LoanConfig loanConfig) {
        return LoanConfigView.builder()
                .minAmount(loanConfig.getMinAmount())
                .maxAmount(loanConfig.getMaxAmount())
                .maxTerm(loanConfig.getMaxTerm())
                .minTerm(loanConfig.getMinTerm())
                .build();
    }
}
