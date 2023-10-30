package shop.lotfresh.storageservice.domain.orderproduct.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import shop.lotfresh.storageservice.domain.orderproduct.api.OrderProductApiController;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.OrderProductRequest;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.ProductInfo;
import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import shop.lotfresh.storageservice.domain.orderproduct.repository.OrderProductRepository;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageProductOrder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class OrderProductService {
    private static final Logger logger = LoggerFactory.getLogger(OrderProductApiController.class);
    private final OrderProductRepository orderProductRepository;
    private final StorageProductService storageProductService;

    public OrderProductService(OrderProductRepository orderProductRepository, StorageProductService storageProductService) {
        this.orderProductRepository = orderProductRepository;
        this.storageProductService = storageProductService;
    }


    @Transactional
    public void inventory(Long orderId) {
        List<OrderProduct> inventoryList = orderProductRepository.inventory(orderId);

        if (inventoryList.isEmpty()) {
            throw new IllegalArgumentException("Invalid order ID: " + orderId);
        }

        for (OrderProduct lists : inventoryList){
            storageProductService.addStock(lists.getStorageProductId(), lists.getStock());
        };
    }

    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Transactional
    public void orderProduct(@RequestBody OrderProductRequest request) throws Exception {
        String province = request.getProvince();
        long orderId = request.getOrderId();
        List<ProductInfo> productInfos = request.getProductInfos();

        List<StorageProductOrder> productOrders = new ArrayList<>();
        for (ProductInfo infos : productInfos) {
            List<StorageProductOrder> orders = storageProductService.productOrder(province, infos.getProductId(), infos.getStock());
            productOrders.addAll(orders);
        }

        for (StorageProductOrder productOrder : productOrders) {
            OrderProduct orderProd = new OrderProduct();
            orderProd.setStorageProductId(productOrder.getStorageProductId());
            orderProd.setStock(productOrder.getStock());
            orderProd.setIsDeleted(0);
            orderProd.setOrderId(orderId);

            orderProductRepository.save(orderProd);
        }
    }

    public void orderSalesProduct(@RequestBody OrderProductRequest request) throws Exception {
        String province = request.getProvince();
        long orderId = request.getOrderId();
        List<ProductInfo> productInfos = request.getProductInfos();

        List<StorageProductOrder> productOrders = new ArrayList<>();
        for (ProductInfo infos : productInfos) {
            List<StorageProductOrder> orders = storageProductService.productsalesOrder(province, infos.getProductId(), infos.getStock());
            productOrders.addAll(orders);
        }

        for (StorageProductOrder productOrder : productOrders) {
            OrderProduct orderProd = new OrderProduct();
            orderProd.setStorageProductId(productOrder.getStorageProductId());
            orderProd.setStock(productOrder.getStock());
            orderProd.setIsDeleted(0);
            orderProd.setOrderId(orderId);

            orderProductRepository.save(orderProd);
        }
    }

}