package backend.e_commerce.infrastructure.out.persistence.product.entity;

import backend.core.common.entity.TimeBaseEntity;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.product.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Time;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate  // 적용 전, 후 비교해보기
public class ProductEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sellerId;

    private String category;
    private String name;
    private String description;
    private int price; // 상품 가격
    private int stock; // 재고 수량

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private String brand;
    private String manufacturer;
}
