package net.atos.si.ma.mt.config.astreinte;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.model.Entite;
import net.atos.si.ma.mt.astreinte.model.EtatAstreinte;
import net.atos.si.ma.mt.astreinte.model.Intervention;
import net.atos.si.ma.mt.astreinte.model.Jf;
import net.atos.si.ma.mt.astreinte.model.Notification;
import net.atos.si.ma.mt.astreinte.model.Parameter;
import net.atos.si.ma.mt.astreinte.model.Theme;
import net.atos.si.ma.mt.astreinte.model.TypeIntervention;
import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.dao.impl.GenericDaoImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DAOConfiguration {
	@Bean
	GenericDAO<Astreinte, Long> astreinteDAO() {
		return new GenericDaoImpl<>(Astreinte.class);

	}

	@Bean
	GenericDAO<Utilisateur, Long> utilisateurDAO() {
		return new GenericDaoImpl<>(Utilisateur.class);

	}

	@Bean
	GenericDAO<Parameter, Long> parameterDAO() {
		return new GenericDaoImpl<>(Parameter.class);
	}

	@Bean
	GenericDAO<Theme, Long> themeDAO() {
		return new GenericDaoImpl<>(Theme.class);
	}

	@Bean
	GenericDAO<Jf, Long> jfDAO() {
		return new GenericDaoImpl<>(Jf.class);
	}

	@Bean
	GenericDAO<TypeIntervention, Long> typeInterventionDAO() {
		return new GenericDaoImpl<>(TypeIntervention.class);
	}

	@Bean
	GenericDAO<EtatAstreinte, Long> etatAstreinteDAO() {
		return new GenericDaoImpl<>(EtatAstreinte.class);
	}

	@Bean
	GenericDAO<Entite, Long> entiteDAO() {
		return new GenericDaoImpl<>(Entite.class);
	}

	@Bean
	GenericDAO<Intervention, Long> interventionDAO() {
		return new GenericDaoImpl<>(Intervention.class);
	}

	@Bean
	GenericDAO<Notification, Long> notificationDAO() {
		return new GenericDaoImpl<>(Notification.class);
	}

}
