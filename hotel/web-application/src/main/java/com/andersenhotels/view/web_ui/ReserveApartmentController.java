package com.andersenhotels.view.web_ui;

import com.andersenhotels.presenter.Presenter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/apartments/reserve")
public class ReserveApartmentController {

    private final Presenter presenter;

    @GetMapping
    public String showReserveApartmentPage() {
        return "reserveApartment";
    }

    @PostMapping
    public String handleReserveApartment(@RequestParam("apartmentId") @Min(1) long apartmentId,
                                         @RequestParam("guestName") @NotEmpty String guestName, Model model) {
        boolean success = presenter.reserveApartment(apartmentId, guestName);
        if (success) {
            model.addAttribute("message", "Apartment reserved successfully.");
        } else {
            model.addAttribute("error", "Failed to reserve apartment.");
        }
        return "reserveApartment";
    }
}
