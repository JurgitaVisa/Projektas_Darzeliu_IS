package it.akademija.security;

import java.io.IOException;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import it.akademija.user.UserDAO;

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
	private UserDAO userDao;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// be saugumo UI dalis ir swaggeris
				.antMatchers("/", "/swagger-ui/", "/hello/**", "/createuser", "/darzelis/**").permitAll()
				// visi /api/ saugus (dar galima .anyRequest() )
				.antMatchers("/api/**").authenticated().and().formLogin() // leidziam login
				// prisijungus
				.successHandler(new AuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {

						LOG.info("** SecurityConfig: Naudotojas [{}] prisijunge prie sistemos **",
								SecurityContextHolder.getContext().getAuthentication().getName());

						response.setHeader("Access-Control-Allow-Credentials", "true");
						response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
						response.setHeader("Content-Type", "application/json;charset=UTF-8");
						response.getWriter()
								.print("{\"username\": \""
										+ SecurityContextHolder.getContext().getAuthentication().getName()
										+ "\", \"role\":\""
										+ userDao.findByUsername(
												SecurityContextHolder.getContext().getAuthentication().getName())
												.getRole()

										+ "\"}");

					}

				})
				// esant blogiems user/pass
				.failureHandler(new SimpleUrlAuthenticationFailureHandler()).loginPage("/login").permitAll() // jis jau
																												// egzistuoja
				.and().logout().logoutUrl("/logout").logoutSuccessHandler(new LogoutSuccessHandler() {

					@Override
					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						response.setHeader("Access-Control-Allow-Credentials", "true");
						response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
						response.setHeader("Content-Type", "application/json;charset=UTF-8");

						LOG.info("** SecurityConfig: Naudotojas atsijunge nuo sistemos **");

					}
				})
				// ištrina sausainėlius ir uždaro sesiją
				.clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll() // leidziam
																												// //
																												// /logout
				.and().csrf().disable() // nenaudojam tokenu
				// toliau forbidden klaidai
				.exceptionHandling().authenticationEntryPoint(securityEntryPoint).and().headers().frameOptions()
				.disable(); // H2 konsolei
	}

}
