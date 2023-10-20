package io.codetrail.codetrailintellij;

public class ExtensionService {
    private static ExtensionService instance;

    public static ExtensionService getInstance() {
        if (instance == null) {
            instance = new ExtensionService();
        }

        return instance;
    }

    public void activate() {

    }

    private void connectToDesktop() {

    }
}
