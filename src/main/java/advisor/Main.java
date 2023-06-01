package advisor;

import advisor.controllers.IMenuController;
import advisor.controllers.impl.MenuControllerImpl;
import advisor.config.ExternalApiConfig;

public class Main {
    public static final int FIRST_ARG = 0;
    public static final int SECOND_ARG = 1;
    public static final int THIRD_ARG = 2;
    public static final int FOURTH_ARG = 3;
    public static final String ACCESS = "-access";
    public static final String RESOURCE = "-resource";

    public static void main(String[] args) {;
        if (args.length > 0) {
            if (args[FIRST_ARG].equals(ACCESS)) {
                ExternalApiConfig.AUTH_SERVER_PATH = args[SECOND_ARG];
            }
            if (args[THIRD_ARG].equals(RESOURCE)) {
                ExternalApiConfig.API_SERVER_PATH = args[FOURTH_ARG];
            }
        }

        IMenuController menuController = new MenuControllerImpl();
        menuController.showAuthMenu();
    }
}
