package dev.java.ecommerce.basketservice.repository;

import dev.java.ecommerce.basketservice.enums.PaymentMethod;
import dev.java.ecommerce.basketservice.enums.Status;
import dev.java.ecommerce.basketservice.model.BasketModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends MongoRepository<BasketModel, String> {

    Optional<BasketModel> findByClientAndStatus(Long id, Status status);

    List<BasketModel> getByStatus(Status status);

    List<BasketModel> getByPaymentMethod(PaymentMethod paymentMethod);

}
