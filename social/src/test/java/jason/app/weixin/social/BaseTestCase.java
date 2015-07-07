package jason.app.weixin.social;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.DatabaseTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:META-INF/test-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	org.springframework.test.context.transaction.TransactionalTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class })
public abstract class BaseTestCase extends DatabaseTestCase {

    /**
     * This is the underlying BasicDataSource used by Dao. If The Dao is using a
     * support class from Spring (i.e. HibernateDaoSupport) this is the
     * BasicDataSource that is used by Spring.
     */
    @Autowired
    private DataSource dataSource;

    /**
     * DBUnit specific object to provide configuration to to properly state the
     * underlying database
     */
    private IDatabaseTester databaseTester;

    public IDatabaseTester getDatabaseTester() {
		return databaseTester;
	}

	public void setDatabaseTester(IDatabaseTester databaseTester) {
		this.databaseTester = databaseTester;
	}

	/**
     * Prepare the test instance by handling the Spring annotations and updating
     * the database to the stale state.
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        databaseTester = new DataSourceDatabaseTester(dataSource);
        databaseTester.setDataSet(this.getDataSet());
        databaseTester.setSetUpOperation(this.getSetUpOperation());
        databaseTester.onSetup();
    }

    /**
     * Perform any required database clean up after the test runs to ensure the
     * stale state has not been dirtied for the next test.
     * 
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        databaseTester.setTearDownOperation(this.getTearDownOperation());
        databaseTester.onTearDown();
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        // TODO Auto-generated method stub
        return databaseTester.getConnection();
    }
    /**
     * Overridden to disable the closing of the connection for every test.
     */
    @Override
    protected void closeConnection(IDatabaseConnection conn) {
        // Empty body on purpose.
    }

}
