package shop.lotfresh.storageservice.domain.storageproduct.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StorageStockVo {
    private Long productId;
    private String province;
    private Integer stock;

}
