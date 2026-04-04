package dev.java.ecommerce.basketservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.java.ecommerce.basketservice.enums.PaymentMethod;
import dev.java.ecommerce.basketservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection  = "basket")
public class BasketModel {

    @Id
    private String id;

    private Long client;

    private BigDecimal totalPrice;

    private List<ProductModel> products;

    // se o valor do retorno no campo paymentMethod for NULL não aparece na requisição
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaymentMethod paymentMethod;

    private Status status;

    public void calculeteTotalPrice() {
        this.totalPrice = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
