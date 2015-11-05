package net.atos.si.ma.mt.astreinte.service.impl;

import net.atos.si.ma.mt.astreinte.model.EtatAstreinte;
import net.atos.si.ma.mt.astreinte.service.EtatAstreinteService;
import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.service.impl.GenericServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EtatAstreinteServiceImpl extends
		GenericServiceImpl<EtatAstreinte, GenericDAO<EtatAstreinte, Long>>
		implements EtatAstreinteService {
	@Autowired
	@Qualifier("etatAstreinteDAO")
	@Override
	public void setDao(GenericDAO<EtatAstreinte, Long> dao) {
		super.setDao(dao);
	}
}
