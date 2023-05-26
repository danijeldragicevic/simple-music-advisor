package advisor.utils;


public final class ConsoleOutput {
    public static final String SUCCESS = "---SUCCESS---";
    public static final String NEW_RELEASES = "---NEW RELEASES---";
    public static final String FEATURED = "---FEATURED---";
    public static final String CATEGORIES = "---CATEGORIES---";
    public static final String GOODBYE = "---GOODBYE!---";
    public static final String PROVIDE_ACCESS = "Please, provide access for application.";
    public static final String ADD_CATEGORY_NAME = "Need to add category name also. Please try again...";
    public static final String VALUE_NOT_SUPPORTED = "Such value is not supported!";
    public static final String REQUEST_THE_ACCESS_CODE = "use this link to request the access code:";
    public static final String GOT_THE_CODE = "Got the code. Return back to your program.";
    public static final String CODE_NOT_FOUND = "Authorization code not found. Try again.";
    public static final String WAITING_FOR_CODE = "waiting for code...";
    public static final String SERVER_ERROR = "Server error";
    public static final String MAKING_HTTP_REQ = "making http request for access_token...";
    public static final String RESPONSE = "response:";
    public static final String ERROR_RESPONSE = "Error response";
    public static final String CODE_RECEIVED = "code received:";
    public String PLAYLISTS = "";
    
    private ConsoleOutput() {}

    public ConsoleOutput(String categoryName) {
        this.PLAYLISTS = String.format("---%s PLAYLISTS---", categoryName.toUpperCase());
    }
}
