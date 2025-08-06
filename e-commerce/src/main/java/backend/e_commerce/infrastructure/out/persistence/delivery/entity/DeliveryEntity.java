package backend.e_commerce.infrastructure.out.persistence.delivery.entity;

import backend.e_commerce.domain.delivery.DeliveryStatus;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deliveries")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Enumerated(EnumType.STRING)
    @Setter
    private DeliveryStatus status;

    @Setter
    private String trackingNumber;

    @Setter
    private String deliveryCompany;

    @Embedded
    @Setter
    private EmbeddedAddress address;

    @Setter
    private String receiverName;

    @Setter
    private String receiverPhone;

    @Setter
    private LocalDateTime shippedAt;

    @Setter
    private LocalDateTime deliveredAt;

    // TODO - 세터 사용을 대체할 리팩토링 필요.


}
