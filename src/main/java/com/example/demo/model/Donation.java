package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private String pspPaymentId;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;
}
