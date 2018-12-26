package com.kilanowski.app.domain.port;

import com.kilanowski.app.domain.request.ExtendLoanRequest;


public interface ExtendLoanPort {

    void extend(ExtendLoanRequest request);
}
