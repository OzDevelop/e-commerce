package backend.e_commerce.infrastructure.out.pg.toss.response.payment.method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cancel {
    private String transactionKey;
    private String cancelReason;
    private int cancelAmount;
    private String cancelStatus;
}
