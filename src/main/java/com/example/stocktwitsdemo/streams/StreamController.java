package com.example.stocktwitsdemo.streams;

import com.google.common.collect.ImmutableList;
import com.stocktwitlist.api.client.StocktwitsClient;
import com.stocktwitlist.api.exception.StocktwitsApiException;
import com.stocktwitlist.api.value.StreamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/streams")
public class StreamController {

  @Autowired private StocktwitsClient stocktwitsClient;

  @GetMapping(path = "/trending")
  public @ResponseBody StreamResponse trending(
      @RegisteredOAuth2AuthorizedClient("stocktwits") OAuth2AuthorizedClient authorizedClient)
      throws StocktwitsApiException {
    return stocktwitsClient
        .newRequest(authorizedClient.getAccessToken().getTokenValue())
        .streams()
        .trending()
        .send();
  }

  @GetMapping(path = "/symbols")
  public @ResponseBody StreamResponse symbols(
      @RegisteredOAuth2AuthorizedClient("stocktwits") OAuth2AuthorizedClient authorizedClient)
      throws StocktwitsApiException {
    return stocktwitsClient
        .newRequest(authorizedClient.getAccessToken().getTokenValue())
        .streams()
        .symbols(ImmutableList.of("AAPL", "MSFT"))
        .send();
  }
}
