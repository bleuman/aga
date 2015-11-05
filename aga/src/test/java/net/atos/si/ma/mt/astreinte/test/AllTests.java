package net.atos.si.ma.mt.astreinte.test;

import java.io.File;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(Suite.class)
@ContextConfiguration("/test-context.xml")
@SuiteClasses({ 
		InterventionServiceImplTest.class })
public class AllTests {
	private static boolean setUpIsDone = false;
	@Autowired
	private DataSource dataSource;

	@Before
	public void setUp() throws Exception {
		if (setUpIsDone) {
			return;
		}

		IDatabaseConnection dbUnitCon = new DatabaseDataSourceConnection(
				dataSource);

		DatabaseOperation.INSERT.execute(dbUnitCon, new FlatXmlDataSetBuilder()
				.build(new File("src/test/resources/dbunit/params.xml")));
		DatabaseOperation.INSERT.execute(dbUnitCon, new FlatXmlDataSetBuilder()
				.build(new File("src/test/resources/dbunit/users.xml")));
		DatabaseOperation.INSERT
				.execute(
						dbUnitCon,
						new FlatXmlDataSetBuilder()
								.build(new File(
										"src/test/resources/dbunit/astreintes-interventions.xml")));

		setUpIsDone = true;
	}
}
