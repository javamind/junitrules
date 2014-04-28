package fr.mixit.rules.step2.firstrule;

import com.javamind.rules.config.PersistenceConfig;
import com.javamind.rules.domain.Country;
import com.javamind.rules.repository.CountryRepository;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class CountryTest  {

    @Rule
    public DbUnitRule rule = new DbUnitRule(readDataSet());


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
