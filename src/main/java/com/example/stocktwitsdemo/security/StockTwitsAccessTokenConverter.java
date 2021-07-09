package com.example.stocktwitsdemo.security;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.flogger.FluentLogger;
import java.util.Map;
import java.util.Set;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

public class StockTwitsAccessTokenConverter
    implements Converter<Map<String, String>, OAuth2AccessTokenResponse> {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private static final Set<String> TOKEN_RESPONSE_PARAMETER_NAMES =
      ImmutableSet.of(
          OAuth2ParameterNames.ACCESS_TOKEN,
          OAuth2ParameterNames.TOKEN_TYPE,
          OAuth2ParameterNames.EXPIRES_IN,
          OAuth2ParameterNames.REFRESH_TOKEN,
          OAuth2ParameterNames.SCOPE);

  @Override
  public OAuth2AccessTokenResponse convert(Map<String, String> tokenResponseParameters) {
    logger.atInfo().log("entering convert method");
    String accessToken = tokenResponseParameters.get(OAuth2ParameterNames.ACCESS_TOKEN);
    String refreshToken = tokenResponseParameters.get(OAuth2ParameterNames.REFRESH_TOKEN);

    TOKEN_RESPONSE_PARAMETER_NAMES.forEach(
        k -> {
          logger.atInfo().log("token[%s] = %s", k, tokenResponseParameters.get(k));
        });

    return OAuth2AccessTokenResponse.withToken(accessToken)
        .tokenType(TokenType.BEARER)
        .expiresIn(0L)
        .scopes(ImmutableSet.of("read"))
        .refreshToken(refreshToken)
        .additionalParameters(ImmutableMap.of("username", "username"))
        .build();
  }
}
