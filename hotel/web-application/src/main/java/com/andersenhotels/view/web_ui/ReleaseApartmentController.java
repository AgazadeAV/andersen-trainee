package com.andersenhotels.view.web_ui;

import com.andersenhotels.presenter.Presenter;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/releaseApartment")
public class ReleaseApartmentController {

    private final Presenter presenter;

    @GetMapping
    public String showReleaseApartmentPage() {
        return "releaseApartment";
    }

    @PostMapping
    public String handleReleaseApartment(@RequestParam("apartmentId") @Min(1) long apartmentId, Model model) {
        boolean success = presenter.releaseApartment(apartmentId);
        if (success) {
            model.addAttribute("message", "Apartment released successfully.");
        } else {
            model.addAttribute("error", "Failed to release apartment.");
        }
        return "releaseApartment";
    }
}
