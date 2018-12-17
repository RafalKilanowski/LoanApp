package com.kilanowski.app.rest;


import com.kilanowski.app.domain.port.ExtendLoanPort;
import com.kilanowski.app.domain.request.ExtendLoanRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/loan")
@AllArgsConstructor
public class ExtendLoanController {

    private ExtendLoanPort port;

    @PostMapping("/{loanId}")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Extend loan for given id !"),
            @ApiResponse(code = 400, message = "Bad extend loan request !")
    })
    public ResponseEntity<Void> apply(@RequestBody @Valid ExtendLoanRequest request, @PathVariable String loanId) {
        port.apply(loanId, request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
