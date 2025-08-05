package com.example.demo.Service;

import com.example.demo.Repository.DonationRepository;
import com.example.demo.model.Donation;
import com.example.demo.model.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final VolaClient volaClient;

    public Donation createDonation(String donorName, String donorEmail, double amount, String paymentMethod, String pspPaymentId) {
        Donation donation = Donation.builder()
                .donorName(donorName)
                .donorEmail(donorEmail)
                .amount(amount)
                .donationDate(Instant.now())
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.VERIFYING)
                .build();
        return donationRepository.save(donation);
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    // Polling task
    @Scheduled(fixedRate = 10000) // Poll every 10 seconds
    public void pollPaymentStatuses() {
        List<Donation> verifyingDonations = donationRepository.findByStatus(PaymentStatus.VERIFYING);
        for (Donation donation : verifyingDonations) {
            volaClient.getPaymentStatus(donation.getDonorEmail(), donation.getPaymentMethod(), donation.getPspPaymentId())
                    .subscribe(paymentResponse -> {
                        donation.setStatus(PaymentStatus.valueOf(paymentResponse.getStatus()));
                        donationRepository.save(donation);
                    });
        }
    }
}