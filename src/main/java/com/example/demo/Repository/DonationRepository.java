package com.example.demo.Repository;

import com.example.demo.model.Donation;
import com.example.demo.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByStatus(PaymentStatus status);
}