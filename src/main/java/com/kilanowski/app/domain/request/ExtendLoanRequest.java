package com.kilanowski.app.domain.request;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.Period;

@Value
public class ExtendLoanRequest {

    @NotNull
    private Period extensionTerm;

}
