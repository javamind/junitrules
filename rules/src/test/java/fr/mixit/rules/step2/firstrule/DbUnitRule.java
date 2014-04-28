package fr.mixit.rules.step2.firstrule;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.rules.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * {@link }
 *
 * @author EHRET_G
 */
public class DbUnitRule extends ExternalResource {

    private static Properties properties = new Properties();
    protected IDatabaseTester databaseTester;
    protected static String databaseJdbcDriver , databaseUrl, databaseUsername, databasePassword;
    protected IDataSet dataSet;


    public DbUnitRule(IDataSet dataSet) {
        this.dataSet = dataSet;
    }


    @Override
    protected void before() throws Throwable {
        initProperties();
        databaseTester = new JdbcDatabaseTester(databaseJdbcDriver, databaseUrl, databaseUsername, databasePassword);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }


    private void initProperties() throws IOException {
        if (databaseJdbcDriver == null) {
            properties.load(getClass().getResourceAsStream("/application.properties"));
            databaseJdbcDriver = properties.getProperty("db.driver");
            databaseUrl = properties.getProperty("db.url");
            databaseUsername = properties.getProperty("db.username");
            databasePassword = properties.getProperty("db.password");
        }
    }


}
