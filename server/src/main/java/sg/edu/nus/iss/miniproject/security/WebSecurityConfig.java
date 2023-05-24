package sg.edu.nus.iss.miniproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import sg.edu.nus.iss.miniproject.security.jwt.AuthEntryPointJwt;
import sg.edu.nus.iss.miniproject.security.jwt.AuthTokenFilter;
import sg.edu.nus.iss.miniproject.services.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Order(1)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests().requestMatchers("/").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/control/**").permitAll()
                .requestMatchers("/api/saverecipes/**").permitAll()
                .requestMatchers("/api/contact/**").permitAll()
                .requestMatchers("/websocket/**").permitAll()
                .requestMatchers("typical-deer-production.up.railway.app/**").permitAll()
                .requestMatchers("/static/**", "/css/**").permitAll()
                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
   
    @Order(2)
    @Bean
    public SecurityFilterChain googleSecurityFilterChain(HttpSecurity http) throws Exception {

        return http
        .authorizeHttpRequests( authorizeConfig -> {
            authorizeConfig.requestMatchers("/").permitAll();
            authorizeConfig.requestMatchers("/login/**").permitAll();
            authorizeConfig.requestMatchers("/error").permitAll();
            authorizeConfig.requestMatchers("/favicon.ico").permitAll();
            authorizeConfig.anyRequest().authenticated();
        })
        .oauth2Login(oauth -> {
            oauth.loginPage("/login").permitAll();
            oauth.defaultSuccessUrl("/private");
            oauth.failureUrl("/login?error=true").permitAll();
        })
        .formLogin().loginPage("/login").and()
        .csrf()
        .disable()
        .build();
    }

}




