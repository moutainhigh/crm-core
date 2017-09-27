package com.cafe.crm.security.configs;


import com.cafe.crm.security.handlers.CustomAuthenticationSuccessHandler;
import com.cafe.crm.security.handlers.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	@Autowired
	private CustomLogoutHandler customLogoutHandler;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
	}

	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}

//	@Bean
//	public CustomLogoutHandler getLogoutHandler() {
//		return new CustomLogoutHandler();
//	}

	@Bean
	public CustomAuthenticationSuccessHandler getHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		http
				.authorizeRequests()
				.antMatchers("/manager/**").hasAnyAuthority("MANAGER", "BOSS")
				.antMatchers("/boss/**", "/worker/**", "/advertising/**").hasAuthority("BOSS")
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/processing-url")
				.successHandler(customAuthenticationSuccessHandler)
				.usernameParameter("username")
				.passwordParameter("password")
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.logoutSuccessHandler(customLogoutHandler)
				.invalidateHttpSession(true)
				.permitAll()
				.and()
				.csrf().disable()
				.addFilterBefore(characterEncodingFilter, CsrfFilter.class);

		http
				.sessionManagement()
				.maximumSessions(100)
				.maxSessionsPreventsLogin(false)
				.expiredUrl("/login?logout")
				.sessionRegistry(sessionRegistry());
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}
}
