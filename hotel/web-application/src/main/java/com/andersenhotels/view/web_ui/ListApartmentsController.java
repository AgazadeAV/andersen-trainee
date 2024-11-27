package com.andersenhotels.view.web_ui;

import com.andersenhotels.presenter.Presenter;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apartments")
public class ListApartmentsController {

    private final Presenter presenter;

    public ListApartmentsController(Presenter presenter) {
        this.presenter = presenter;
    }

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
        return "apartmentsList";
    }
}
