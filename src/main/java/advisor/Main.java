package advisor;

import advisor.controllers.IMenuController;
import advisor.controllers.impl.MenuControllerImpl;

public class Main {
    public static void main(String[] args) {
        IMenuController menuController = new MenuControllerImpl();
        menuController.showAuthMenu();
    }
}
