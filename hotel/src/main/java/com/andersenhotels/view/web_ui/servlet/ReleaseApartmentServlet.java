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

@WebServlet("/releaseApartment")
public class ReleaseApartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/releaseApartment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler requestHandler = new RequestHandler(request, response);
        WebUI webUI = Optional.ofNullable((WebUI) getServletContext().getAttribute("webUI"))
                .orElseThrow(() -> new IllegalStateException("WebUI is not initialized in the application context."));
        webUI.setRequestHandler(requestHandler);

        Optional.ofNullable(request.getParameter("apartmentId"))
                .map(String::trim)
                .filter(param -> !param.isEmpty())
                .flatMap(this::parseApartmentId)
                .ifPresentOrElse(
                        apartmentId -> handleValidApartmentId(requestHandler, webUI, apartmentId),
                        () -> handleInvalidApartmentId(webUI)
                );

        request.getRequestDispatcher("/WEB-INF/jsp/releaseApartment.jsp").forward(request, response);
    }

    private Optional<Integer> parseApartmentId(String apartmentIdParam) {
        try {
            return Optional.of(Integer.parseInt(apartmentIdParam));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void handleValidApartmentId(RequestHandler requestHandler, WebUI webUI, int apartmentId) {
        requestHandler.setAttribute("apartmentId", apartmentId);
        boolean success = webUI.releaseApartment();
        if (success) {
            webUI.displayMessage("Apartment released successfully.");
        } else {
            webUI.displayError("Failed to release apartment.");
        }
    }

    private void handleInvalidApartmentId(WebUI webUI) {
        webUI.displayError("Invalid or missing apartment ID. Please enter a valid integer.");
    }
}
