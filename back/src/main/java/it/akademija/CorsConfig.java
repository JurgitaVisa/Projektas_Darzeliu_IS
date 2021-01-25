package it.akademija;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 
 *  Cross-origin resource sharing
 * 
 * @author Jurgita
 *
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
//	Išjungiame visiems serverio URL "/**" (mapping), visiems metodams "*" (pvz. Options
//	preflight check), patikrinimą taip, kad visi "*" šaltiniai (origins) būtų leidžiami
//	leidžiame allowCredentials=true siųsti cookies informaciją tarp skirtingų domenų
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*").allowCredentials(true);
	}
}
