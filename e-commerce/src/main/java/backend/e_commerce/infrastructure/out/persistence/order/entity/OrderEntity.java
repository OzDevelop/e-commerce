package backend.e_commerce.infrastructure.out.persistence.order.entity;

import backend.core.common.utils.IntegrityUtils;
import backend.e_commerce.domain.order.OrderStatus;
import backend.e_commerce.infrastructure.out.persistence.payment.entity.PaymentEntity;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long userId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    private String paymentKey;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true ) // 설명 찾아보기
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    // 주문 데이터 무결성 해시
    private String integrityHash;

    public void calculateAndSetIntegrityHash() {
        this.integrityHash = IntegrityUtils.calculateHash(this);
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }
}
