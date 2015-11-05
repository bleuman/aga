package net.atos.si.ma.mt.astreinte.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.model.Intervention;
import net.atos.si.ma.mt.astreinte.model.Jf;
import net.atos.si.ma.mt.astreinte.model.TypeIntervention;
import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.astreinte.service.InterventionService;
import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.service.impl.GenericServiceImpl;
import net.atos.si.ma.mt.config.web.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InterventionServiceImpl extends
		GenericServiceImpl<Intervention, GenericDAO<Intervention, Long>>
		implements InterventionService {

	private static SimpleDateFormat formater = new SimpleDateFormat("ddMMyyyy");

	@Autowired
	@Qualifier("interventionDAO")
	@Override
	public void setDao(GenericDAO<Intervention, Long> dao) {
		super.setDao(dao);
	}

	@Autowired
	@Qualifier("astreinteDAO")
	private GenericDAO<Astreinte, Long> astreinteDAO;

	@Autowired
	@Qualifier("jfDAO")
	private GenericDAO<Jf, Date> jfDAO;

	@Autowired
	@Qualifier("typeInterventionDAO")
	private GenericDAO<TypeIntervention, Long> typeInterventionDAO;

	@Autowired
	private RuleEngine ruleEngine;

	@Override
	public boolean cheakIntervention(Intervention intervention,
			Utilisateur utilisateur, Long idAstreinte)
			throws ValidationException {
		try {

			Astreinte astreinte = astreinteDAO.load(idAstreinte);
			String ressources = astreinte.getRessources();
			String emailr = utilisateur.getEmail();

			if (!ressources.contains(emailr))
				throw new ValidationException(
						"Utilisateur n'est pas affécté à l'astreinte");
			if (intervention.getDateD().after(intervention.getDateF()))
				throw new ValidationException("Date debut after Date fin");
			String dateIntervention = formater.format(intervention.getDateD());
			if (intervention.getDateD().before(astreinte.getDateD())
					|| intervention.getDateF().after(astreinte.getDateF()))
				throw new ValidationException(
						"Dates Début/Fin ne sont pas compatibles avec la période de l'astreinte; la date doit être entre "
								+ astreinte.getDateD()
								+ " et "
								+ astreinte.getDateF());
			if (!dateIntervention.equals(formater.format(intervention
					.getDateF())))
				throw new ValidationException("Date debut != Date fin");

			Jf jf = jfDAO.load(formater.parse(dateIntervention));
			TypeIntervention typeIntervention = intervention
					.getTypeIntervention();
			if (typeIntervention.getId() == null)
				throw new ValidationException("Type Intervention not set");
			if (typeIntervention.getKeyp() == null)
				typeIntervention = typeInterventionDAO.load(typeIntervention
						.getId());

			// jour ferier
			if (jf != null
					&& !typeIntervention.getStringValue().equals(
							TypeIntervention.JOUR_FERIE))
				throw new ValidationException(
						"Type Intervention doit correspondre à un jour férier");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(intervention.getDateD());
			int j = calendar.get(Calendar.DAY_OF_WEEK);
			/*if (j == Calendar.SUNDAY
					&& !typeIntervention.getStringValue().equals(
							TypeIntervention.DIMANCHE))
				throw new ValidationException(
						"Type Intervention doit correspondre à un Dimanche");
			if (j == Calendar.SATURDAY
					&& !typeIntervention.getStringValue().equals(
							TypeIntervention.SAMEDI)
					&& !typeIntervention.getStringValue().equals(
							TypeIntervention.TELEPHONE))
				throw new ValidationException(
						"Type Intervention doit correspondre à un Samedi");*/

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Intervention> checkChevauchement(Integer idRessource,
			Date dateD, Date dateF, Long idAstreinte,Long idIntervention) {
		String hquery = "from Intervention where id<>"+idIntervention+" and  astreinte.id<>"
				+ idAstreinte
				+ " and	utilisateur.id=:ressourceid  and 	( dateD BETWEEN :hdebut and :hfin or  dateF BETWEEN :hdebut and :hfin  or  :hdebut BETWEEN dateD and dateF or  :hfin BETWEEN dateD and dateF ) order by dateD";
		return getDao().listByCriteres(
				hquery,
				new String[] { "ressourceid:integer", "hdebut:date",
						"hfin:date" },
				new Object[] { idRessource, dateD, dateF });
	}

	@Override
	public List<Intervention> getInterventionsForRessourcesByAstreinte(
			Integer idRessource, Long idAstreinte) {
		// TODO Auto-generated method stub
		String hquery = "from Intervention where  astreinte.id=" + idAstreinte
				+ " and	utilisateur.id=" + idRessource;
		return getDao().listByCriteres(hquery, null, null);
	}

	@Override
	public Intervention save(Intervention entity) {
		ruleEngine.getkSession().insert(entity);
		ruleEngine.getkSession().fireAllRules();
		return super.save(entity);
	}

	@Override
	public Intervention update(Intervention entity) {
		ruleEngine.getkSession().insert(entity);
		ruleEngine.getkSession().fireAllRules();
		return super.update(entity);
	}

}
