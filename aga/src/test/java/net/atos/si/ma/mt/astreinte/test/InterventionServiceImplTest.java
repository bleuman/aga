package net.atos.si.ma.mt.astreinte.test;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;
import net.atos.si.ma.mt.astreinte.model.EtatAstreinte;
import net.atos.si.ma.mt.astreinte.model.Intervention;
import net.atos.si.ma.mt.astreinte.model.TypeIntervention;
import net.atos.si.ma.mt.astreinte.service.AstreinteService;
import net.atos.si.ma.mt.astreinte.service.InterventionService;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class InterventionServiceImplTest extends TestCase {
	
	@Autowired
	private InterventionService interventionService;
	@Autowired
	private AstreinteService astreinteService;

	
	@Test
	public void testSaveIntervention() {
		Intervention intervention = new Intervention();
		intervention.setDateD(new Date());
		intervention.setDateF(new Date());
		intervention.setTypeIntervention(new TypeIntervention());
		intervention.getTypeIntervention().setId(21l);
		intervention.setAstreinte(astreinteService.load(1l));
		intervention.setEtatAstreinte(new EtatAstreinte());
		intervention.getEtatAstreinte().setId(12l);
		interventionService.save(intervention);
	}

	@Test
	public void testListAll() {
		List<Intervention> interventions = interventionService.listAll();
		assertNotNull(interventions);
		assertTrue(interventions.size() > 0);

	}

	public void testListByQuery() {
		List<Object> interventions = interventionService
				.listByQuery("from Intervention");

		assertNotNull(interventions);
		assertTrue(interventions.size() > 0);

	}

}
