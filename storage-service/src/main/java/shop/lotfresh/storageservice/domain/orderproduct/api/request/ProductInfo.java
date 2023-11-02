package shop.lotfresh.storageservice.domain.orderproduct.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfo {

    private long productId;

    private Integer stock;

    private int isDeleted;
}
