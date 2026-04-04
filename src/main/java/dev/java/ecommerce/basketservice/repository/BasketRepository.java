package dev.java.ecommerce.basketservice.repository;

import dev.java.ecommerce.basketservice.enums.Status;
import dev.java.ecommerce.basketservice.model.BasketModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BasketRepository extends MongoRepository<BasketModel, String> {

    Optional<BasketModel> findByClientAndStatus(Long id, Status status);

}
