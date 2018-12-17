package com.kilanowski.app.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kilanowski.app.domain.Loan
import com.kilanowski.app.domain.LoanRepository
import com.kilanowski.app.domain.request.ApplyForLoanRequest
import com.kilanowski.time.CurrentTimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDate
import java.time.Period

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class ApplyForLoanControllerTest extends Specification {

    public static final Period TERM_OF_LOAN = Period.ofDays(100)
    public static final int AMOUNT_OF_LOAN = 10000
    public static final LocalDate START_DATE_OF_LOAN = LocalDate.of(2018, 12, 24)
    @Autowired
    private MockMvc mockMvc

    @Autowired
    private LoanRepository repository

    @Autowired
    private CurrentTimeService timeService

    private ObjectWriter objectWriter


    def setup() {
        repository.deleteAll()
        ObjectMapper mapper = new ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        mapper.registerModule(new JavaTimeModule())
        objectWriter = mapper.writer().withDefaultPrettyPrinter()
    }

    def "Should create loan based on a request"() {
        given:
        ApplyForLoanRequest request = aRequest()
        String requestAsString = objectWriter.writeValueAsString(request)

        when:
        mockMvc.perform(post("/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsString))
                .andExpect(status().isCreated())
        then:
        repository.count() == 1
        Loan loan = repository.findAll().get(0)
        loan.creationTime.toEpochMilli() == timeService.now().toEpochMilli()
        loan.amount.compareTo(AMOUNT_OF_LOAN) == 0
        loan.amountWithInterest == 11000
        loan.period == TERM_OF_LOAN
    }

    ApplyForLoanRequest aRequest() {
        new ApplyForLoanRequest(AMOUNT_OF_LOAN, START_DATE_OF_LOAN, TERM_OF_LOAN)
    }
}
