package dev.java.ecommerce.basketservice.records.request;

import dev.java.ecommerce.basketservice.enums.PaymentMethod;
import lombok.Builder;

@Builder
public record PaymentRequest(PaymentMethod paymentMethod) {
}
