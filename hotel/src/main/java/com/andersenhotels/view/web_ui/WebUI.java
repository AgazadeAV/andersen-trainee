package com.andersenhotels.view.web_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.common.View;
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
        presenter.loadState();
    }

    @Override
    public void complete() {
        presenter.saveState();
    }

    @Override
    public boolean registerApartment() {
        double price = requestHandler.getDoubleAttribute("price");
        boolean success = presenter.registerApartment(price);
        if (success) {
            complete();
        }
        return success;
    }

    @Override
    public boolean reserveApartment() {
        int apartmentId = requestHandler.getIntAttribute("apartmentId");
        String guestName = requestHandler.getStringAttribute("guestName");
        boolean success = presenter.reserveApartment(apartmentId, guestName);
        if (success) {
            complete();
        }
        return success;
    }

    @Override
    public boolean releaseApartment() {
        int apartmentId = requestHandler.getIntAttribute("apartmentId");
        boolean success = presenter.releaseApartment(apartmentId);
        if (success) {
            complete();
        }
        return success;
    }

    @Override
    public List<String> listApartments() {
        int page = requestHandler.getIntAttribute("page");
        return presenter.listApartments(page);
    }

    public void printListApartments() {
        try {
            List<String> listApartments = listApartments();
            listApartments.forEach(this::displayMessage);
        } catch (ApartmentNotFoundException e) {
            displayError(e.getMessage());
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
}
