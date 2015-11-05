package net.atos.si.ma.mt.astreinte.service;

import java.util.List;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.model.Intervention;
import net.atos.si.ma.mt.astreinte.model.Notification;
import net.atos.si.ma.mt.config.service.GenericService;

public interface NotificationService extends GenericService<Notification,Long> {

	void manageNotifications();

	void notifyAffectation(Astreinte astreinte);

	void notifyAValidation(Astreinte astreinte);

	void notifyRejet(Astreinte astreinte);

	void notifyValidation(Astreinte astreinte);

	void notifyAValidationIntervention(Astreinte astreinte,
			List<Intervention> interventions);

	void notifyValidationIntervention(Intervention intervention);

	void notifyRjetIntervention(Intervention interventions);

	void sendMail(Notification notification) throws Exception;

	Integer getSeuil();
	
}
