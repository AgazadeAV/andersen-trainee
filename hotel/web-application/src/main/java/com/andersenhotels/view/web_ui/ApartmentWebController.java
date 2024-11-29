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
@RequestMapping("/apartments")
public class ApartmentWebController {

    private final Presenter presenter;

    @GetMapping
    public String listApartments(@RequestParam(value = "page", defaultValue = "1") @Min(1) int page, Model model) {
        int totalPages = presenter.getTotalPages();
        if (page > totalPages) {
            model.addAttribute("error", "Invalid page number. Showing page 1.");
            page = 1;
        }
        model.addAttribute("apartments", presenter.listApartments(page));
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        return "apartments";
    }

    @PostMapping
    public String addApartment(@RequestParam("price") @Min(0) double price, Model model) {
        boolean success = presenter.registerApartment(price);
        if (success) {
            model.addAttribute("message", "Apartment registered successfully.");
        } else {
            model.addAttribute("error", "Failed to register apartment.");
        }
        return "apartments";
    }

    @PostMapping("/{id}/reserve")
    public String reserveApartment(@PathVariable("id") @Min(1) long apartmentId,
                                   @RequestParam("guestName") @NotEmpty String guestName, Model model) {
        boolean success = presenter.reserveApartment(apartmentId, guestName);
        if (success) {
            model.addAttribute("message", "Apartment reserved successfully.");
        } else {
            model.addAttribute("error", "Failed to reserve apartment.");
        }
        return "apartments";
    }

    @DeleteMapping("/{id}")
    public String releaseApartment(@PathVariable("id") @Min(1) long apartmentId, Model model) {
        boolean success = presenter.releaseApartment(apartmentId);
        if (success) {
            model.addAttribute("message", "Apartment released successfully.");
        } else {
            model.addAttribute("error", "Failed to release apartment.");
        }
        return "apartments";
    }
}
