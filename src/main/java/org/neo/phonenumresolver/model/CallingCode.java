package org.neo.phonenumresolver.model;

import jakarta.persistence.*;


@Entity
@Table(name = "country_calling_codes")
public class CallingCode {

    @Id
    private String prefix;
    private String countries;

    public CallingCode() {
    }

    public CallingCode(String name, String prefix) {
        this.countries = name;
        this.prefix = prefix;
    }

    public CallingCode(String prefix) {
        this.prefix = prefix;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String name) {
        this.countries = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


}
