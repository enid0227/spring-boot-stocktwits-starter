package com.example.stocktwitsdemo.security;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class StockTwitsOAuth2User implements OAuth2User {

  private OAuth2User user;
  private Map<String, Object> userAttributes;

  public StockTwitsOAuth2User(OAuth2User user) {
    this.user = user;
    this.userAttributes = user.getAttribute("user");
  }

  @Override
  public Map<String, Object> getAttributes() {
    return userAttributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthorities();
  }

  @Override
  public String getName() {
    return (String) userAttributes.get("username");
  }

  public String getUsername() {
    return (String) userAttributes.get("username");
  }

  public String getDisplayName() {
    return (String) userAttributes.get("name");
  }

  public Long getId() {
    return Long.valueOf((Integer) userAttributes.get("id"));
  }

  public String getEmail() {
    return (String) userAttributes.get("email");
  }
}
