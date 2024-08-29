package org.neo.phonenumresolver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.neo.phonenumresolver.dto.PhoneNumberRequest;
import org.neo.phonenumresolver.model.CallingCode;
import org.neo.phonenumresolver.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneNumberController.class)
public class PhoneNumberControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneNumberService phoneNumberService;

    @InjectMocks
    private PhoneNumberController phoneNumberController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResolvePhoneNumberSuccess() throws Exception {
        PhoneNumberRequest request = new PhoneNumberRequest("123456789");
        CallingCode callingCode = new CallingCode("Country A", "1");
        when(phoneNumberService.findCountryByPhoneNumber("123456789")).thenReturn(Optional.of(callingCode));

        mockMvc.perform(post("/api/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(callingCode)));
    }

    @Test
    void testResolvePhoneNumberNotFound() throws Exception {
        PhoneNumberRequest request = new PhoneNumberRequest("0987654321");
        when(phoneNumberService.findCountryByPhoneNumber("987654321")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Country not found for the given phone number"));
    }


}
