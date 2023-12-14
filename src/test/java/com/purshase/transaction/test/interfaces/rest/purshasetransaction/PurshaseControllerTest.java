package com.purshase.transaction.test.interfaces.rest.purshasetransaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purshase.transaction.test.application.service.PurshaseTransactionService;
import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeRequest;
import com.purshase.transaction.test.domain.model.dto.exchange.ExchangeResponse;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionRequest;
import com.purshase.transaction.test.domain.model.dto.purshase.PurshaseTransactionResponse;
import com.purshase.transaction.test.interfaces.rest.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurshaseController.class)
@AutoConfigureMockMvc(addFilters = false)
@Execution(ExecutionMode.SAME_THREAD)
public class PurshaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PurshaseTransactionService purshaseTransactionService;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc =
                MockMvcBuilders
                        .standaloneSetup(
                                new PurshaseController(purshaseTransactionService))
                        .setControllerAdvice(new GlobalExceptionHandler())
                        .build();
    }
    @Test
    void purshase() throws Exception {
        var request = PurshaseTransactionRequest
                .builder()
                .transactionDate(LocalDate.now())
                .description("test")
                .amount(BigDecimal.TEN)
                .build();

        var id = UUID.randomUUID().toString();

        var response = PurshaseTransactionResponse
                .builder()
                .id(id)
                .transactionDate(request.getTransactionDate())
                .description(request.getDescription())
                .amount(request.getAmount())
                .build();

        when(purshaseTransactionService.write(eq(request))).thenReturn(response);
        var responseAsString = mockMvc.perform(MockMvcRequestBuilders.post("/v1/purshase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(responseAsString, "Verifying if is null");
        var data = objectMapper.readValue(responseAsString, PurshaseTransactionResponse.class);
        assertNotNull(data, "Verifying if is null");
        assertNotNull(data.getId(), "Verifying if is null");
        assertEquals(data.getId(), id, "Verifying if size has matched");
        assertEquals(data.getDescription(), request.getDescription(), "Verifying if size has matched");
        assertEquals(data.getAmount(), request.getAmount(), "Verifying if size has matched");
        assertEquals(data.getTransactionDate(), request.getTransactionDate(), "Verifying if size has matched");

    }
    @Test
    void purshaseInvalidRequests() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/purshase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PurshaseTransactionRequest
                                .builder()
                                .transactionDate(LocalDate.now())
                                .amount(BigDecimal.TEN)
                                .build())))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/purshase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PurshaseTransactionRequest
                                .builder()
                                .description("test")
                                .amount(BigDecimal.TEN)
                                .build())))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/purshase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PurshaseTransactionRequest
                                .builder()
                                .description("test")
                                .transactionDate(LocalDate.now())
                                .build())))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/purshase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                PurshaseTransactionRequest
                                .builder()
                                        .build())))
                .andExpect(status().isBadRequest());

    }
    @Test
    void convertExchange() throws Exception {

        var id = UUID.randomUUID().toString();

        var response = ExchangeResponse
                .builder()
                .id(id)
                .transactionDate(LocalDate.now())
                .description("teste")
                .originalAmount(BigDecimal.TEN)
                .exchangeRate(BigDecimal.valueOf(1.5))
                .convertedAmount(BigDecimal.valueOf(15))
                .build();

        when(purshaseTransactionService.convertExchange(any(ExchangeRequest.class))).thenReturn(response);
        var responseAsString = mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/v1/purshase/%s",id))
                        .param("targetExchange", "Canada-Dollar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(responseAsString, "Verifying if is null");
        var data = objectMapper.readValue(responseAsString, ExchangeResponse.class);
        assertNotNull(data, "Verifying if is null");
        assertNotNull(data.getId(), "Verifying if is null");
        assertEquals(data.getId(), id, "Verifying if size has matched");
        assertEquals(data.getDescription(), response.getDescription(), "Verifying if size has matched");
        assertEquals(data.getOriginalAmount(), response.getOriginalAmount(), "Verifying if size has matched");
        assertEquals(data.getTransactionDate(), response.getTransactionDate(), "Verifying if size has matched");
        assertEquals(data.getTransactionDate(), response.getTransactionDate(), "Verifying if size has matched");
        assertEquals(data.getExchangeRate(), response.getExchangeRate(), "Verifying if size has matched");
        assertEquals(data.getConvertedAmount(), response.getConvertedAmount(), "Verifying if size has matched");
    }
    @Test
    void convertExchangeInvalidRequests() throws Exception {
        var id = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/v1/purshase/%s",id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/purshase/")
                        .param("targetExchange", "Canada-Dollar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/purshase")
                        .param("targetExchange", "Canada-Dollar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());


    }

    @Configuration
    @Import({PurshaseController.class, GlobalExceptionHandler.class})
    public static class SpringTestConfig {
    }
}
