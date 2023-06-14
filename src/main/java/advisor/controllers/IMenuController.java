package advisor.controllers;

import advisor.models.OutputPage;

public interface IMenuController {
    void showAuthMenu();
    void showMainMenu();
    void printOutput(OutputPage outputPage);
    void exitTheApplication();
}
