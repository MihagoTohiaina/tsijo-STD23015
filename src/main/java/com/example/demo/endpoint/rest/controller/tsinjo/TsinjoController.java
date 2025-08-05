package com.example.demo.endpoint.rest.controller.tsinjo;

import com.example.demo.Service.AidService;
import com.example.demo.Service.DonationService;
import com.example.demo.model.Aid;
import com.example.demo.model.Donation;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
public class TsinjoController {
  private final DonationService donationService;
  private final AidService aidService;

  @GetMapping("/")
  public String home(Model model) {
    List<Donation> donations = donationService.getAllDonations();
    List<Aid> aids = aidService.getAllAids();
    List<?> allMovements =
        Stream.concat(donations.stream(), aids.stream())
            .sorted(Comparator.comparing(m -> getTimestamp(m), Comparator.reverseOrder()))
            .collect(Collectors.toList());
    model.addAttribute("movements", allMovements);
    model.addAttribute("newDonation", new Donation());
    return "index";
  }

  @PostMapping("/donations")
  public RedirectView submitDonation(
      @RequestParam String donorName,
      @RequestParam String donorEmail,
      @RequestParam double amount,
      @RequestParam String pspType,
      @RequestParam String pspPaymentId) {
    donationService.createDonation(donorName, donorEmail, amount, pspType, pspPaymentId);
    return new RedirectView("/");
  }

  private java.time.Instant getTimestamp(Object obj) {
    if (obj instanceof Donation) {
      return ((Donation) obj).getDonationDate();
    } else if (obj instanceof Aid) {
      return ((Aid) obj).getAidDate();
    }
    return null;
  }
}
