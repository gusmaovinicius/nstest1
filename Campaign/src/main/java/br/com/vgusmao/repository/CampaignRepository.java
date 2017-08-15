package br.com.vgusmao.repository;

import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import br.com.vgusmao.model.Campaign;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CampaignRepository extends ReactiveMongoRepository<Campaign, String> {
	
	
	@Query("{'startDate' : ?0, 'endDate': ?1}")
	Mono<Campaign> findCampaignByDate(Date startDate, Date endDate);
		
	@Query("{'endDate' : {'$gt': ?0} }")	
	Flux<Campaign> listCurrentCamapaigns(Date date);
	
	@Query("{'endDate' : {'$gt': ?0}, 'name' : {$ne : ?0} }")	
	Flux<Campaign> listCurrentCamapaigns(Date date, String name);
}
