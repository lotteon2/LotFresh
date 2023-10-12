package shop.lotfresh.storageservice.domain.storageproduct.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.repository.StorageProductRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StorageProductService {
    private final StorageProductRepository storageProductRepository;

    @Autowired
    public StorageProductService(StorageProductRepository storageProductRepository) {
        this.storageProductRepository = storageProductRepository;
    }

    @Transactional
    public List<StorageProduct> findProductsByStorageId(Long storageId) {
        return storageProductRepository.findProductsByStorageId(storageId);
    }

    //TODO 리턴값 재고만 줄지, 객체 다 줄지 생각해보기
    public List<StorageProduct> productStockCheck(Long storageId, Long productId) {
        return storageProductRepository.productStockCheck(storageId, productId);
    }

    //TODO 음수, 0, 이상한값 들어오는거 처리해야함
    @Transactional
    public List<StorageProduct> productOrder(Long storageId, Long productId, Long stock) {
        List<StorageProduct> products = storageProductRepository.productStockCheck(storageId, productId);

        long totalStock = products.stream().mapToLong(StorageProduct::getStock).sum();
        if (totalStock < stock) {
            throw new IllegalArgumentException("주문을 위한 재고가 부족합니다..");
        }

        for (StorageProduct product : products) {
            if (product.getStock() >= stock) {
                product.setStock(product.getStock() - stock);
                break;
            } else {
                stock -= product.getStock();
                product.setStock(0L);
            }
        }

        return products;
    }


}
