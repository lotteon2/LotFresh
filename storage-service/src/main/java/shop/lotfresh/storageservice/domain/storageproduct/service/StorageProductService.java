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

    @Transactional
    //TODO 리턴값 재고만 줄지, 객체 다 줄지 생각해보기
    public List<StorageProduct> productStockCheck(Long storageId, Long productId) {
        return storageProductRepository.productStockCheck(storageId, productId);
    }

    //TODO 음수, 0, 이상한값 들어오는거 처리해야함/ 예외처리랑 같이 작업하기
    //TODO 상품주문에 넘겨주는 로직 짜기 << 단순히 리턴값으로 재고 차단한 객체 ID 전달보다 좋은 방법 있는지?
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
