package com.kilanowski.app.domain

import com.kilanowski.app.domain.request.ExtendLoanRequest
import com.kilanowski.app.exception.InvalidRequestException
import com.kilanowski.app.exception.ResourceNotFoundException
import spock.lang.Specification

class LoanServiceTest extends Specification {

    private LoanRepository loanRepository = Mock()

    private LoanService service = new LoanService(loanRepository)

    def "should throw ResourceNotFoundException when loan for given id does not exists"() {
        given:
        ExtendLoanRequest request = new ExtendLoanRequest("NON_EXISTING_LOAN")
        loanRepository.findById(_) >> Optional.empty()

        when:
        service.extend(request)

        then:
        thrown ResourceNotFoundException
    }

    def "should throw InvalidRequestException when trying to extend a loan which is already extended"() {
        given:
        ExtendLoanRequest request = new ExtendLoanRequest("EXISTING_LOAN")
        loanRepository.findById(_) >> Optional.of(LoanMotherObject.aLoanWithExtension())

        when:
        service.extend(request)

        then:
        thrown InvalidRequestException
    }


    def "should successfully extend a loan"() {
        given:
        ExtendLoanRequest request = new ExtendLoanRequest("EXISTING_LOAN")
        loanRepository.findById(_) >> Optional.of(LoanMotherObject.aLoanWithoutExtension())

        when:
        service.extend(request)

        then:
        noExceptionThrown()
        1 * loanRepository.save(_)
    }
}
