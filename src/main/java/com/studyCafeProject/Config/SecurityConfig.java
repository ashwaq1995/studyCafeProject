package com.studyCafeProject.Config;


import com.studyCafeProject.Service.MyUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                .antMatchers("/api/v1/user/register").permitAll()
                .antMatchers("/api/v1/admin").hasAuthority("ADMIN")
                .antMatchers("/api/v1/user").hasAuthority("USER")

                .antMatchers("/api/v1/user/all","/api/v1/cafe/add/cafe", "/api/v1/cafe/update/cafe", "/api/v1/cafe/delete/cafe/{id}").hasAuthority("ADMIN")
                .antMatchers("/api/v1/cafe/{id}", "/api/v1/cafe/name", "/api/v1/cafe/rate").hasAnyAuthority("ADMIN", "USER")



                .anyRequest().authenticated()
                .and().httpBasic();

    }

}