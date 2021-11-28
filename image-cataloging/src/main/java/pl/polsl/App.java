package pl.polsl;

import pl.polsl.controllers.*;
import pl.polsl.views.*;

public class App {

    public static void main(String[] args) {
        MainWindow view = new MainWindow();
        Controller controller = new Controller(view);
        controller.initController();
    }
}
