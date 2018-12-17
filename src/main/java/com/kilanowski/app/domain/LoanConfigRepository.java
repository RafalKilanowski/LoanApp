package com.kilanowski.app.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


interface LoanConfigRepository extends MongoRepository<LoanConfig, String> {

    Optional<LoanConfig> findFirstOrderByVersion();
}
