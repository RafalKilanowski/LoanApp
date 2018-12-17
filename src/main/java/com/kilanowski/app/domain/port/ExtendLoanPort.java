package com.kilanowski.app.domain.port;

import com.kilanowski.app.domain.request.ExtendLoanRequest;


public interface ExtendLoanPort {

    void apply(String loanId, ExtendLoanRequest request);
}
