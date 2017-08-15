package br.com.vgusmao.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import br.com.vgusmao.model.Fan;

public interface FanRepository extends ReactiveMongoRepository<Fan, String> {

}
