package com.javamind.rules.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.javamind.rules.domain.Country;
import java.util.List;

/**
 * Repository associé au {@link com.javamind.rules.domain.Country}
 *
 * @author ehret_g
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query(value = "SELECT c FROM Country c WHERE c.code = :code")
    Country findCountryByCode(@Param("code") String code);

    @Query(value = "SELECT c FROM Country c WHERE c.name like :name")
    List<Country> findCountryByNamePart(@Param("name") String name);

}
