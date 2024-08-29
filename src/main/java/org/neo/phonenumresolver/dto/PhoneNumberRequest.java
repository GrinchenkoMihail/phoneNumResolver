package org.neo.phonenumresolver.dto;

public class PhoneNumberRequest {
    private String phoneNumber;

    public PhoneNumberRequest() {}

    public PhoneNumberRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
