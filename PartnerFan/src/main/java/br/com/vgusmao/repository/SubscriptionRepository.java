package br.com.vgusmao.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import br.com.vgusmao.model.FanSubscription;
import reactor.core.publisher.Flux;

public interface SubscriptionRepository  extends ReactiveMongoRepository<FanSubscription, String> {
	
	@Query("{'fanEmail' : {'$eq': ?0} }")	
	Flux<FanSubscription> listFanSubscriptions(String email);
}
