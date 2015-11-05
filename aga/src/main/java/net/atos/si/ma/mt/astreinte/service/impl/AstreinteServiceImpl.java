package net.atos.si.ma.mt.astreinte.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.service.AstreinteService;
import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.service.impl.GenericServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AstreinteServiceImpl extends
		GenericServiceImpl<Astreinte, GenericDAO<Astreinte, Long>> implements
		AstreinteService {

	@Autowired
	@Qualifier("astreinteDAO")
	@Override
	public void setDao(GenericDAO<Astreinte, Long> dao) {
		super.setDao(dao);
	}

	@Override
	public Astreinte findByRef(String ref, String domaine) {
		return getDao().oneByCriteres("from Astreinte a where a.ref=:ref ",
				new String[] { "ref:string" }, new Object[] { ref });
	}

	@Override
	public List<Astreinte> checkChevauchementRessources(
			List<String> listRessources, Date debut, Date fin) {

		List<Astreinte> astreintes = new ArrayList<Astreinte>();

		for (String res : listRessources) {
			Astreinte astreinte = getDao()
					.oneByCriteres(
							"from Astreinte a where a.ref like '%'||:res||'%' and ( a.dateF < :debut or :fin < a.dateD)",
							new String[] { "res:string", "debut:date",
									"fin:date" },
							new Object[] { res, debut, fin });
			if (astreinte != null)
				astreintes.add(astreinte);
		}
		// TODO Auto-generated method stub
		return astreintes;
	}

}
