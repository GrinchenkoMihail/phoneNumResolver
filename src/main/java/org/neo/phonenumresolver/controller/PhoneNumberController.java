package org.neo.phonenumresolver.controller;


import org.neo.phonenumresolver.model.CallingCode;
import org.neo.phonenumresolver.dto.PhoneNumberRequest;
import org.neo.phonenumresolver.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @PostMapping("/resolve")
    public ResponseEntity<?> resolvePhoneNumber(@RequestBody PhoneNumberRequest request) {
        String phoneNumber = request.getPhoneNumber();
        Optional<CallingCode> callingCode = phoneNumberService.findCountryByPhoneNumber(phoneNumber);

        if (callingCode.isPresent()) {
            return ResponseEntity.ok(callingCode.get());
        } else {
            return ResponseEntity.status(404).body("Country not found for the given phone number");
        }
    }
}
