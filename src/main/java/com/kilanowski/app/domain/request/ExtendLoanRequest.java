package com.kilanowski.app.domain.request;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ExtendLoanRequest {

    @NotNull
    private String loanId;

}
