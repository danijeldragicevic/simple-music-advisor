package advisor;

import advisor.controllers.IMenuController;
import advisor.controllers.impl.MenuControllerImpl;
import advisor.utils.Config;

public class MusicAdvisorApp {
    public static void main(String[] args) {;
        if (args.length > 1 && args[0].equals("-access")) {
            Config.AUTH_SERVER_PATH = args[1];
        }

        IMenuController controller = new MenuControllerImpl();
        controller.showAuthMenu();
    }
}
