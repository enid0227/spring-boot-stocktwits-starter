package com.example.stocktwitsdemo.security;

import com.google.common.flogger.FluentLogger;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Autowired private StockTwitsOAuth2UserService stockTwitsOAuth2UserService;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests()
        .antMatchers("/", "/login**", "/error**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .logout()
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID")
        .and()
        .oauth2Login()
        .userInfoEndpoint()
        .userService(stockTwitsOAuth2UserService)
        .and()
        .tokenEndpoint()
        .accessTokenResponseClient(stockTwitsTokenResponseClient());
  }

  private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
      stockTwitsTokenResponseClient() {
    logger.atInfo().log("entering security converter dispatch");

    OAuth2AccessTokenResponseHttpMessageConverter tokenCoverter =
        new OAuth2AccessTokenResponseHttpMessageConverter();
    tokenCoverter.setTokenResponseConverter(new StockTwitsAccessTokenConverter());
    // No public API to set converter directly. Therfore, set RestOperations instead
    // see default implementation at:
    // https://github.com/spring-projects/spring-security/blob/main/oauth2/oauth2-client/src/main/java/org/springframework/security/oauth2/client/endpoint/DefaultAuthorizationCodeTokenResponseClient.java
    DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient =
        new DefaultAuthorizationCodeTokenResponseClient();
    RestTemplate restTemplate =
        new RestTemplate(Arrays.asList(new FormHttpMessageConverter(), tokenCoverter));
    restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
    accessTokenResponseClient.setRestOperations(restTemplate);
    return accessTokenResponseClient;
  }
}
