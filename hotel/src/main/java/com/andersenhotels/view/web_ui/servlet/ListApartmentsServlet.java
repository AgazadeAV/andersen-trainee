package com.andersenhotels.view.web_ui.servlet;

import com.andersenhotels.view.web_ui.WebUI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/apartments")
public class ListApartmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebUI webUI = new WebUI(request, response);

        String pageParam = request.getParameter("page");
        int page = 1;

        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                webUI.displayError("Invalid page number. Defaulting to page 1.");
            }
        }

        request.setAttribute("page", page);
        List<String> apartments = webUI.listApartments();
        request.setAttribute("apartments", apartments);
        request.getRequestDispatcher("/WEB-INF/jsp/apartmentsList.jsp").forward(request, response);
    }
}
