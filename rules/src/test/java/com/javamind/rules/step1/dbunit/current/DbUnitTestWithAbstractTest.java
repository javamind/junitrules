package com.javamind.rules.step1.dbunit.current;

import com.javamind.rules.domain.Country;
import com.javamind.rules.repository.CountryRepository;
import org.assertj.core.api.Assertions;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hibernate.PropertyValueException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Classe de test du repository {@link com.javamind.rules.repository.CountryRepository}
 *
 * @author ehret_g
 */
public class DbUnitTestWithAbstractTest extends AbstractDbunitRepositoryTest {


    /**
     * Repository à tester
     */
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    protected PlatformTransactionManager transactionManager;


    protected IDataSet readDataSet() {
        try {
            return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/country.xml"));
        } catch (MalformedURLException | DataSetException e) {
            throw new RuntimeException(e);
        }
    }

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
        TransactionTemplate tp = new TransactionTemplate(transactionManager);
        tp.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tp.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Country country = new Country("CODE", "Libelle");
                Country persistantCountry = countryRepository.save(country);

                assertThat(persistantCountry.getId()).isNotNull().isEqualTo(country.getId());
            }
        });

        assertTableInDatabaseIsEqualToXmlDataset("COUNTRY", "src/test/resources/datasets/country_created.xml", "code", "name");
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
