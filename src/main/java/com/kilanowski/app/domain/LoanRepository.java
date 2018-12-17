package com.kilanowski.app.domain;

import com.kilanowski.app.domain.view.LoanView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LoanRepository extends MongoRepository<Loan, String> {

    Optional<LoanView> findLoanById(String loanId);

}
