package org.scd.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String BASIC_USER_ROLE = "BASIC_USER";

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(@Qualifier("customUserDetailsService") final UserDetailsService userDetailsService,
                          final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users", "/users/login", "/users/register").permitAll()
                .antMatchers("/users/me").hasAnyRole(ADMIN_ROLE, BASIC_USER_ROLE)
                .antMatchers("/users/get_all").hasAnyRole(ADMIN_ROLE)       //get all users
                .antMatchers("/users/update/{id}").hasAnyRole(ADMIN_ROLE, BASIC_USER_ROLE)          //update
                //    .antMatchers("/users").hasAnyRole(ADMIN_ROLE)
                .antMatchers("/users/delete/{id}").hasAnyRole(ADMIN_ROLE, BASIC_USER_ROLE)
                .antMatchers("/locations/add").hasAnyRole(ADMIN_ROLE, BASIC_USER_ROLE)
                .antMatchers("/locations/get/{id}").hasAnyRole(ADMIN_ROLE, BASIC_USER_ROLE)
                .antMatchers("/locations/update/{id}").hasAnyRole(ADMIN_ROLE, BASIC_USER_ROLE)
                .antMatchers("/location/delete/{id}").hasAnyRole(ADMIN_ROLE, BASIC_USER_ROLE)
                .antMatchers("/locations/filterASC").hasAnyRole(ADMIN_ROLE)         //filter ASC by date
                .antMatchers("/locations/filterDESC").hasAnyRole(ADMIN_ROLE)        //filter DESC by date
                .antMatchers("/locations/filterID/{id}").hasAnyRole(ADMIN_ROLE, BASIC_USER_ROLE)          //filter by ID
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        /* To allow Pre-flight [OPTIONS] request from browser */
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
