package com.kilanowski.app.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kilanowski.app.domain.Loan
import com.kilanowski.app.domain.LoanMotherObject
import com.kilanowski.app.domain.LoanRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class ExtendLoanControllerTest extends Specification {

    public static final LocalDate START_DATE_OF_LOAN = LocalDate.of(2018, 12, 24)
    @Autowired
    private MockMvc mockMvc

    @Autowired
    private LoanRepository repository

    private ObjectWriter objectWriter


    def setup() {
        repository.deleteAll()
        ObjectMapper mapper = new ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        mapper.registerModule(new JavaTimeModule())
        objectWriter = mapper.writer().withDefaultPrettyPrinter()
    }


    def "Should extend loan for given loan id"() {
        given:
        Loan loan = LoanMotherObject.aLoanWithoutExtension()
        repository.save(loan)

        when:
        mockMvc.perform(post("/loan/" + loan.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
        then:
        Loan extendedLoan = repository.findById(loan.getId()).get()
        extendedLoan.isExtended == Boolean.TRUE
        extendedLoan.extendedEndDate.compareTo(LocalDate.of(2019, 10, 28)) == 0
    }


}
