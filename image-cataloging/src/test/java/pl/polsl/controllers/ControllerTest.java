package pl.polsl.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.polsl.views.MainWindow;

/**
 * @author Karolina Kołek
 */
class ControllerTest {

    @Test
    void initController() {

        // GIVEN
        MainWindow mainWindowSpy = Mockito.spy(MainWindow.class);
        Controller controller = new Controller(mainWindowSpy);

        // WHEN
        controller.initController();

        // THEN
        Mockito.verify(mainWindowSpy).getPreviousButton();
        Mockito.verify(mainWindowSpy).getNextButton();
    }
}