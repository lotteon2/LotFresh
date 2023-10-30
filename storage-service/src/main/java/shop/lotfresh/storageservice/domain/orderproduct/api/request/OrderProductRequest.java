package shop.lotfresh.storageservice.domain.orderproduct.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderProductRequest {
    private String province;
    private long orderId;
    private List<ProductInfo> productInfos;

}
