package org.neo.phonenumresolver.service;

import org.neo.phonenumresolver.model.CallingCode;
import org.neo.phonenumresolver.repository.CallingCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneNumberService {

    @Autowired
    private CallingCodeRepository callingCodeRepository;

    public Optional<CallingCode> findCountryByPhoneNumber(String phoneNumber) {
        String cleanNumber = phoneNumber.replaceAll("[^0-9]+", "").trim();
        String zone = String.valueOf(cleanNumber.charAt(0));
        List<CallingCode> countriesFromTheSameZone = callingCodeRepository.getListByPrefix(zone);
        if (countriesFromTheSameZone.isEmpty()) {
            return Optional.empty();
        }
        return findCountry(cleanNumber, countriesFromTheSameZone);
    }

    private Optional<CallingCode> findCountry(String phoneNumber, List<CallingCode> countries) {
        for (CallingCode country : countries) {
            if (phoneNumber.contains(country.getPrefix())) {
                return Optional.of(country);
            }
        }
        return Optional.empty();
    }





}
