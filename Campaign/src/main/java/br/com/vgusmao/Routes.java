package br.com.vgusmao;


import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import br.com.vgusmao.service.CampaignHandler;

@Configuration
public class Routes {

	
	private static final String PATH = "/api/campaign";
	  private final CampaignHandler campaignHandler;
	
	  @Autowired
	  public Routes(CampaignHandler campaignHandler) {
	      this.campaignHandler = campaignHandler;
	  }
	
	  @Bean
	  public RouterFunction<?> routerFunction() {
	
	      return nest(path(PATH),
	              nest(accept(MediaType.APPLICATION_JSON),
	                      route(GET("/"), campaignHandler::listAll)
	                          .andRoute(POST("/"), campaignHandler::save)
	                          .andRoute(PUT("/{id}"), campaignHandler::update)
	                          .andRoute(DELETE("/{id}"), campaignHandler::delete)
	              ));
	
	  }
}
