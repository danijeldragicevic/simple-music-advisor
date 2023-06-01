package advisor.utils;


public final class ConsoleOutput {
    public String PLAYLISTS = "";
    
    private ConsoleOutput() {}

    public ConsoleOutput(String categoryName) {
        this.PLAYLISTS = String.format("---%s PLAYLISTS---", categoryName.toUpperCase());
    }
}
