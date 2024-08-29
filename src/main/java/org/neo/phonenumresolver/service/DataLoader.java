package org.neo.phonenumresolver.service;


import jakarta.transaction.Transactional;
import org.neo.phonenumresolver.model.CallingCode;
import org.neo.phonenumresolver.repository.CallingCodeRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DataLoader {

    private final WikipediaDataFetcher wikipediaDataFetcher;
    private final CallingCodeRepository callingCodeRepository;

    public DataLoader(WikipediaDataFetcher wikipediaDataFetcher, CallingCodeRepository callingCodeRepository) {
        this.wikipediaDataFetcher = wikipediaDataFetcher;
        this.callingCodeRepository = callingCodeRepository;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void loadDataOnStartup() {
        try {
            List<CallingCode> callingCodes = wikipediaDataFetcher.fetchCountryData();
            callingCodeRepository.clearTable();
            callingCodes.forEach(callingCodeRepository::save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
