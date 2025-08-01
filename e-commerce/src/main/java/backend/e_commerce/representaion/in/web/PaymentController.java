package backend.e_commerce.representaion.in.web;

import backend.e_commerce.application.command.payment.PaymentApprovedCommand;
import backend.e_commerce.application.command.payment.PaymentCancelledCommand;
import backend.e_commerce.application.port.in.payment.PaymentCommandUseCase;
import backend.e_commerce.infrastructure.out.pg.toss.TossPayment;
import backend.e_commerce.representaion.request.payment.PaymentCancelRequestDto;
import backend.e_commerce.representaion.request.payment.PaymentConfirmRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentCommandUseCase paymentCommandUseCase;

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

    @PostMapping("/confirm")
    public String paymentConfirm(@RequestBody PaymentConfirmRequestDto requestDto) {
        PaymentApprovedCommand command = new PaymentApprovedCommand(
                requestDto.getPaymentKey(),
                requestDto.getOrderId(),
                requestDto.getAmount()
        );
        log.warn("여기까지 오늬?");
        log.warn(command.getOrderId());


        return paymentCommandUseCase.paymentApproved(command);
    }
    @PostMapping("api/payment/cancel")
    @ResponseBody
    public boolean paymentCancel(@RequestBody PaymentCancelRequestDto requestDto) {
        PaymentCancelledCommand command = requestDto.toCommand();

        try {
            System.out.println("PaymentCancelRequestDto 응답 전체: " + new ObjectMapper().writeValueAsString(requestDto));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentCommandUseCase.paymentCancel(command);
    }

}
