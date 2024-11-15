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

@WebServlet("/addApartment")
public class AddApartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/addApartment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler requestHandler = new RequestHandler(request, response);
        WebUI webUI = Optional.ofNullable((WebUI) getServletContext().getAttribute("webUI"))
                .orElseThrow(() -> new IllegalStateException("WebUI is not initialized in the application context."));
        webUI.setRequestHandler(requestHandler);

        Optional.ofNullable(request.getParameter("price"))
                .map(String::trim)
                .filter(price -> !price.isEmpty())
                .flatMap(this::parsePrice)
                .ifPresentOrElse(
                        price -> handleValidPrice(requestHandler, webUI, price),
                        () -> handleMissingOrInvalidPrice(webUI)
                );

        request.getRequestDispatcher("/WEB-INF/jsp/addApartment.jsp").forward(request, response);
    }

    private Optional<Double> parsePrice(String priceParam) {
        try {
            return Optional.of(Double.parseDouble(priceParam));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void handleValidPrice(RequestHandler requestHandler, WebUI webUI, double price) {
        requestHandler.setAttribute("price", price);
        boolean success = webUI.registerApartment();
        if (success) {
            webUI.displayMessage("Apartment registered successfully.");
        } else {
            webUI.displayError("Failed to register apartment.");
        }
    }

    private void handleMissingOrInvalidPrice(WebUI webUI) {
        webUI.displayError("Invalid or missing price. Please enter a valid number.");
    }
}
