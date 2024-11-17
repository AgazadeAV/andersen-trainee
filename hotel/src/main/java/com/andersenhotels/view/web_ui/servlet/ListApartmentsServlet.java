package com.andersenhotels.view.web_ui.servlet;

import com.andersenhotels.view.web_ui.RequestHandler;
import com.andersenhotels.view.web_ui.WebUI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/apartments")
public class ListApartmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler requestHandler = new RequestHandler(request, response);
        WebUI webUI = Optional.ofNullable((WebUI) getServletContext().getAttribute("webUI"))
                .orElseThrow(() -> new IllegalStateException("WebUI is not initialized in the application context."));
        webUI.setRequestHandler(requestHandler);

        int page = Optional.ofNullable(request.getParameter("page"))
                .map(String::trim)
                .filter(param -> !param.isEmpty())
                .flatMap(this::parsePageNumber)
                .orElseGet(() -> {
                    webUI.displayError("Invalid or missing page number. Defaulting to page 1.");
                    return 1;
                });

        requestHandler.setAttribute("page", page);

        List<String> apartments = webUI.listApartments();
        requestHandler.setAttribute("apartments", apartments);

        webUI.printListApartments();

        request.getRequestDispatcher("/WEB-INF/jsp/apartmentsList.jsp").forward(request, response);
    }

    private Optional<Integer> parsePageNumber(String pageParam) {
        try {
            return Optional.of(Integer.parseInt(pageParam));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
