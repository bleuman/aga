package net.atos.si.ma.mt.astreinte.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.astreinte.service.ExchangeAuthenticatorService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")

public class ExchangeAuthenticatorServiceTest {

	@Autowired
	private ExchangeAuthenticatorService exchangeAuthenticatorService;

	@Test
	public void testIsUserAuthenthicated() {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setLogin("a134368");
		utilisateur.setPassword("Tilyass$2009");
		boolean ret = exchangeAuthenticatorService
				.isUserAuthenthicated(utilisateur);
		assertTrue(ret);
		utilisateur.setPassword("dummy");
		ret = exchangeAuthenticatorService.isUserAuthenthicated(utilisateur);
		assertFalse(ret);
	}

}
