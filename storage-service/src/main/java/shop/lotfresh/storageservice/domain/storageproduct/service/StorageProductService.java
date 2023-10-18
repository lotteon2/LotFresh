package shop.lotfresh.storageservice.domain.storageproduct.service;

import org.springframework.stereotype.Service;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.repository.StorageProductRepository;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageProductOrder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageProductService {
    private final StorageProductRepository storageProductRepository;

    @Transactional
    public void addStock(Long storageProductId, Long quantityToAdd) {
        StorageProduct product = storageProductRepository.findById(storageProductId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + storageProductId));

        product.setStock(product.getStock() + quantityToAdd);
    }


    public StorageProductService(StorageProductRepository storageProductRepository) {
        this.storageProductRepository = storageProductRepository;
    }

    @Transactional
    public List<StorageProduct> findProductsByStorageId(Long storageId) {
        return storageProductRepository.findProductsByStorageId(storageId);
    }

    @Transactional
    public List<StorageProduct> findNearExpiryProductsByStorageId(Long storageId) {
        return storageProductRepository.findNearExpiryProductsByStorageId(storageId);
    }

    public Long getProductStock(Long storageId, Long productId) {
        return storageProductRepository.getProductStock(storageId, productId);
    }
    @Transactional
    public List<StorageProduct> getProductOrderList(Long storageId, Long productId) {
        return storageProductRepository.getProductOrderList(storageId, productId);
    }

    //TODO 음수, 0, 이상한값 들어오는거 처리해야함/ 예외처리랑 같이 작업하기
    //TODO 상품주문에 넘겨주는 로직 짜기 << 단순히 리턴값으로 재고 차단한 객체 ID 전달보다 좋은 방법 있는지?
  /*  @Transactional
    public List<StorageProduct> productOrder(Long storageId, Long productId, Long stock) {
        List<StorageProduct> products = storageProductRepository.getProductOrderList(storageId, productId);

        long totalStock = storageProductRepository.getProductStock(storageId, productId);

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
    }*/

    @Transactional
    public List<StorageProductOrder> productOrder(Long storageId, Long productId, Long stock) {
        List<StorageProduct> products = storageProductRepository.getProductOrderList(storageId, productId);

        long totalStock = storageProductRepository.getProductStock(storageId, productId);

        if (totalStock < stock) {
            throw new IllegalArgumentException("주문을 위한 재고가 부족합니다..");
        }

        List<StorageProductOrder> subtractedProducts = new ArrayList<>();

        for (StorageProduct product : products) {
            if (product.getStock() >= stock) {
                subtractedProducts.add(new StorageProductOrder(product.getId(), stock));
                product.setStock(product.getStock() - stock);
                break;
            } else {
                subtractedProducts.add(new StorageProductOrder(product.getId(), product.getStock()));
                stock -= product.getStock();
                product.setStock(0L);
            }
        }

        return subtractedProducts;
    }

}