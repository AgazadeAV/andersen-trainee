package com.andersenhotels.view.web_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.view.common.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class WebUI implements View {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Presenter presenter;

    public WebUI(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.presenter = new Presenter(this);
    }

    @Override
    public void startWork() {
        // Логика начала работы, если требуется
    }

    @Override
    public void finishWork() {
        // Завершающая логика, если требуется
    }

    @Override
    public boolean registerApartment() {
        double price = (double) request.getAttribute("price");
        return presenter.registerApartment(price);
    }

    @Override
    public boolean reserveApartment() {
        int apartmentId = (int) request.getAttribute("apartmentId");
        String guestName = (String) request.getAttribute("guestName");
        return presenter.reserveApartment(apartmentId, guestName);
    }

    @Override
    public boolean releaseApartment() {
        int apartmentId = (int) request.getAttribute("apartmentId");
        return presenter.releaseApartment(apartmentId);
    }

    @Override
    public List<String> listApartments() {
        int page = (int) request.getAttribute("page");
        return presenter.listApartments(page);
    }

    @Override
    public void displayMessage(String message) {
        request.setAttribute("message", message);
    }

    @Override
    public void displayError(String errorMessage) {
        request.setAttribute("error", errorMessage);
    }
}
