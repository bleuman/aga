package net.atos.si.ma.mt.config;

import net.atos.si.ma.mt.config.service.GenericUtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	GenericUtilisateurService utilisateurService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(utilisateurService).passwordEncoder(
				new ShaPasswordEncoder());
	}

	// @Bean(name = "authenticationManager")
	// @Override
	// public AuthenticationManager authenticationManager() throws Exception {
	// return super.authenticationManagerBean();
	// }te

	@Bean(name = "agaAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println(".......configuring security..................");
		http.headers().disable().csrf().disable().authorizeRequests().antMatchers("/secure/**")
				.authenticated().antMatchers("/unsecure/**").authenticated()
				.antMatchers("/javax.faces.resource/**").permitAll().and()
				.formLogin().loginProcessingUrl("/login")
				.loginPage("/login.xhtml").failureUrl("/login.xhtml?error=X").permitAll().and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/login.xhtml")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "current_node", "locale");
	}
}
