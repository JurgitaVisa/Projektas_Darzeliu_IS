package it.akademija.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import it.akademija.user.UserDAO;
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private SecurityEntryPoint securityEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserDetailsPasswordService userDetailsPasswordService;

	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private JournalService journalService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		BcryptPepperEncoder pepper = new BcryptPepperEncoder();

		Map<String, PasswordEncoder> encoders = Map.of("pepper", pepper);
		DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder("pepper", encoders);

		delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(bcrypt);

		return delegatingPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder()).userDetailsPasswordManager(userDetailsPasswordService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests()
				// be saugumo UI dalis ir swaggeris
				.antMatchers("/", "/swagger-ui/").permitAll()
				// visi /api/ saugus (dar galima .anyRequest() )
				.antMatchers("/home/**", "/api/**", "/admin/**", "/naudotojai/**").authenticated().and().formLogin() // leidziam
																														// login
				// prisijungus
				.successHandler(new AuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {

						Authentication user = SecurityContextHolder.getContext().getAuthentication();

						String username = user.getName();
						Object[] roles = user.getAuthorities().toArray();
						String role = roles[0].toString().substring(5);

						LOG.info("Naudotojas [{}] prisijunge prie sistemos", username);
						
						journalService.newJournalEntry(OperationType.SUCCESSFUL_LOGIN, userDao.findByUsername(username).getUserId(), ObjectType.LOGIN, "Naudotojas prisijungė prie sistemos");

						response.setHeader("Access-Control-Allow-Credentials", "true");
						response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
						response.setHeader("Content-Type", "application/json;charset=UTF-8");
						response.getWriter().print("{\"username\": \"" + username + "\", \"role\":\"" + role + "\"}");

					}

				})
				// esant blogiems user/pass
				.failureHandler(new AuthenticationFailureHandler() {
					 
	                @Override
	                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                        AuthenticationException exception) throws IOException, ServletException {
	                    String username = request.getParameter("username");
						
	                    LOG.info("Nesėkmingas prisijungimas prie sistemos. Naudotojo vardas: {}", username);
	                    journalService.newJournalEntry(Long.valueOf(000), username, OperationType.UNSUCCESSFUL_LOGIN, Long.valueOf(000), ObjectType.LOGIN, "Nesėkmingas prisijungimas");
	                    response.sendError(401, "Neteisingas prisijungimo vardas ir/arba slaptažodis");
	                }
	            }).loginPage("/login").permitAll() // jis jau
																												// egzistuoja
				// atsijungimas nuo sistemos
				.and().logout().logoutUrl("/logout")
				// ištrina sausainėlius ir uždaro sesiją
				.clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID")

//				.logoutSuccessHandler(new LogoutSuccessHandler() {
//
//					@Override
//					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
//							Authentication authentication) throws IOException, ServletException {
//
//						LOG.info("** SecurityConfig: Naudotojas atsijunge nuo sistemos **");
//
//					}
//				})
				.logoutSuccessUrl("/").permitAll() // leidziam logout

				.and().csrf().disable() // nenaudojam tokenu
				// toliau forbidden klaidai
				.exceptionHandling().authenticationEntryPoint(securityEntryPoint).and().headers().frameOptions()
				.disable(); // H2 konsolei
	}

}
