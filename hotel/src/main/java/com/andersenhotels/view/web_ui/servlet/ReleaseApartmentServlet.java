package com.andersenhotels.view.web_ui.servlet;

import com.andersenhotels.view.web_ui.WebUI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/releaseApartment")
public class ReleaseApartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/releaseApartment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebUI webUI = new WebUI(request, response);

        String apartmentIdParam = request.getParameter("apartmentId");

        if (apartmentIdParam != null) {
            try {
                int apartmentId = Integer.parseInt(apartmentIdParam);
                request.setAttribute("apartmentId", apartmentId);

                boolean success = webUI.releaseApartment();
                if (success) {
                    webUI.displayMessage("Apartment released successfully.");
                } else {
                    webUI.displayError("Failed to release apartment.");
                }
            } catch (NumberFormatException e) {
                webUI.displayError("Invalid apartment ID. Please enter a valid integer.");
            }
        } else {
            webUI.displayError("Apartment ID is required.");
        }

        request.getRequestDispatcher("/WEB-INF/jsp/releaseApartment.jsp").forward(request, response);
    }
}
