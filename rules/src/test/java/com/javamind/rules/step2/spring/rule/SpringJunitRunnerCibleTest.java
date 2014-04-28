package com.javamind.rules.step2.spring.rule;

import com.javamind.rules.config.PersistenceConfig;
import com.javamind.rules.repository.CountryRepository;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Le runner est remplacé par une rule
 * @author ehret_g
 */
public class SpringJunitRunnerCibleTest {

    @Rule
    public SpringContextRule springContextRule = new SpringContextRule(this, PersistenceConfig.class);

    @Autowired
    private CountryRepository countryRepository;


    @Test
    public void shouldFindRepositoryInContext() {
        assertThat(countryRepository).isNotNull();
    }

}
