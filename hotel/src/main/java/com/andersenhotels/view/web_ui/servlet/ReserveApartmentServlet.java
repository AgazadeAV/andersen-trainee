package com.andersenhotels.view.web_ui.servlet;

import com.andersenhotels.view.web_ui.WebUI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reserveApartment")
public class ReserveApartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/reserveApartment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebUI webUI = new WebUI(request, response);

        String apartmentIdParam = request.getParameter("apartmentId");
        String guestName = request.getParameter("guestName");

        if (apartmentIdParam != null && guestName != null) {
            try {
                int apartmentId = Integer.parseInt(apartmentIdParam);
                request.setAttribute("apartmentId", apartmentId);
                request.setAttribute("guestName", guestName);

                boolean success = webUI.reserveApartment();
                if (success) {
                    webUI.displayMessage("Apartment reserved successfully.");
                } else {
                    webUI.displayError("Failed to reserve apartment.");
                }
            } catch (NumberFormatException e) {
                webUI.displayError("Invalid apartment ID. Please enter a valid number.");
            }
        } else {
            webUI.displayError("Apartment ID and Guest Name are required.");
        }

        request.getRequestDispatcher("/WEB-INF/jsp/reserveApartment.jsp").forward(request, response);
    }
}
