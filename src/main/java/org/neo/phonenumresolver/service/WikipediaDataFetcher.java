package org.neo.phonenumresolver.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.neo.phonenumresolver.model.CallingCode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WikipediaDataFetcher {

    private static final String WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/List_of_country_calling_codes#Alphabetical_order";

    public List<CallingCode> fetchCountryData() throws IOException {

        List<CallingCode> callingCodes = new ArrayList<>();
        Document doc = Jsoup.connect(WIKIPEDIA_URL).get();

        Element table = doc.select("table.wikitable").first();
        Elements rows = table.select("tr");

        for (Element row : rows.subList(1, rows.size())) {
            Elements columns = row.select("td");
            if (columns.size() >= 2) {
                String countryName = columns.get(0).text();
                String countryCode = columns.get(1).text().replaceAll("[^0-9 ]+", "").trim();
                String[] arr = countryCode
                        .split("\\s+");
                String code = arr[0];
                if (arr.length > 1) {
                    for (int i = 1; i < arr.length; i++) {
                        String prefix = code + arr[i];
                        CallingCode callingCode = new CallingCode(countryName, prefix);
                        callingCodes.add(callingCode);
                    }
                } else {
                    CallingCode callingCode = new CallingCode(countryName, code);
                    callingCodes.add(callingCode);
                }
            }
        }
        return updateList(callingCodes);
    }

    private List<CallingCode> updateList(List<CallingCode> callingCodes) {
        Map<String, String> helperMap = new HashMap<>();
        callingCodes.forEach(callingCode -> {
            String prefix = callingCode.getPrefix();
            String country = callingCode.getCountries();

            helperMap.merge(prefix, country, (existingCountry, newCountry) -> existingCountry + ", " + newCountry);
        });
        return helperMap.entrySet()
                .stream()
                .map(entry -> new CallingCode(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
    }

}

