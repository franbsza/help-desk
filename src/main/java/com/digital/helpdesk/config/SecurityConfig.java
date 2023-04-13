package com.digital.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/registration")
                .permitAll()
                .antMatchers("/**")
                .hasAnyAuthority("ADMIN", "USER")
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?errors=true")
                .defaultSuccessUrl("/users")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/denied");

    }

    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity
                .ignoring().antMatchers("/static/**", "/js/**","/css/**", "/resources/**", "/images/**", "/videos/**" );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
                .usersByUsernameQuery("select usr.email , usr.password, usr.active from helpdeskdb.users usr where usr.email = ? and usr.active=1")
                .authoritiesByUsernameQuery("select usr.email, rl.name from users usr " +
                        "inner join users_role usrr on (usr.id = usrr.user_id) " +
                        "inner join roles rl on (usrr.role_id = rl.id)" +
                        "where usr.email = ? " +
                        "and usr.active = 1")
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }


}
