package com.andersenhotels.view;

/**
 * The View interface defines the methods required for any view in the Hotel Management application.
 * It represents the user interface layer, responsible for interacting with users
 * and displaying relevant information.
 */
public interface View {

    /**
     * Starts the work of the view, typically initializing the user interface
     * and displaying the initial state or options to the user.
     */
    void startWork();

    /**
     * Finishes the work of the view, typically handling any cleanup tasks
     * and terminating the interaction with the user.
     */
    void finishWork();
}
