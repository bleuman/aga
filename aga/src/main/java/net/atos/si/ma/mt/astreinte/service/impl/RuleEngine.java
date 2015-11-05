package net.atos.si.ma.mt.astreinte.service.impl;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class RuleEngine {
	public RuleEngine() {
		super();
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		this.kSession = kContainer.newKieSession("ksession-rules");
	}

	private KieSession kSession;

	public KieSession getkSession() {
		return kSession;
	}

}
