package dev.java.ecommerce.basketservice.controller;

import dev.java.ecommerce.basketservice.enums.PaymentMethod;
import dev.java.ecommerce.basketservice.enums.Status;
import dev.java.ecommerce.basketservice.model.BasketModel;
import dev.java.ecommerce.basketservice.records.request.BasketRequest;
import dev.java.ecommerce.basketservice.records.request.PaymentRequest;
import dev.java.ecommerce.basketservice.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService service;

    @GetMapping("/{id}")
    public ResponseEntity<BasketModel> findBasketById(@PathVariable String id) {
        return ResponseEntity.ok(service.findBasketById(id));

    }

    @PostMapping
    public ResponseEntity<BasketModel> createBasket(@RequestBody BasketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createBasket(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<BasketModel> updateBasket(@PathVariable String id, @RequestBody BasketRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.updateBasket(id, request));
    }

    @PutMapping("{id}/payment")
    public ResponseEntity<BasketModel> payBasket(@PathVariable String id, @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.payBasket(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBasket(@PathVariable String id) {
        BasketModel carrinhoExistente = service.findBasketById(id);
        if (carrinhoExistente != null) {
            service.deleteBasket(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Carinho não existe nos nossos registros");
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findBasketByStatus(@PathVariable Status status) {
        List<BasketModel> carrinho = service.findBasketByStatus(status);

        if (carrinho.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não há nenhum carrinho com o status de " + status);
        } else {
            return ResponseEntity.ok(carrinho);
        }

    }

    @GetMapping("/payment/{paymentMethod}")
    public ResponseEntity<?> filterByPaymentBasket(@PathVariable PaymentMethod paymentMethod) {
        List<BasketModel> formaDePagamento = service.filterByPaymentBasket(paymentMethod);

        if (formaDePagamento.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de pagamento não encontrado");
        } else {
            return ResponseEntity.ok(formaDePagamento);
        }

    }

    @GetMapping
    public ResponseEntity<List<BasketModel>> getAll() {
        List<BasketModel> buscarTudo = service.getAll();
        return ResponseEntity.ok(buscarTudo);
    }

}
