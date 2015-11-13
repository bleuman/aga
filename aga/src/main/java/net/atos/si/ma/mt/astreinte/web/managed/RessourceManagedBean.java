package net.atos.si.ma.mt.astreinte.web.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.model.EtatAstreinte;
import net.atos.si.ma.mt.astreinte.model.Intervention;
import net.atos.si.ma.mt.astreinte.model.TypeIntervention;
import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.astreinte.service.AstreinteService;
import net.atos.si.ma.mt.astreinte.service.ExcelService;
import net.atos.si.ma.mt.astreinte.service.InterventionService;
import net.atos.si.ma.mt.astreinte.service.NotificationService;
import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.web.ValidationException;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Component("beanR")
@Scope("session")
public class RessourceManagedBean

implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Autowired
	// @Qualifier("interventionDAO")
	// private GenericDAO<Intervention, Long> interventionDAO;

	private Long IdAstreinte = 0L;
	private Intervention intervention = initIntervention();

	private List<Intervention> interventions;

	@Autowired
	@Qualifier("astreinteDAO")
	private GenericDAO<Astreinte, Long> astreinteDAO;

	@Autowired
	@Qualifier("notificationServiceImpl")
	private NotificationService notificationService;

	@Autowired
	@Qualifier("excelServiceImpl")
	private ExcelService excelService;

	@Autowired
	@Qualifier("typeInterventionDAO")
	private GenericDAO<TypeIntervention, Long> typeInterventionDAO;

	@Autowired
	@Qualifier("etatAstreinteDAO")
	private GenericDAO<EtatAstreinte, Long> etatAstreinteDAO;

	@Autowired
	@Qualifier("astreinteServiceImpl")
	private AstreinteService astreinteService;
	@Autowired
	@Qualifier("interventionServiceImpl")
	private InterventionService interventionService;

	private Intervention initIntervention() {
		interventions = null;
		Intervention interv = new Intervention();
		interv.setTypeIntervention(new TypeIntervention());
		interv.setEtatAstreinte(new EtatAstreinte());
		interv.getEtatAstreinte().setId(EtatAstreinte.ID_EN_COURS);
		interv.getEtatAstreinte().setKeyp(EtatAstreinte.EN_COURS);
		return interv;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public Long getIdAstreinte() {
		return IdAstreinte;
	}

	public void setIdAstreinte(Long idAstreinte) {
		this.IdAstreinte = idAstreinte;
	}

	public List<Intervention> getInterventions() {
		return interventions;
	}

	public void setInterventions(List<Intervention> interventions) {
		this.interventions = interventions;
	}

	public String editIntervention(Intervention intervention) {
		this.intervention = intervention;
		return "intervention";
	}

	public void removeIntervention(Intervention intervention) {
		Astreinte astreinteToRemoveFrom = intervention.getAstreinte();
		astreinteToRemoveFrom.getInterventions().remove(intervention);
		astreinteDAO.save(astreinteToRemoveFrom);
		interventionService.delete(intervention);
	}

	public String initAddIntervention() {
		this.intervention = initIntervention();
		return "intervention";
	}

	public String updateEtatIntervention(Intervention interToProc, Long action) {
		interToProc.setEtatAstreinte(etatAstreinteDAO.load(action));
		interventionService.save(interToProc);

		switch (action.intValue()) {
		case (int) EtatAstreinte.ID_REJETEE:
			notificationService.notifyRjetIntervention(interToProc);
			break;
		case (int) EtatAstreinte.ID_VALIDEE:
			notificationService.notifyValidationIntervention(interToProc);
			break;
		case (int) EtatAstreinte.ID_A_VALIDER:
			List<Intervention> resInterventions = new ArrayList<Intervention>();
			boolean allAValider = true;
			ELContext elContext = FacesContext.getCurrentInstance()
					.getELContext();
			MngedBean managedBean = (MngedBean) FacesContext
					.getCurrentInstance().getApplication().getELResolver()
					.getValue(elContext, null, "bean");
			Utilisateur utilisateur = managedBean.getUtilisateur();
			for (Intervention intervN : interToProc.getAstreinte()
					.getInterventions()) {

				if (intervN.getUtilisateur().getId() == utilisateur.getId()) {
					if (intervN.getEtatAstreinte().getId() == EtatAstreinte.ID_A_VALIDER) {
						resInterventions.add(intervN);
					} else {
						allAValider = false;
						break;
					}
				}
			}
			if (allAValider)
				notificationService.notifyAValidationIntervention(
						interToProc.getAstreinte(), resInterventions);
			break;

		}
		return null;
	}

	public String addinterv() throws Exception {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		MngedBean managedBean = (MngedBean) FacesContext.getCurrentInstance()
				.getApplication().getELResolver()
				.getValue(elContext, null, "bean");
		if (intervention.getAstreinte() != null
				&& intervention.getAstreinte().getId() != null)
			IdAstreinte = intervention.getAstreinte().getId();
		if (!interventionService.cheakIntervention(intervention,
				managedBean.getUtilisateur(), IdAstreinte))
			throw new ValidationException("Parametres non valides");

		Astreinte astreinte = astreinteDAO.load(IdAstreinte);
		Utilisateur utilisateur = managedBean.getUtilisateur();
		if (utilisateur == null)
			throw new ValidationException("Vous n'êtes pas connectés");
		interventions = interventionService.checkChevauchement(
				utilisateur.getId(), astreinte.getDateD(),
				astreinte.getDateD(), IdAstreinte, intervention.getId());
		if (interventions != null && !interventions.isEmpty())
			throw new ValidationException("Chevechement de l'intervetion");

		if (intervention.getId() == null) {
			Utilisateur utilisateur2 = new Utilisateur();
			utilisateur2.setId(utilisateur.getId());
			intervention.setUtilisateur(utilisateur);
			intervention.setAstreinte(astreinte);
			// interventionDAO.save(intervention);
			astreinte.getInterventions().add(intervention);
			astreinte = astreinteDAO.save(astreinte);
		} else {
			intervention = interventionService.save(intervention);
		}

		intervention = initIntervention();
		return "interventions";

	}

	public List<Object> loadInterventionAdmin() {
		if (astreinte.getId() != null)
			return interventionService
					.listByQuery("select  i from Intervention  as i where  i.astreinte.id="
							+ astreinte.getId() + "  order by i.id");
		else
			return interventionService
					.listByQuery("select  i from Intervention as i order by i.id");

	}

	public List<Object> loadIntervention(Long idUser) {

		if (astreinte.getId() != null) {

			if (idUser != null && !idUser.equals("null"))
				return interventionService
						.listByQuery("select  i from Intervention as i where  i.astreinte.id="
								+ astreinte.getId()
								+ " and i.utilisateur.id ="
								+ idUser + " order by i.id");
			else
				return interventionService
						.listByQuery("select  i from Intervention  as i where  i.astreinte.id="
								+ astreinte.getId() + "  order by i.id");

		}

		return null;
	}

	public List<Object> loadInterventionSTT(Integer idUser) {

		if (astreinte.getId() != null) {

			if (idUser != null && !idUser.equals("null")) {

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("user", idUser);
				params.put("astreinte", astreinte.getId());
				return interventionService.executeNamedQuery(
						"sttintervention-by-user-astreinte", params);
			}

		}
		return null;
 
	}

	public void listenerIntervention(AjaxBehaviorEvent event) {
		if (intervention.getTypeIntervention() != null
				&& intervention.getTypeIntervention().getId() != null)
			intervention.setTypeIntervention(typeInterventionDAO
					.load(intervention.getTypeIntervention().getId()));

	}

	public StreamedContent excelRessource(Long idAstreinte, Integer idRessource)
			throws Exception {

		if (idAstreinte != null)
			return excelService.generateExcelForRessource(interventionService
					.getInterventionsForRessourcesByAstreinte(idRessource,
							idAstreinte));
		else
			return null;
	}

	public StreamedContent excelRessourceSTT(Long idAstreinte,
			Integer idRessource) throws Exception {

		if (idAstreinte != null) {
			return excelService.generateExcelForRessourceSTT(idAstreinte,
					idRessource);
		} else
			return null;
	}

	private Astreinte astreinte = new Astreinte();

	public Astreinte getAstreinte() {
		return astreinte;
	}

	public void setAstreinte(Astreinte astreinte) {
		this.astreinte = astreinte;
	}

}
