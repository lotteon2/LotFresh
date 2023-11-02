package shop.lotfresh.storageservice.domain.storageproduct.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StorageProductOrder {
    private Long storageProductId;
    private Integer stock;

    // getters and setters

    public StorageProductOrder(Long storageProductId, Integer quantity) {
        this.storageProductId = storageProductId;
        this.stock = quantity;
    }

}

