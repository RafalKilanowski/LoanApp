package com.kilanowski.app.rest;


import com.kilanowski.app.domain.port.ExtendLoanPort;
import com.kilanowski.app.domain.request.ExtendLoanRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> apply(@PathVariable String loanId) {
        ExtendLoanRequest request = new ExtendLoanRequest(loanId);
        port.extend(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
