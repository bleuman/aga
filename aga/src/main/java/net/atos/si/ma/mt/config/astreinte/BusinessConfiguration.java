package net.atos.si.ma.mt.config.astreinte;

import java.net.URI;
import java.net.URISyntaxException;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import net.atos.si.ma.mt.astreinte.service.AstreinteService;
import net.atos.si.ma.mt.astreinte.service.EtatAstreinteService;
import net.atos.si.ma.mt.astreinte.service.NotificationService;
import net.atos.si.ma.mt.astreinte.service.impl.AstreinteUtils;
import net.atos.si.ma.mt.astreinte.service.impl.NotificationUtils;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class BusinessConfiguration {
	@Value("${url}")
	private String url;
	@Value("${login}")
	private String login;
	@Value("${password}")
	private String password;

	@Value("${domaine}")
	private String domaine;
	@Value("${cronExpression}")
	private String cronExpression;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private AstreinteService astreinteService;

	@Autowired
	private EtatAstreinteService etatAstreinteService; 

	@Bean
	ExchangeService exchangeAuthenticator() throws URISyntaxException {

		ExchangeService exchangeService = new ExchangeService();
		exchangeService.setUrl(new URI(url));
		return exchangeService;
	}

	@Bean
	ExchangeService exchangeService() throws URISyntaxException {

		ExchangeService exchangeService = new ExchangeService();
		exchangeService.setUrl(new URI(url));
		exchangeService.setCredentials(new WebCredentials(login, password,
				domaine));
		return exchangeService;
	}

	@Bean
	String setUtilsRules() {
		NotificationUtils.setNotificationService(notificationService);
		AstreinteUtils.setAstreinteService(astreinteService);
		AstreinteUtils.setEtatAstreinteService(etatAstreinteService);
		return null;
	}
	
	@Bean
	MethodInvokingJobDetailFactoryBean notificationJob() {
		MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
		bean.setTargetObject(notificationService);
		bean.setTargetMethod("manageNotifications");
		return bean;

	}

	@Bean
	CronTriggerFactoryBean notifTrigger() {
		CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
		bean.setJobDetail(notificationJob().getObject());
		bean.setCronExpression(cronExpression);

		return bean;
	}

	@Bean
	SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setJobDetails(new JobDetail[] { notificationJob().getObject() });
		bean.setTriggers(new CronTrigger[] { notifTrigger().getObject() });
		return bean;
	}

}
