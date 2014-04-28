package com.javamind.rules.step3.dbsetup;

import com.javamind.rules.repository.CountryRepository;
import com.javamind.rules.step2.spring.rule.SpringContextRule;
import com.javamind.rules.config.PersistenceConfig;
import com.javamind.rules.domain.Country;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import org.assertj.core.api.Assertions;
import org.hibernate.PropertyValueException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Classe de test du repository {@link com.javamind.rules.repository.CountryRepository}
 *
 * @author ehret_g
 */
public class CountryRepositoryDbSetupTest {

    private static Operation init = Operations.sequenceOf(
            deleteAllFrom("country"),
            insertInto("country")
                    .columns("id", "code", "name")
                    .values(1, "FRA", "France")
                    .values(2, "USA", "United States")
                    .build()
    );

    public SpringContextRule springContextRule = new SpringContextRule(this, PersistenceConfig.class);

    @Rule
    public TestRule testRule = RuleChain.outerRule(springContextRule).around(new DbSetupRule(springContextRule.getContext(), init));

    @Autowired
    private CountryRepository countryRepository;





    /**
     * Test de la classe {@link com.javamind.rules.repository.CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void shouldNotFindCountryWhenCodeIsNull() {
        Country persistantCountry = countryRepository.findCountryByCode(null);
        assertThat(persistantCountry).isNull();
    }

    /**
     * Test de la classe {@link com.javamind.rules.repository.CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void shouldFindCountryWhenCodeIsKnown() {
        Country persistantCountry = countryRepository.findCountryByCode("FRA");
        assertThat(persistantCountry.getName()).isEqualTo("France");
    }

    /**
     * Test de la classe {@link com.javamind.rules.repository.CountryRepository#findCountryByCode(String)}
     */
    @Test
    public void shouldNotFindCountryWhenCodeIsUnknown() {
        Country persistantCountry = countryRepository.findCountryByCode("ZZZ");
        assertThat(persistantCountry).isNull();
    }


    /**
     * Test de la classe {@link com.javamind.rules.repository.CountryRepository#findCountryByNamePart(String)}
     */
    @Test
    public void shouldFindCountryWhenNameIsKnown() {
        List<Country> countriesStartByFra = countryRepository.findCountryByNamePart("Fra%");
        assertThat(countriesStartByFra).isNotEmpty().hasSize(1);
        assertThat(countriesStartByFra).extracting("code").containsExactly("FRA");
    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité
     */
    @Test
    public void shouldCreateCountry() {
        Country country = new Country("CODE", "Libelle");
        Country persistantCountry = countryRepository.save(country);

        assertThat(persistantCountry.getId()).isNotNull().isEqualTo(country.getId());
    }

    /**
     * Test permettant de vérifier la création d'une nouvelle entité sans avoir renseigné un champ obligatoire comme
     * le code
     */
    @Test
    public void shouldNotCreateCountryWhenRequiredFieldIsEmpty() {
        Country country = new Country();
        country.setName("Libelle");
        try {
            countryRepository.save(country);
            failBecauseExceptionWasNotThrown(IndexOutOfBoundsException.class);
        } catch (JpaSystemException e) {
            Assertions.assertThat(e.getCause().getCause()).isInstanceOf(PropertyValueException.class);
        }
    }

}
