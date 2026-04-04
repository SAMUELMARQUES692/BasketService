package dev.java.ecommerce.basketservice.records.request;

import java.util.List;

public record BasketRequest(Long clientId, List<ProductRequest> products) {
}
