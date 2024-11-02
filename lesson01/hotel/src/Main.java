import com.andersenhotels.view.common.View;
import com.andersenhotels.view.console_ui.ConsoleUI;

/**
 * The Main class serves as the entry point for the Hotel Management Console Application.
 * It initializes the view and starts the user interface.
 */
public class Main {
    /**
     * The main method is the entry point of the application.
     * It creates an instance of the ConsoleUI, which implements the View interface,
     * and invokes the startWork method to begin user interaction.
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        View view = new ConsoleUI();
        view.startWork();
    }
}
