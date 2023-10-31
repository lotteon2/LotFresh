package shop.lotfresh.paymentservice.domain.refund.dto;

import shop.lotfresh.paymentservice.domain.refund.dto.RefundInfoResponseDTO;
import lombok.Builder;
import lombok.Data;
import shop.lotfresh.paymentservice.domain.refund.entity.Refund;
import org.springframework.data.domain.Page;

import java.util.List;

import java.time.LocalDateTime;

@Builder
@Data
public class RefundInfoListDTO {
    private List<RefundInfoResponseDTO> refundInfoList;

    private Long totalElements;

    public static RefundInfoListDTO of(Page<Refund> page, List<RefundInfoResponseDTO> refundInfoList) {
        return RefundInfoListDTO.builder()
                .refundInfoList(refundInfoList)
                .totalElements(page.getTotalElements())
                .build();
    }
    
}
