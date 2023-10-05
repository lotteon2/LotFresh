package shop.lotfresh.storageservice.domain.storageproduct.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Storageproduct {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private Long product_id;

@Column(nullable = false)
private Long storage_id;

@Column(nullable = false)
private Long stock;

private Date expiration_date_start;

private Date expiration_date_end;

}