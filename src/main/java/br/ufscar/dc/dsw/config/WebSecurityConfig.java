package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

@Bean
public UserDetailsService userDetailsService() {
   return new UsuarioDetailsServiceImpl();
}

@Bean
public BCryptPasswordEncoder passwordEncoder() {
   return new BCryptPasswordEncoder();
}

@Bean
public DaoAuthenticationProvider authenticationProvider() {
   DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
   authProvider.setUserDetailsService(userDetailsService());
   authProvider.setPasswordEncoder(passwordEncoder());

   return authProvider;
}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   auth.authenticationProvider(authenticationProvider());
}

@Override
protected void configure(HttpSecurity http) throws Exception {
   http.csrf().disable().authorizeRequests()
   		.antMatchers("/pacientes", "/medicos", "/consultas").permitAll()
   		.antMatchers("/pacientes/{\\d+}", "/medicos/{\\d+}").permitAll()
   		.antMatchers("/consultas/{\\d+}").permitAll()
   		.antMatchers("/medicos/especialidades/{\\w+}").permitAll()
   		.antMatchers("/consultas/pacientes/{\\d+}").permitAll()
   		.antMatchers("/consultas/medicos/{\\d+}").permitAll()
           
   		.antMatchers("/", "/index", "/error","/medicos/listar","/consulta/listar").permitAll()
   		.antMatchers("/login/**", "/js/**", "/css/**","/image/**", "/webjars/**").permitAll()
		.antMatchers("/pacientes/listar","/pacientes/cadastrar","/medicos/cadastrar").hasRole("ADMIN")
   		.anyRequest().authenticated()
   	.and()
   		.formLogin()
   		.loginPage("/login")
   		.permitAll()
   	.and()
   		.logout()
   		.logoutSuccessUrl("/")
   		.permitAll();
}
}