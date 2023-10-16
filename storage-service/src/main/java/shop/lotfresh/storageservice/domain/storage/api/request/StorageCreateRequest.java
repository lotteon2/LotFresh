package shop.lotfresh.storageservice.domain.storage.api.request;

import lombok.Getter;
import lombok.Setter;
import shop.lotfresh.storageservice.domain.storage.entity.Storage;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StorageCreateRequest {

    @NotNull(message = "StorageName can not be null")
    private String name;

    @NotNull(message = "StorageId can not be null")
    private String province;


    public Storage toEntity() {
        return Storage.builder()
                .name(name)
                .province(province)
                .build();
    }
}
