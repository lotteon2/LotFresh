package com.lotfresh.orderservice.repository;

import com.lotfresh.orderservice.domain.order.Order;
import com.lotfresh.orderservice.domain.productOrder.ProductOrder;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductOrderRepositoryTest {
    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("ProductOrderId의 orderId는 MapsId에 의해 ProductOrder를 생성할 때 사용된 Order의 id와 같다")
    @Test
    void createProductOrder(){
        // given
        Order savedOrder = orderRepository.save(Order.builder()
                .authId(1L)
                .build());
        ProductOrderId productOrderId = ProductOrderId.builder()
                .productId(1L)
                .build();

        ProductOrder productOrder = makeProductOrder(productOrderId,savedOrder);

        // when
        ProductOrder savedProductOrder = productOrderRepository.save(productOrder);

        // then
        Assertions.assertThat(savedProductOrder.getId().getOrderId())
                .isEqualTo(savedOrder.getId());
    }

    @DisplayName("동일한 Order와 productId를 가지는 ProductOrder를 생성할 수 없다")
    @Test
    void cannotCreateDuplicatePrimaryKey(){
        // given
        Long productId = 1L;
        Order savedOrder = orderRepository.save(Order.builder()
                .authId(1L)
                .build());
        ProductOrderId productOrderId1 = ProductOrderId.builder()
                .productId(productId)
                .build();
        ProductOrderId productOrderId2 = ProductOrderId.builder()
                .productId(productId)
                .build();

        ProductOrder productOrder1 = makeProductOrder(productOrderId1,savedOrder);
        ProductOrder productOrder2 = makeProductOrder(productOrderId2,savedOrder);

        // when // then
        Assertions.assertThatThrownBy(() -> {
                productOrderRepository.save(productOrder1);
                productOrderRepository.save(productOrder2);
            }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    private static ProductOrder makeProductOrder(ProductOrderId productOrderId, Order order){
        return ProductOrder.builder()
                .id(productOrderId)
                .order(order)
                .price(1000L)
                .quantity(2L)
                .status(ProductOrderStatus.CREATED)
                .build();
    }

}