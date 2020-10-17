package com.example.demo.conf;

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

	
}
