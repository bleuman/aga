package net.atos.si.ma.mt.astreinte.web.managed;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.astreinte.service.ExchangeAuthenticatorService;
import net.atos.si.ma.mt.astreinte.service.UtilisateurService;

import org.quartz.xml.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("bean")
@Scope("session")
public class MngedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8415536101548089113L;

	@Autowired
	@Qualifier("utilisateurServiceImpl")
	private UtilisateurService utilisateurService;
	@Autowired
	@Qualifier("agaAuthenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	private ExchangeAuthenticatorService exchangeAuthenticatorService;

	@Autowired
	private ApplicationContext applicationContext;

	public String[] getListBeansSpring() {

		return applicationContext.getBeanDefinitionNames();

	}

	@Value("${default-theme}")
	private String theme;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}


	private Boolean updatePassword = true;

	private Utilisateur utilisateur =null;

	private Utilisateur aUtilisateur;

	public Boolean getUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(Boolean updatePassword) {
		this.updatePassword = updatePassword;
	}

	public Utilisateur getUtilisateur() {
		Object object = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (object instanceof Utilisateur) {
			Utilisateur ut = (Utilisateur) object;
			if(ut.getTheme()!=null && !ut.getTheme().trim().equals(""))
			this.theme=ut.getTheme();
			return ut;
		}
		return null;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Utilisateur getaUtilisateur() {
		return aUtilisateur;
	}

	public void setaUtilisateur(Utilisateur aUtilisateur) {
		this.aUtilisateur = aUtilisateur;
	}

	public String initPasswordupdate(Utilisateur user) {
		if (user != null)
			setaUtilisateur(user);
		else
			setaUtilisateur(new Utilisateur());
		return "passwordupdate";
	}

	public String login() throws ValidationException {
		try {
			Authentication request = new UsernamePasswordAuthenticationToken(
					utilisateur.getLogin().toLowerCase(),
					utilisateur.getPassword());
			Authentication result = authenticationManager.authenticate(request);
			utilisateur = (Utilisateur) result.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(result);
		} catch (AuthenticationException e) {
			throw new ValidationException(e.getMessage());
		}

		return "index";
	}

	public String loginByDas() throws ValidationException {
		try {
			boolean authed = exchangeAuthenticatorService
					.isUserAuthenthicated(utilisateur);
			// utilisateur = (Utilisateur) result.getPrincipal();

		} catch (AuthenticationException e) {
			throw new ValidationException(e.getMessage());
		}

		return "index";
	}

	public void updateUtilisateur(Utilisateur user) {

		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		if (updatePassword)
			user.setPassword(encoder.encodePassword(user.getPassword(), null));

		utilisateurService.update(user);

		String httpReferer = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestHeaderMap().get("referer");
		/*
		 * if (this.utilisateur.getRoles() == null ||
		 * this.utilisateur.getRoles().isEmpty()) return "goHomeIntervention";
		 * else return "goHome";
		 */

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(httpReferer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
