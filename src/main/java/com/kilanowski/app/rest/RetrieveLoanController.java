package com.kilanowski.app.rest;

import com.kilanowski.app.domain.port.RetrieveLoanPort;
import com.kilanowski.app.domain.view.LoanView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
@AllArgsConstructor
public class RetrieveLoanController {

    private RetrieveLoanPort retrieveLoanPort;

    @GetMapping("/{loanId}")
    @ApiOperation(value = "Retrieve loan for a given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retreive loan !"),
            @ApiResponse(code = 404, message = "No loan for given id !")
    })
    public ResponseEntity<LoanView> retrieve(@PathVariable String loanId) {
        LoanView loanView = retrieveLoanPort.retrieve(loanId);
        return ResponseEntity.ok(loanView);
    }
}
