package backend.e_commerce.application.command.product;

import backend.e_commerce.domain.product.ProductStatus;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProductCommand {
    @Builder.Default
    private Optional<String> category = Optional.empty();

    @Builder.Default
    private Optional<String> name = Optional.empty();

    @Builder.Default
    private Optional<String> description = Optional.empty();

    @Builder.Default
    private Optional<Integer> price = Optional.empty();

    @Builder.Default
    private Optional<ProductStatus> status = Optional.empty();

    @Builder.Default
    private Optional<Integer> stock = Optional.empty();

    @Builder.Default
    private Optional<String> brand = Optional.empty();

    @Builder.Default
    private Optional<String> manufacturer = Optional.empty();
}
