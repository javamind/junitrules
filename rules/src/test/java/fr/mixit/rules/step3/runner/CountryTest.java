package fr.mixit.rules.step3.runner;

import com.javamind.rules.config.PersistenceConfig;
import com.javamind.rules.domain.Country;
import com.javamind.rules.repository.CountryRepository;
import fr.mixit.rules.step2.firstrule.DbUnitRule;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class CountryTest {

    @Rule
    public SpringContextRule springContextRule = new SpringContextRule(this, PersistenceConfig.class);

    @Rule
    public DbUnitRule dbUnitRule = new DbUnitRule(readDataSet());



    @Autowired
    private CountryRepository countryRepository;

    protected IDataSet readDataSet() {
        try {
            return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/country.xml"));
        } catch (MalformedURLException | DataSetException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldFindCountryWhenNameIsKnown() {
        List<Country> countriesStartByFra = countryRepository.findCountryByNamePart("Fra%");
        assertThat(countriesStartByFra).isNotEmpty().hasSize(1);
        assertThat(countriesStartByFra).extracting("code").containsExactly("FRA");
    }


}
