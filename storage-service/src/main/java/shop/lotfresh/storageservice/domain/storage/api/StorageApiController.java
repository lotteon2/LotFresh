package shop.lotfresh.storageservice.domain.storage.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.lotfresh.storageservice.domain.storage.api.request.StorageCreateRequest;
import shop.lotfresh.storageservice.domain.storage.service.StorageService;

import javax.validation.Valid;


@RestController
@RequestMapping("/storage")
@CrossOrigin(origins = "http://localhost:3000")
public class StorageApiController{
    private final StorageService storageService;


    public StorageApiController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createStorage(@Valid @RequestBody StorageCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storageService.createStorage(request));
    }
}
