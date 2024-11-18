package com.andersenhotels.view.web_ui.servlet;

import com.andersenhotels.view.web_ui.RequestHandler;
import com.andersenhotels.view.web_ui.WebUI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/reserveApartment")
public class ReserveApartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/reserveApartment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler requestHandler = new RequestHandler(request, response);
        WebUI webUI = Optional.ofNullable((WebUI) getServletContext().getAttribute("webUI"))
                .orElseThrow(() -> new IllegalStateException("WebUI is not initialized in the application context."));
        webUI.setRequestHandler(requestHandler);

        Optional<Integer> apartmentIdOpt = parseApartmentId(request.getParameter("apartmentId"));
        Optional<String> guestNameOpt = Optional.ofNullable(request.getParameter("guestName"))
                .map(String::trim)
                .filter(name -> !name.isEmpty());

        if (apartmentIdOpt.isPresent() && guestNameOpt.isPresent()) {
            int apartmentId = apartmentIdOpt.get();
            String guestName = guestNameOpt.get();

            requestHandler.setAttribute("apartmentId", apartmentId);
            requestHandler.setAttribute("guestName", guestName);

            boolean success = webUI.reserveApartment();
            if (success) {
                webUI.displayMessage("Apartment reserved successfully.");
            } else {
                webUI.displayError("Failed to reserve apartment.");
            }
        } else {
            handleInvalidInput(webUI, apartmentIdOpt, guestNameOpt);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/reserveApartment.jsp").forward(request, response);
    }

    private Optional<Integer> parseApartmentId(String apartmentIdParam) {
        try {
            return Optional.ofNullable(apartmentIdParam)
                    .map(String::trim)
                    .filter(param -> !param.isEmpty())
                    .map(Integer::parseInt);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void handleInvalidInput(WebUI webUI, Optional<Integer> apartmentIdOpt, Optional<String> guestNameOpt) {
        if (apartmentIdOpt.isEmpty()) {
            webUI.displayError("Invalid or missing apartment ID. Please enter a valid number.");
        }
        if (guestNameOpt.isEmpty()) {
            webUI.displayError("Guest Name is required.");
        }
    }
}
