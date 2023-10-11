package shop.lotfresh.paymentservice.domain.payment.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

public class KakaopayApproveResponseVO {
    private String aid;
    private String tid;
    private String cid;
    @JsonProperty("partner_order_id")
    private String orderId;
    @JsonProperty("partner_user_id")
    private String oauthId;
    @JsonProperty("payment_method_type")
    private String paymentMethod;
    @JsonProperty("item_name")
    private String itemName;
    private Integer quantity;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("approved_at")
    private LocalDateTime approvedAt;

    private Amount amount;

    private CardInfo cardInfo;

    @Getter
    public static class Amount {
        private Integer total;
        @JsonProperty("tax_free")
        private Integer taxFree;

        private Integer vat;

        private Integer point;

        private Integer discount;

        @JsonProperty("green_deposit")
        private Integer greenDeposit;
    }

    @Getter
    public static class CardInfo {
        @JsonProperty("purchase_corp")
        private String purchaseCorp;

        @JsonProperty("purchase_corp_code")
        private String PurchaseCorpCode;

        @JsonProperty("issuer_corp")
        private String IssuerCorp;

        @JsonProperty("issuer_corp_code")
        private String issuerCorpCode;

        @JsonProperty("kakaopay_purchase_corp")
        private String kakaopayPurchaseCorp;

        @JsonProperty("kakaopay_purchase_corp_code")
        private String kakaopayPurchaseCorpCode;

        @JsonProperty("kakaopay_issuer_corp")
        private String kakaopayIssuerCorp;

        @JsonProperty("kakaopay_issuer_corp_code")
        private String kakaopayIssuerCorpCode;

        private String bin;

        @JsonProperty("card_type")
        private String cardType;

        @JsonProperty("install_month")
        private String installMonth;

        @JsonProperty("approved_id")
        private String approvedId;

        @JsonProperty("card_mid")
        private String cardMid;

        @JsonProperty("interest_free_install")
        private String interestFreeInstall;

        @JsonProperty("card_item_code")
        private String cardItemCode;

    }
}
