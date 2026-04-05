package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.enums.PaymentMethod;
import dev.java.ecommerce.basketservice.enums.Status;
import dev.java.ecommerce.basketservice.exception.BusinessException;
import dev.java.ecommerce.basketservice.exception.DataNotFoundException;
import dev.java.ecommerce.basketservice.model.BasketModel;
import dev.java.ecommerce.basketservice.model.ProductModel;
import dev.java.ecommerce.basketservice.records.request.BasketRequest;
import dev.java.ecommerce.basketservice.records.request.PaymentRequest;
import dev.java.ecommerce.basketservice.records.response.PlatziProductResponse;
import dev.java.ecommerce.basketservice.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    public BasketModel findBasketById(String id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Carinho não encontrado"));
    }


    public BasketModel createBasket(BasketRequest basketRequest) {
        basketRepository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(basketModel -> {
                    throw new BusinessException("Há um carrinho aberto para este cliente");
                });

        List<ProductModel> productModels = getProductModels(basketRequest);

        BasketModel basketModel = BasketModel.builder()
                .client(basketRequest.clientId())
                .status(Status.OPEN)
                .products(productModels)
                .build();
        basketModel.calculeteTotalPrice();
        return basketRepository.save(basketModel);
    }



    public BasketModel updateBasket(String id, BasketRequest request) {
        BasketModel saveBaseket = findBasketById(id);

        List<ProductModel> productUpdated = getProductModels(request);

        saveBaseket.setProducts(productUpdated);

        saveBaseket.calculeteTotalPrice();
        return basketRepository.save(saveBaseket);
    }

    public BasketModel payBasket(String id, PaymentRequest request) {
        BasketModel saveBaseket = findBasketById(id);
        saveBaseket.setPaymentMethod(request.paymentMethod());
        saveBaseket.setStatus(Status.SOLD);
        return basketRepository.save(saveBaseket);
    }

    public void deleteBasket(String id) {
            basketRepository.deleteById(id);
    }

    private @NonNull List<ProductModel> getProductModels(BasketRequest basketRequest) {
        List<ProductModel> productModels = new ArrayList<>();
        basketRequest.products().forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductsById(productRequest.id());
            productModels.add(ProductModel.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(productRequest.quantity())
                    .build());
        });
        return productModels;
    }

    public List<BasketModel> findBasketByStatus(Status status) {
        List<BasketModel> statusBasket = basketRepository.getByStatus(status);

        List<BasketModel> filtrarCarinho = statusBasket.stream()
                .filter(basketModel -> basketModel.getStatus().equals(status))
                .toList();

        return filtrarCarinho;
    }

    public List<BasketModel> filterByPaymentBasket(PaymentMethod paymentMethod) {
        List<BasketModel> formaDePagamento = basketRepository.getByPaymentMethod(paymentMethod);

        List<BasketModel> filtrarPagamentos = formaDePagamento.stream()
                .filter(formPay -> formPay.getPaymentMethod().equals(paymentMethod))
                .toList();
        return filtrarPagamentos;
    }


}
