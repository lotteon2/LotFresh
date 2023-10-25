package com.lotfresh.orderservice.domain.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.ProductInfo;
import com.lotfresh.orderservice.domain.orchestrator.service.OrchestratorService;
import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@EmbeddedKafka(
        topics = {"testTopic"},
        ports = 7777,
        brokerProperties = {"listener=PLAINTEXT://localhost:7777"}
)
@SpringBootTest
@Transactional
public class feignTest {
    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    @Autowired
    private OrchestratorService orchestratorService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @Test
    void inventory와_feign_테스트() {
        ProductInfo productInfo = ProductInfo.builder()
                .productId(1L)
                .stock(1L)
                .build();
        InventoryRequest request = InventoryRequest.builder()
                .province("서울시")
                .orderId(1L)
                .productInfos(List.of(productInfo))
                .build();
        inventoryFeignClient.deductNormalStock(request);
    }

    @Test
    void inventoryRollbackTest() {
        Order order = Order.builder()
                .authId(1L)
                .build();
        orderRepository.save(order);
        em.flush();
        em.clear();

        Order myOrder = orderRepository.findById(1L).get();
        System.out.println("before" + myOrder.getIsDeleted());

        try {
            orchestratorService.orderNormalTransaction(1L,"서울시",1L,true);
        } catch (Exception e){
        }
        em.flush();
        em.clear();

        System.out.println("after" + myOrder.getIsDeleted());
    }
}
