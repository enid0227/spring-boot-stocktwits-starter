package com.example.stocktwitsdemo.user;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @GetMapping("/user")
  public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
    return principal != null
        ? ImmutableMap.of("name", principal.getAttribute("name"))
        : ImmutableMap.of();
  }
}
