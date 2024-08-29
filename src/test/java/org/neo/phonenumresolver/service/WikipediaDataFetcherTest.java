package org.neo.phonenumresolver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo.phonenumresolver.model.CallingCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WikipediaDataFetcherTest {

    @Autowired
    private WikipediaDataFetcher wikipediaDataFetcher;

    @BeforeEach
    void setUp() {
        wikipediaDataFetcher = new WikipediaDataFetcher();
    }

    @Test
    void fetchCountryDataTest() throws IOException {
        List<CallingCode> callingCodes = wikipediaDataFetcher.fetchCountryData();
        assertFalse(callingCodes.isEmpty());
        assertNotNull(callingCodes.get(0).getCountries());
        assertNotNull(callingCodes.get(0).getPrefix());
    }

    @Test
    void updateListTest() throws Exception {
        List<CallingCode> callingCodes = new ArrayList<>();
        callingCodes.add(new CallingCode("Country A", "1"));
        callingCodes.add(new CallingCode("Country B", "1"));
        callingCodes.add(new CallingCode("Country C", "2"));

        Method updateListMethod = WikipediaDataFetcher.class.getDeclaredMethod("updateList", List.class);
        updateListMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<CallingCode> result = (List<CallingCode>) updateListMethod.invoke(wikipediaDataFetcher, callingCodes);

        assertEquals(2, result.size());

        CallingCode result1 = result.stream()
                .filter(code -> code.getPrefix().equals("1"))
                .findFirst()
                .orElse(null);

        CallingCode result2 = result.stream()
                .filter(code -> code.getPrefix().equals("2"))
                .findFirst()
                .orElse(null);

        assertNotNull(result1);
        assertEquals("Country A, Country B", result1.getCountries());

        assertNotNull(result2);
        assertEquals("Country C", result2.getCountries());
    }

}