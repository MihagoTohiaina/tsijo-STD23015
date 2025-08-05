package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String donorName;
    private String donorEmail;
    private double amount;
    private Instant donationDate;
    private String paymentMethod; // e.g., "MVOLA", "ORANGE_MONEY"
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}