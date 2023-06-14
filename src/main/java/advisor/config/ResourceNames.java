package advisor.config;

public enum ResourceNames {
    ALBUMS("albums"),
    CATEGORIES ("categories"),
    PLAYLISTS("playlists");
    
    private String name;

    ResourceNames(String resourceName) {
        this.name = resourceName;
    }

    public String getName() {
        return name;
    }
}
