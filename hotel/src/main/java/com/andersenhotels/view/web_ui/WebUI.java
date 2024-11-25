package com.andersenhotels.view.web_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.View;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class WebUI implements View {

    @Setter
    private RequestHandler requestHandler;
    private final Presenter presenter;

    public WebUI() {
        this.presenter = new Presenter(this);
    }

    @Override
    public void initialize() {
        displayMessage("WebUI initialized.");
    }

    @Override
    public boolean registerApartment() {
        try {
            double price = requestHandler.getDoubleAttribute("price");
            boolean success = presenter.registerApartment(price);
            if (success) {
                displayMessage("Apartment registered successfully.");
            }
            return success;
        } catch (Exception e) {
            displayError("Failed to register apartment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean reserveApartment() {
        try {
            int apartmentId = requestHandler.getIntAttribute("apartmentId");
            String guestName = requestHandler.getStringAttribute("guestName");
            boolean success = presenter.reserveApartment(apartmentId, guestName);
            if (success) {
                displayMessage("Apartment reserved successfully.");
            }
            return success;
        } catch (Exception e) {
            displayError("Failed to reserve apartment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean releaseApartment() {
        try {
            int apartmentId = requestHandler.getIntAttribute("apartmentId");
            boolean success = presenter.releaseApartment(apartmentId);
            if (success) {
                displayMessage("Apartment released successfully.");
            }
            return success;
        } catch (Exception e) {
            displayError("Failed to release apartment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> listApartments() {
        try {
            int page = requestHandler.getIntAttribute("page");
            List<String> apartments = presenter.listApartments(page);
            displayMessage("Listed apartments successfully.");
            return apartments;
        } catch (ApartmentNotFoundException e) {
            displayError("Failed to list apartments: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public void displayMessage(String message) {
        requestHandler.setAttribute("message", message);
    }

    @Override
    public void displayError(String errorMessage) {
        requestHandler.setAttribute("error", errorMessage);
    }

    @Override
    public void complete() {
        displayMessage("Operation completed successfully.");
    }

    public void printListApartments() {
        List<String> apartments = listApartments();
        apartments.forEach(this::displayMessage);
    }
}
