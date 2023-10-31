package com.lotfresh.userservice.domain.address.entity;

import com.lotfresh.userservice.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String province;
    @Column(nullable = false)
    private String roadAddress;
    @Column(nullable = false)
    private String detailAddress;
    @Column(nullable = false)
    private String zipCode;
    @Column(nullable = false, name = "defaultAddress")
    private Boolean defaultAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private Address(String province, String roadAddress, String detailAddress, String zipCode, Boolean defaultAddress, Member member) {
        this.province = province;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.defaultAddress = defaultAddress;
        this.member = member;
    }
}
