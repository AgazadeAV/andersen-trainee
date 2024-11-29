package com.andersenhotels.view.web_ui;

import com.andersenhotels.presenter.Presenter;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/addApartment")
public class AddApartmentController {

    private final Presenter presenter;

    @GetMapping
    public String showAddApartmentPage() {
        return "addApartment";
    }

    @PostMapping
    public String handleAddApartment(@RequestParam("price") @Min(0) double price, Model model) {
        boolean success = presenter.registerApartment(price);
        if (success) {
            model.addAttribute("message", "Apartment registered successfully.");
        } else {
            model.addAttribute("error", "Failed to register apartment.");
        }
        return "addApartment";
    }
}
