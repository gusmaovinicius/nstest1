package br.com.vgusmao;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import br.com.vgusmao.service.FanHandler;


@Configuration
public class Routes {
	
	private static final String PATH = "/api/partnerfan";
	  private final FanHandler fanHandler;
	
	  @Autowired
	  public Routes(FanHandler fanHandler) {
	      this.fanHandler = fanHandler;
	  }
	
	  @Bean
	  public RouterFunction<?> routerFunction() {
	
	      return nest(path(PATH),
	              nest(accept(MediaType.APPLICATION_JSON),
	                      route(GET("/"), fanHandler::listAll)
	                      .andRoute(POST("/"), fanHandler::create)
	                      .andRoute(POST("/validateEmail"), fanHandler::validateEmail)
	                      .andRoute(POST("/listFanSubscriptions"), fanHandler::listFanSubscriptions)
	              ));	
	  }
}
