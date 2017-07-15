package com.govans;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity(debug = true)
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	try{
    		JwtWebSecurityConfigurer
            .forRS256(apiAudience, issuer)
            .configure(http)
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/login").permitAll()
            .antMatchers(HttpMethod.GET, "/photos/**").hasAnyAuthority("read:photos")
            .antMatchers(HttpMethod.POST, "/photos/**").hasAuthority("create:photos")
            .antMatchers(HttpMethod.PUT, "/photos/**").hasAuthority("update:photos")
            .antMatchers(HttpMethod.DELETE, "/photos/**").hasAuthority("delete:photos")
            .anyRequest().authenticated();
    	}catch(Exception e){
    		System.err.println("ERROR :"+ e.getMessage());
    	}

    }

}
