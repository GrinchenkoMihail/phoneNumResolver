package org.neo.phonenumresolver.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neo.phonenumresolver.model.CallingCode;
import org.neo.phonenumresolver.repository.CallingCodeRepository;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceTest {

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @Mock
    private CallingCodeRepository callingCodeRepository;

    @Test
    void findCountryByPhoneNumber_Found() {

        String phoneNumber = "12025550123";
        CallingCode callingCode = new CallingCode("USA", "1");
        List<CallingCode> mockCountryList = Arrays.asList(callingCode);

        when(callingCodeRepository.getListByPrefix(anyString())).thenReturn(mockCountryList);

        Optional<CallingCode> result = phoneNumberService.findCountryByPhoneNumber(phoneNumber);

        assertTrue(result.isPresent());
        assertEquals("USA", result.get().getCountries());
        assertEquals("1", result.get().getPrefix());
    }

    @Test
    void findCountryByPhoneNumber_NotFound() {
        String phoneNumber = "992025550123";
        when(callingCodeRepository.getListByPrefix(anyString())).thenReturn(Arrays.asList());
        Optional<CallingCode> result = phoneNumberService.findCountryByPhoneNumber(phoneNumber);
        assertTrue(result.isEmpty());
    }

    @Test
    void findCountryByPhoneNumber_MultipleCountriesSameZone() {
        String phoneNumber = "442079460958"; // Великобритания
        CallingCode ukCode = new CallingCode("United Kingdom", "44");
        CallingCode otherCountry = new CallingCode("Country B", "4");
        List<CallingCode> sortedMockCountryList = Stream.of(ukCode,otherCountry)
                .sorted(Comparator.comparing(CallingCode::getPrefix))
                .toList();
        when(callingCodeRepository.getListByPrefix(anyString())).thenReturn(sortedMockCountryList.reversed());

        Optional<CallingCode> result = phoneNumberService.findCountryByPhoneNumber(phoneNumber);

        assertTrue(result.isPresent());
        assertEquals("United Kingdom", result.get().getCountries());
        assertEquals("44", result.get().getPrefix());
    }
}
