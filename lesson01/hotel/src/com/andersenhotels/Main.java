package com.andersenhotels;

import com.andersenhotels.view.common.View;
import com.andersenhotels.view.console_ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        View view = new ConsoleUI();
        view.startWork();
    }
}
