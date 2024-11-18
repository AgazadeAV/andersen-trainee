package com.andersenhotels.view.web_ui;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestHandler {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public double getDoubleAttribute(String attributeName) {
        return (double) request.getAttribute(attributeName);
    }

    public int getIntAttribute(String attributeName) {
        return (int) request.getAttribute(attributeName);
    }

    public String getStringAttribute(String attributeName) {
        return (String) request.getAttribute(attributeName);
    }

    public void setAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }
}
