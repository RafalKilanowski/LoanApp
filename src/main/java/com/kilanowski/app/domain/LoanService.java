package com.kilanowski.app.domain;

import com.kilanowski.app.domain.port.ExtendLoanPort;
import com.kilanowski.app.domain.port.RetrieveLoanPort;
import com.kilanowski.app.domain.request.ExtendLoanRequest;
import com.kilanowski.app.domain.view.LoanConfigView;
import com.kilanowski.app.domain.view.LoanView;
import com.kilanowski.app.exception.InvalidRequestException;
import com.kilanowski.app.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoanService implements RetrieveLoanPort, ExtendLoanPort {

    private LoanRepository repository;

    private LoanConfigService loanConfigService;

    @Override
    public LoanView retrieve(String loanId) {
        return repository.findLoanById(loanId).orElseThrow(() ->
                new ResourceNotFoundException("No loan with id : " + loanId));
    }

    @Override
    public void apply(String loanId, ExtendLoanRequest request) {
        Loan loan = repository.findById(loanId).orElseThrow(() -> new ResourceNotFoundException("No loan for id : " + loanId));
        assertTermOfLoanIsBelowMaxTerm(loan, request);
        loan.extend(request.getExtensionTerm());
        repository.save(loan);

    }

    private void assertTermOfLoanIsBelowMaxTerm(Loan loan, ExtendLoanRequest request) {
        LoanConfigView loanConfigView = loanConfigService.retrieve();
        if (loan.getPeriod().plus(request.getExtensionTerm()).getDays() > loanConfigView.getMaxTerm().getDays()) {
            throw new InvalidRequestException("Extension term is above max term of a loan!");
        }
    }
}
