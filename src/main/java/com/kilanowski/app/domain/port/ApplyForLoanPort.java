package com.kilanowski.app.domain.port;

import com.kilanowski.app.domain.request.ApplyForLoanRequest;
import com.kilanowski.app.domain.view.LoanView;

public interface ApplyForLoanPort {

    LoanView apply(ApplyForLoanRequest request);

}
