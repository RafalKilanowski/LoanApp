package com.kilanowski.app.domain;

import com.kilanowski.app.domain.port.ExtendLoanPort;
import com.kilanowski.app.domain.port.RetrieveLoanPort;
import com.kilanowski.app.domain.request.ExtendLoanRequest;
import com.kilanowski.app.domain.view.LoanView;
import com.kilanowski.app.exception.InvalidRequestException;
import com.kilanowski.app.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Period;

@Service
@AllArgsConstructor
public class LoanService implements RetrieveLoanPort, ExtendLoanPort {

    private static Period EXTENSION_TERM = Period.ofDays(200);

    private LoanRepository repository;

    @Override
    public LoanView retrieve(String loanId) {
        return repository.findLoanById(loanId).orElseThrow(() ->
                new ResourceNotFoundException("No loan with id : " + loanId));
    }

    @Override
    public void extend(ExtendLoanRequest request) {
        Loan loan = repository.findById(request.getLoanId())
                .orElseThrow(() -> new ResourceNotFoundException("No loan for id : " + request.getLoanId()));
        if (loan.isExtended()) {
            throw new InvalidRequestException("Loan with id " + request.getLoanId() + " is already extended");
        }

        loan.extend(EXTENSION_TERM);
        repository.save(loan);
    }
}
