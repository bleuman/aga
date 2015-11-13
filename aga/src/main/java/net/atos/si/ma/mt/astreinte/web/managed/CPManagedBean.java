package net.atos.si.ma.mt.astreinte.web.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.model.EtatAstreinte;
import net.atos.si.ma.mt.astreinte.model.Jf;
import net.atos.si.ma.mt.astreinte.model.Parameter;
import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.astreinte.service.AstreinteService;
import net.atos.si.ma.mt.astreinte.service.ExcelService;
import net.atos.si.ma.mt.astreinte.service.NotificationService;
import net.atos.si.ma.mt.config.dao.GenericDAO;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Component("beanCP")
@Scope("session")
public class CPManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("utilisateurDAO")
	private GenericDAO<Utilisateur, Long> utilisateurDAO;
	@Autowired
	@Qualifier("etatAstreinteDAO")
	private GenericDAO<EtatAstreinte, Long> etatAstreinteDAO;

	@Autowired
	@Qualifier("parameterDAO")
	private GenericDAO<Parameter, Long> parameterDAO;

	private Parameter parameter = new Parameter();

	@Autowired
	@Qualifier("astreinteServiceImpl")
	private AstreinteService astreinteService;

	@Autowired
	@Qualifier("notificationServiceImpl")
	private NotificationService notificationService;

	@Autowired
	@Qualifier("excelServiceImpl")
	private ExcelService excelService;

	@Autowired
	@Qualifier("astreinteDAO")
	private GenericDAO<Astreinte, Long> astreinteDAO;
	@Autowired
	@Qualifier("jfDAO")
	private GenericDAO<Jf, Long> jfDAO;

	private Astreinte astreinte = new Astreinte(true);

	private Jf jf = new Jf();

	private Integer year = 1;

	private Integer month = 1;

	private String refAstrinte;

	public String getRefAstrinte() {
		return refAstrinte;
	}

	public void setRefAstrinte(String refAstrinte) {
		this.refAstrinte = refAstrinte;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Jf getJf() {
		return jf;
	}

	public void setJf(Jf jf) {
		this.jf = jf;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	private List<String> selectedRess = new ArrayList<String>();

	public Astreinte getAstreinte() {
		return astreinte;
	}

	public void setAstreinte(Astreinte astreinte) {
		this.astreinte = astreinte;
	}

	public List<String> getSelectedRess() {
		return selectedRess;
	}

	public void setSelectedRess(List<String> selectedRess) {
		this.selectedRess = selectedRess;
	}

	public String updateAstreinte(Astreinte astreinte) {
		this.astreinte = astreinte;
		String res[] = astreinte.getRessources().split("~");

		selectedRess = new ArrayList<String>();
		for (String string : res) {
			selectedRess.add(string);
		}
		return "astreinte";
	}

	public String validerAsteinte(Long id, int action, String destination) {
		validerAsteinte(id, action);
		return destination;
	}

	public void validerAsteinte(Long id, int action) {
		Astreinte astr = astreinteDAO.load(id);
		astr.setMotif(astreinte.getMotif());
		switch (action) {
		case 1:
			astr.setEtatAstreinte(etatAstreinteDAO
					.load(EtatAstreinte.ID_EN_COURS));
			notificationService.notifyAffectation(astr);
			break;
		case 2:
			astr.setEtatAstreinte(etatAstreinteDAO
					.load(EtatAstreinte.ID_VALIDEE));
			notificationService.notifyValidation(astr);
			break;
		case 3:
			astr.setEtatAstreinte(etatAstreinteDAO
					.load(EtatAstreinte.ID_REJETEE));
			notificationService.notifyRejet(astr);
			break;
		case 4:
			astr.setEtatAstreinte(etatAstreinteDAO
					.load(EtatAstreinte.ID_TERMINEE));

			break;
		case 5:
			astr.setEtatAstreinte(etatAstreinteDAO
					.load(EtatAstreinte.ID_A_VALIDER));
			notificationService.notifyAValidation(astr);
			break;

		}

		astreinteDAO.save(astr);

	}

	public void addJf(Jf ajf) {
		jfDAO.save(ajf);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(ajf.getDateJour());
		month = calendar.get(Calendar.MONTH) + 1;
		year = calendar.get(Calendar.YEAR);
		jf = new Jf();
	}

	public void removeJf(Jf jf) {
		jfDAO.delete(jf);
	}

	public String addAstreinte() throws Exception {

		if (astreinte.getId() == null
				&& astreinteService.findByRef(astreinte.getRef(),
						astreinte.getEntite()) != null) {
			FacesContext.getCurrentInstance().addMessage("astr:ref",
					new FacesMessage("La référence existe déjà."));
			return null;

		} else {

			List<Astreinte> listAst = astreinteService
					.checkChevauchementRessources(selectedRess,
							astreinte.getDateD(), astreinte.getDateF());
			if (!listAst.isEmpty()) {

				FacesContext.getCurrentInstance().addMessage("astr:dated",
						new FacesMessage("Chauvechement sur l'astreinte"));
				return null;
			} else {

				StringBuilder concatenatedList = new StringBuilder();
				for (String s : selectedRess) {
					concatenatedList.append(s).append('~');
				}
				if (concatenatedList.length() > 0)
					concatenatedList
							.deleteCharAt(concatenatedList.length() - 1);
				ELContext elContext = FacesContext.getCurrentInstance()
						.getELContext();
				MngedBean managedBean = (MngedBean) FacesContext
						.getCurrentInstance().getApplication().getELResolver()
						.getValue(elContext, null, "bean");
				astreinte.setEtatAstreinte(etatAstreinteDAO
						.load(EtatAstreinte.ID_EN_COURS));
				astreinte.setCp(managedBean.getUtilisateur());
				String ressources = concatenatedList.toString();
				astreinte.setRessources(ressources);
				astreinte = astreinteDAO.save(astreinte);
				switch (astreinte.getEtatAstreinte().getKeyp()) {
				case EtatAstreinte.EN_COURS:
					notificationService.notifyAffectation(astreinte);
					break;
				case EtatAstreinte.A_VALIDER:
					notificationService.notifyAValidation(astreinte);
					break;
				case EtatAstreinte.VALIDEE:
					notificationService.notifyValidation(astreinte);
					break;
				case EtatAstreinte.REJETEE:
					notificationService.notifyRejet(astreinte);
					break;

				}
				// UtilMail.senMailAffectation(astreinte.getRef(), astreinte
				// .getType(), astreinte.getCp().getEmail(), astreinte
				// .getDateD().toString(),
				// astreinte.getDateF().toString(), astreinte.getCp()
				// .getNom());
				astreinte = new Astreinte(true);
				selectedRess = new ArrayList<String>();
				return "home";
			}
		}
	}

	public void listenerAstreinte(AjaxBehaviorEvent event) {
		if (astreinte.getId() != null)
			astreinte = astreinteDAO.load(astreinte.getId());
		else {
			selectedRess = new ArrayList<String>();
			astreinte = new Astreinte(true);
		}
	}

	public void listenerAstreinteByRef() {
		astreinte = astreinteService.findByRef(refAstrinte, null);

	}

	public String initNewAstreinte(String destination) {
		selectedRess = new ArrayList<String>();
		astreinte = new Astreinte(true);
		return destination;
	}

	public void initUpadteParameter(Parameter param) {
		this.parameter = param;
	}
	public void initNewParameter() {
		this.parameter = new Parameter();
	}

	public void updateParameter(Parameter param) {
		parameterDAO.save(param);
		parameter = new Parameter();
	}

	public void valider() {
		astreinte.setEtatAstreinte(etatAstreinteDAO
				.load(EtatAstreinte.ID_VALIDEE));
		astreinteDAO.save(astreinte);
	}

	public void rejeter() {
		astreinte.setEtatAstreinte(etatAstreinteDAO
				.load(EtatAstreinte.ID_REJETEE));
		astreinteDAO.save(astreinte);
	}

	public StreamedContent excel() throws Exception {

		return excelService.generateExcelAstrinte(astreinte);

	}

}
