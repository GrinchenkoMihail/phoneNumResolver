package org.neo.phonenumresolver.repository;

import org.neo.phonenumresolver.model.CallingCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CallingCodeRepository extends JpaRepository<CallingCode, Long> {

    @Modifying
    @Query(value = "TRUNCATE TABLE country_calling_codes", nativeQuery = true)
    void clearTable();

    @Query(value = "SELECT * FROM country_calling_codes WHERE prefix LIKE CONCAT(:prefix, '%') ORDER BY prefix DESC", nativeQuery = true)
    List<CallingCode> getListByPrefix(String prefix);
}
