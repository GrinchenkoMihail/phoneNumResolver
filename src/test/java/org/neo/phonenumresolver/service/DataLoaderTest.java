package org.neo.phonenumresolver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neo.phonenumresolver.model.CallingCode;
import org.neo.phonenumresolver.repository.CallingCodeRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
public class DataLoaderTest {

    @Mock
    private WikipediaDataFetcher wikipediaDataFetcher;

    @Mock
    private CallingCodeRepository callingCodeRepository;

    @InjectMocks
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализируем Mockito
    }

    @Test
    void loadDataOnStartupTest() throws IOException {
        List<CallingCode> mockCallingCodes = Arrays.asList(
                new CallingCode("Country A", "1"),
                new CallingCode("Country B", "2")
        );
        when(wikipediaDataFetcher.fetchCountryData()).thenReturn(mockCallingCodes);

        dataLoader.loadDataOnStartup();

        verify(callingCodeRepository, times(1)).clearTable();

        verify(callingCodeRepository, times(1)).save(new CallingCode("Country A", "1"));
        verify(callingCodeRepository, times(1)).save(new CallingCode("Country B", "2"));
    }
}
