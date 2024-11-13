package com.andersenhotels.view.web_ui.servlet;

import com.andersenhotels.view.web_ui.WebUI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addApartment")
public class AddApartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/addApartment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebUI webUI = new WebUI(request, response);

        String priceParam = request.getParameter("price");

        if (priceParam != null) {
            try {
                double price = Double.parseDouble(priceParam);
                request.setAttribute("price", price);

                boolean success = webUI.registerApartment();
                if (success) {
                    webUI.displayMessage("Apartment registered successfully.");
                } else {
                    webUI.displayError("Failed to register apartment.");
                }
            } catch (NumberFormatException e) {
                webUI.displayError("Invalid price format. Please enter a valid number.");
            }
        } else {
            webUI.displayError("Price is required.");
        }

        request.getRequestDispatcher("/WEB-INF/jsp/addApartment.jsp").forward(request, response);
    }
}
