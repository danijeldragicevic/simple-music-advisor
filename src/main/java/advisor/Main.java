package advisor;

import advisor.controllers.IMenuController;
import advisor.controllers.impl.MenuControllerImpl;
import advisor.config.ExternalApiConfig;

public class Main {
    private static final int ZERO_LENGTH = 0;
    
    private static final int FIRST_ARG = 0;
    private static final int SECOND_ARG = 1;
    private static final int THIRD_ARG = 2;
    private static final int FOURTH_ARG = 3;
    private static final int FIFTH_ARG = 4;
    private static final int SIXTH_ARG = 5;
    
    private static final String ACCESS = "-access";
    private static final String RESOURCE = "-resource";
    private static final String PAGE = "-page";

    public static void main(String[] args) {;
        if (args.length > ZERO_LENGTH) {
            if (args[FIRST_ARG].equals(ACCESS)) {
                ExternalApiConfig.AUTH_SERVER_PATH = args[SECOND_ARG];
            }
            if (args[THIRD_ARG].equals(RESOURCE)) {
                ExternalApiConfig.API_SERVER_PATH = args[FOURTH_ARG];
            }
            if (args[FIFTH_ARG].equals(PAGE)) {
                ExternalApiConfig.API_PAGE_LIMIT = Integer.parseInt(args[SIXTH_ARG]);
            }
        }

        IMenuController menuController = new MenuControllerImpl();
        menuController.showAuthMenu();
    }
}
