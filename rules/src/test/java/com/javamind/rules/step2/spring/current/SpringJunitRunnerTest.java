package com.javamind.rules.step2.spring.current;

import com.javamind.rules.config.PersistenceConfig;
import com.javamind.rules.repository.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Dans le cas courant d'un test avec Spring on uilise le runner {@link org.springframework.test.context.junit4.SpringJUnit4ClassRunner}
 * @author ehret_g
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class SpringJunitRunnerTest {

    @Autowired
    private CountryRepository countryRepository;


    @Test
    public void shouldFindRepositoryInContext() {
        assertThat(countryRepository).isNotNull();
    }

}
