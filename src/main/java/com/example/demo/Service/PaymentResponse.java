package com.example.demo.Service;

import lombok.Data;

@Data
public class PaymentResponse {
    private String id;
    private String status;
    private String creationTimestamp;
    private PayerInfo payerInfo;
    private PspPayment pspPayment;

    @Data
    public static class PayerInfo {
        private String email;
    }

    @Data
    public static class PspPayment {
        private double amount;
        private String type;
        private String id;
    }
}