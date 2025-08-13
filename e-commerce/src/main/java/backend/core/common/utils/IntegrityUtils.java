package backend.core.common.utils;

import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.security.core.parameters.P;

public class IntegrityUtils {

    public static String calculateHash(OrderEntity orderEntity) {
        StringBuilder sb = new StringBuilder();
        sb.append(orderEntity.getId());
        orderEntity.getOrderItems().forEach(item -> {
            sb.append(item.getId())
                    .append(item.getQuantity())
                    .append(item.getPrice());

        });

        sb.append(orderEntity.getAddress().toString());
        return sha256(sb.toString());

    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
