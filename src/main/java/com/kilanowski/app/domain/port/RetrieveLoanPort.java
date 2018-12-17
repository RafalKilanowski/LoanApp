package com.kilanowski.app.domain.port;


import com.kilanowski.app.domain.view.LoanView;

public interface RetrieveLoanPort {

    LoanView retrieve(String loanId);

}
