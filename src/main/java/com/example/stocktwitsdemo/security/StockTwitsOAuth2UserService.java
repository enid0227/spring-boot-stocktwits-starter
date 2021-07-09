package com.example.stocktwitsdemo.security;

import com.example.stocktwitsdemo.user.UserEntity;
import com.example.stocktwitsdemo.user.UserRepository;
import com.google.common.flogger.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class StockTwitsOAuth2UserService extends DefaultOAuth2UserService {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Autowired private UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User user = super.loadUser(userRequest);

    user.getAttributes()
        .forEach(
            (k, v) -> {
              logger.atInfo().log("[oauth2user]%s=%s", k, v.toString());
            });

    StockTwitsOAuth2User stUser = new StockTwitsOAuth2User(user);
    UserEntity userRecord = userRepository.getUserByUsername(stUser.getUsername());

    if (userRecord == null) {
      logger.atInfo().log("new stocktwit user registration flow");
      UserEntity newUser = new UserEntity();
      newUser.setUsername(stUser.getName());
      newUser.setDisplayName(stUser.getDisplayName());
      newUser.setEmail(stUser.getEmail());
      newUser.setId(stUser.getId());
      userRepository.save(newUser);
    } else {
      logger.atInfo().log("returning user flow");
    }
    return new StockTwitsOAuth2User(user);
  }
}
