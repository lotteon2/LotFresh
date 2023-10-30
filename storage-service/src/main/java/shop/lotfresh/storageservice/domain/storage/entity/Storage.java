package shop.lotfresh.storageservice.domain.storage.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String province;

    @Builder
    private Storage(
            Long  id,
            String name,
            String province) {
        this.id = id;
        this.name = name;
        this.province = province;
    }
}