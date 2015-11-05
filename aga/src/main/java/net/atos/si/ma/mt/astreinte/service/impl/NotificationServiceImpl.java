package net.atos.si.ma.mt.astreinte.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.model.Intervention;
import net.atos.si.ma.mt.astreinte.model.Notification;
import net.atos.si.ma.mt.astreinte.service.NotificationService;
import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.service.impl.GenericServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends
		GenericServiceImpl<Notification, GenericDAO<Notification, Long>>
		implements NotificationService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	private static SimpleDateFormat FORMATER = new SimpleDateFormat(
			"EEE dd/MM/yyyy hh:mm");

	@Value("${enableEmail}")
	private Boolean enableEmail;

	@Value("${seuil}")
	private Integer seuil;

	@Override
	public Integer getSeuil() {
		return seuil;
	}

	@Value("${astreinteTemplate}")
	private String astreinteTemplate;

	@Autowired
	private ExchangeService exchangeService;

	@Autowired
	@Qualifier("notificationDAO")
	@Override
	public void setDao(GenericDAO<Notification, Long> dao) {
		super.setDao(dao);
	}

	@Autowired
	private RuleEngine ruleEngine;

	@Override
	public void manageNotifications() {
		log.trace("Managing Notification in Background");

		List<Notification> notifications = listByCriteres(
				"from Notification where occurence >-1 ", null, null);
		log.trace("Found " + notifications.size() + " Notification");

		for (Notification notification : notifications) {

			ruleEngine.getkSession().insert(notification);

		}
		ruleEngine.getkSession().fireAllRules();
	}

	@Override
	public void notifyAffectation(Astreinte astreinte) {
		Notification notification = new Notification();
		notification.setAstreinte(astreinte);
		notification.setEtatAstreinte(astreinte.getEtatAstreinte());

		notification.setDestinataires(astreinte.getRessources().replace("~",
				";"));
		notification
				.setSubject("Gestion Astriente [Affectation] Astreinte Référece : "
						+ astreinte.getRef());
		notification.setContent("Bonjour<br/>"
				+ "Vouas avez été affecté à l'astreinte :<br/> "+getAstreinteInfoAsHTML(astreinte)
				);
		getDao().save(notification);
		try {
			sendMail(notification);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void notifyAValidation(Astreinte astreinte) {
		Notification notification = new Notification();
		notification.setAstreinte(astreinte);
		notification.setEtatAstreinte(astreinte.getEtatAstreinte());
		notification.setDestinataires(astreinte.getCp().getEmail());
		notification.setSubject("Gestion Astrient [A valider]: Ref: "
				+ astreinte.getRef());
		notification.setContent("Bonjour<br/>" + "L'astreinte référence : "
				+ astreinte.getRef() + " est soumise pour validation <br />"+getAstreinteInfoAsHTML(astreinte));
		getDao().save(notification);
		try {
			sendMail(notification);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void notifyRejet(Astreinte astreinte) {
		Notification notification = new Notification();
		notification.setAstreinte(astreinte);
		notification.setEtatAstreinte(astreinte.getEtatAstreinte());
		notification.setDestinataires(astreinte.getCp().getEmail());
		notification.setSubject("Gestion Astrient [Rejet]: Ref: "
				+ astreinte.getRef());
		notification.setContent("Bonjour<br/>" + "L'astreinte référence : "
				+ astreinte.getRef() + " est rejeté <br />Motif: "
				+ astreinte.getMotif()+getAstreinteInfoAsHTML(astreinte));
		getDao().save(notification);
		try {
			sendMail(notification);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void notifyValidation(Astreinte astreinte) {
		Notification notification = new Notification();
		notification.setAstreinte(astreinte);
		notification.setEtatAstreinte(astreinte.getEtatAstreinte());
		notification.setDestinataires(astreinte.getCp().getEmail());
		notification.setSubject("Gestion Astriente: [Validée]: Ref: "
				+ astreinte.getRef());
		notification.setContent("Bonjour<br/>" + "L'astreinte référence : "
				+ astreinte.getRef() + " est validée<br />"+getAstreinteInfoAsHTML(astreinte));
		getDao().save(notification);
		try {
			sendMail(notification);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void notifyAValidationIntervention(Astreinte astreinte,
			List<Intervention> interventions) {
		String interv = "";
		String dest = null;
		for (Intervention intervention : interventions) {
			if (dest == null)
				dest = intervention.getUtilisateur().getEmail();
			interv += intervention.getTypeIntervention().getKeyp() + " : de "
					+ FORMATER.format(intervention.getDateD()) + " à "
					+ FORMATER.format(intervention.getDateF()) + "<br/>";
		}
		Notification notification = new Notification();
		notification.setAstreinte(astreinte);
		notification.setEtatAstreinte(astreinte.getEtatAstreinte());
		notification.setDestinataires(dest);
		notification
				.setSubject("Gestion Astriente: Interventions [A valider]: Ref: "
						+ astreinte.getRef());

		notification.setContent("Bonjour<br/>" + "Les intervention  : "
				+ interv + astreinte.getRef()
				+ " sont soumises pour validation");
		getDao().save(notification);
		try {
			sendMail(notification);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void notifyValidationIntervention(Intervention intervention) {
		Notification notification = new Notification();
		notification.setOccurence(-1);
		notification.setIntervention(intervention);
		notification.setEtatAstreinte(intervention.getEtatAstreinte());
		notification.setDestinataires(intervention.getUtilisateur().getEmail());
		notification.setSubject("Gestion Astriente: Intervention ["
				+ intervention.getEtatAstreinte().getKeyp() + "]: Ref: "
				+ intervention.getAstreinte().getRef());

		notification.setContent("Bonjour<br/>" + "L'intervention  : "
				+ intervention.getTypeIntervention().getKeyp() + " : de "
				+ FORMATER.format(intervention.getDateD()) + " à "
				+ FORMATER.format(intervention.getDateF())
				+ "<br/> sur l'astreinte "
				+ intervention.getAstreinte().getRef() + " : "
				+ intervention.getEtatAstreinte().getKeyp());
		getDao().save(notification);
		try {
			sendMail(notification);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void notifyRjetIntervention(Intervention intervention) {

		Notification notification = new Notification();
		notification.setIntervention(intervention);
		notification.setEtatAstreinte(intervention.getEtatAstreinte());
		notification.setDestinataires(intervention.getUtilisateur().getEmail());
		notification.setSubject("Gestion Astriente: Intervention ["
				+ intervention.getEtatAstreinte().getKeyp() + "]: Ref: "
				+ intervention.getAstreinte().getRef());

		notification.setContent("Bonjour<br/>" + "L'intervention  : "
				+ intervention.getTypeIntervention().getKeyp() + " : de "
				+ FORMATER.format(intervention.getDateD()) + " à "
				+ FORMATER.format(intervention.getDateF())
				+ "<br/> sur l'astreinte "
				+ intervention.getAstreinte().getRef() + " : "
				+ intervention.getEtatAstreinte().getKeyp());
		getDao().save(notification);
		try {
			sendMail(notification);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void sendMail(Notification notification) throws Exception {
		if (enableEmail && notification.getOccurence() <= seuil) {
			EmailMessage msg = new EmailMessage(exchangeService);
			msg.setSubject(notification.getSubject() + " ("
					+ notification.getOccurence() + ")");
			msg.setBody(MessageBody.getMessageBodyFromText(notification
					.getContent()));
			msg.getToRecipients().add(notification.getDestinataires());
			msg.send();
			log.trace("Send Mail for " + notification);
		}
	}

	private String getAstreinteInfoAsHTML(Astreinte astreinte) {
		StringBuilder message = new StringBuilder(astreinteTemplate);
		replaceAll(message, "#REF#", astreinte.getRef());
		replaceAll(message, "#TYPE#", astreinte.getType());
		replaceAll(message, "#SUJET#", astreinte.getSujet());
		replaceAll(message, "#DEMENDEUR#", astreinte.getDemandeur());
		replaceAll(message, "#DEBUT#", FORMATER.format(astreinte.getDateD()));
		replaceAll(message, "#FIN#", FORMATER.format(astreinte.getDateF()));

		replaceAll(message, "#CP#", astreinte.getCp().getNom()+ " "+ astreinte.getCp().getPrenom());
		replaceAll(message, "#ETAT#", astreinte.getEtatAstreinte()
				.getStringValue());
		for (String res : astreinte.getRessources().split("~")) {
			replaceAll(message, "#RES#", "<li>" + res + "</li>#RES#");
		}
		replaceAll(message, "#RES#", "");

		return message.toString();
	}

	private static void replaceAll(StringBuilder builder, String from, String to) {
		int index = builder.indexOf(from);
		while (index != -1) {
			builder.replace(index, index + from.length(), to);
			index += to.length(); // Move to the end of the replacement
			index = builder.indexOf(from, index);
		}
	}

	public static void main(String[] args) {
		String string = "<ui>#RES#</ui>";
		for (String str : "1,2,3,4,5".split(",")) {
			string = string.replaceAll("#RES#", "<li>" + str + "</li>#RES#");
			System.out.println(string);
		}
		string = string.replaceAll("#RES#", "");
		System.out.println("final : " + string);
	}
}
