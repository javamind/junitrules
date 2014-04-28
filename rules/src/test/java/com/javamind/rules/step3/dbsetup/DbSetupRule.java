package com.javamind.rules.step3.dbsetup;

import com.javamind.rules.step2.spring.rule.SpringContextRule;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.rules.ExternalResource;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

/**
 * {@link }
 *
 * @author EHRET_G
 */
public class DbSetupRule extends ExternalResource {
    protected Operation init;
    protected ApplicationContext applicationContext;

    public DbSetupRule(ApplicationContext applicationContext, Operation init) {
        this.applicationContext = applicationContext;
        this.init = init;
    }


    @Override
    protected void before() throws Throwable {
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(applicationContext.getBean(DataSource.class)), init);
        dbSetup.launch();
    }

}
