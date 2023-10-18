package shop.lotfresh.storageservice.domain.orderproduct.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductRequest {
    private long StorageId;

    private long orderDetailId;

    private long productId;

    private long stock;

    private int isDeleted;

}
