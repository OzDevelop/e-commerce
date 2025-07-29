package backend.e_commerce.representaion.in.web;

import backend.e_commerce.infrastructure.out.pg.toss.TossPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final TossPayment tossPayment;

    @GetMapping("/success")
    public String paymentConfirm(
            @RequestParam(value = "paymentType") String paymentType,
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "paymentKey") String paymentKey,
            @RequestParam(value = "amount") String amount
    ) {
        return "success";
    }

    @GetMapping("/fail")
    public String paymentFail(@RequestParam(value = "message") String message) {
        return "fail";
    }


}
