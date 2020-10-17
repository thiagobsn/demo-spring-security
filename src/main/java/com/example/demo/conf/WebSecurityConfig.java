package com.example.demo.conf;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AcessoUserDetailsService acessoUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().and()
			.authorizeRequests()
//				.antMatchers("/resources/**", "/webjars/**", "/api/autenticacao/grupos", "/api/autenticacao/permissoes").permitAll()
				.antMatchers("/resources/**", "/webjars/**").permitAll()
//				.authorizeRequests().antMatchers("/authenticate","/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll().
				.antMatchers("/", "/home", "/api/autenticacao/login").permitAll()
				.antMatchers("/api/autenticacao/usuarios").hasRole("USUARIO")
				.antMatchers("/api/autenticacao/permissoes").hasRole("USUARIO")
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//				.httpBasic()
//			.formLogin()
//				.loginPage("/login")
//				.permitAll()
//				.and()
//			.logout().permitAll();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	 @Override
	  public void configure(AuthenticationManagerBuilder builder) throws Exception {
//	    builder
//	        .inMemoryAuthentication()
//	        .withUser("thiago").password("123")
//	            .roles("USER")
//	        .and()
//	        .withUser("bernardo").password("123")
//	            .roles("USER");
		 
		 builder
	        .userDetailsService(acessoUserDetailsService)
	        .passwordEncoder(new BCryptPasswordEncoder());
	  }
	 
	 public static void main(String[] args) {
		 BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
		 System.out.println(encoder.encode("12345678"));
	}
	 
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();

//		configuration.setAllowedOrigins(Arrays.asList("https://www.yourdomain.com")); // www - obligatory
	    configuration.setAllowedOrigins(Arrays.asList("*"));  //set access from all domains
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	 
	
}
