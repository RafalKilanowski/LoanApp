package com.kilanowski.app.rest;

import com.kilanowski.app.domain.port.ApplyForLoanPort;
import com.kilanowski.app.domain.request.ApplyForLoanRequest;
import com.kilanowski.app.domain.view.LoanView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/loan")
@AllArgsConstructor
public class ApplyForLoanController {

    private ApplyForLoanPort port;

    @PostMapping
    @ApiOperation(value = "Create loan for given request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created loan !"),
            @ApiResponse(code = 400, message = "Bad loan request !")
    })
    public ResponseEntity<LoanView> apply(@RequestBody @Valid ApplyForLoanRequest request) {
        LoanView loanView = port.apply(request);
        return new ResponseEntity<>(loanView, HttpStatus.CREATED);
    }

}
