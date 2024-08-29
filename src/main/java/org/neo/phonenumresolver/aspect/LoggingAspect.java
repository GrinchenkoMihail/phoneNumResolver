package org.neo.phonenumresolver.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.neo.phonenumresolver.model.CallingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Before(value = "execution(public java.util.List org.neo.phonenumresolver.service.WikipediaDataFetcher.fetchCountryData(..))")
    public void logBeforeFetching(JoinPoint joinPoint) {
        LOG.info("Unloading a table from Wikipedia ");
    }

    @AfterReturning(value = "execution(public java.util.List org.neo.phonenumresolver.service.WikipediaDataFetcher.fetchCountryData(..))", returning = "callingCodes")
    public void logAfterFetching(JoinPoint joinPoint, List<CallingCode> callingCodes) {
        LOG.info("Unloading a table from Wikipedia complete. Number of calling Codes: {}", callingCodes.size());
    }

    @After("execution(public * org.neo.phonenumresolver.service.DataLoader.loadDataOnStartup())")
    public void logAfterDataLoading(JoinPoint joinPoint) {
        LOG.info("Countries data loaded successfully in DB");
    }

    @AfterReturning(value = "execution(public java.util.Optional<org.neo.phonenumresolver.model.CallingCode> org.neo.phonenumresolver.service.PhoneNumberService.findCountryByPhoneNumber(..))", returning = "result")
    public void logAfterResolvePhoneNumber(JoinPoint joinPoint, Optional<CallingCode> result) {
        if (result.isPresent()) {
            LOG.info("Countries: {}", result.get());
        } else {
            LOG.warn("Country not found for the given phone number");
        }
    }
}
